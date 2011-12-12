package models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.data.validation.Email;
import play.data.validation.Match;
import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Phone;
import play.data.validation.Required;
import play.db.jpa.GenericModel;

@Entity
public class User extends GenericModel {
    private static final long serialVersionUID = -602733026033932730L;
    
    @Transient
    public static Logger log = LoggerFactory.getLogger(User.class);
    
    @Id
    @MinSize(3)
    @MaxSize(15)
    @Match(value="^\\w*$", message="Not a valid username")
    public String username;
    
    @Required
    @MinSize(5)
    @MaxSize(15)
    public String password;
    
    @Required
    @MinSize(1)
    @MaxSize(100)
    public String name;
    
    @Required
    @Email
    public String email;

    @Phone
    @Column(nullable=true)
    public String phone;

    @ManyToMany
    public List<Club> clubs = new ArrayList<Club>();

    @OneToMany
    public List<MembershipRequest> membershipRequests = new ArrayList<MembershipRequest>();

    @ManyToMany
    public List<ClubRole> clubRoles = new ArrayList<ClubRole>();
    
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	@OrderBy("date desc")
	public List<Score> scores = new ArrayList<Score>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("date DESC")
    public List<Handicap> handicapHistory = new ArrayList<Handicap>();

	@ManyToMany
	public List<TeeTime> teeTimes = new ArrayList<TeeTime>();

	@OneToOne(cascade=CascadeType.ALL)
	public NotificationPreference newMemberShipNotificationType = new NotificationPreference();

	@OneToOne(cascade=CascadeType.ALL)
	public NotificationPreference handicapCalculateNotificationType = new NotificationPreference();

	@OneToOne(cascade=CascadeType.ALL)
	public NotificationPreference newTeeTimeNotificationType = new NotificationPreference();

	@OneToOne(cascade=CascadeType.ALL)
	public NotificationPreference teeTimeFullNotificationType = new NotificationPreference();

	@Transient
	public Club selectedClub;
	@Transient
	private Set<String> roles = new HashSet<String>();
	@Transient
	private BigDecimal handicap;
	
    public User() {
    	newMemberShipNotificationType.notifyOnSite = true;
    	handicapCalculateNotificationType.notifyOnSite = true;
    	newTeeTimeNotificationType.notifyOnSite = true;
    	teeTimeFullNotificationType.notifyOnSite = true;
    }

    public User(final String name, final String username, final String email) {
    	this();
        this.name = name;
        this.username = username;
        this.email = email;
    }

    public User(final String name, final String username, final String email, final String password) {
        this(name, username, email);
        this.password = password;
    }

    @Transient
    public String getEmailWithName() {
        return name + " <" + email + ">";
    }

	public void addMembership(Club club) {
		if (club != null) {
			club.addMember(this);
			clubs.add(club);
		}
	}
	
	public void addScore(Score score) {
		score.user = this;
		scores.add(score);
	}

	public boolean calculateHandicap() {
		boolean handicapCalculated = false;
		BigDecimal handicap = null;
		//get up to last 20 scores
		// if num scores 5 or 6 use lowest differential
		//     7-8 use lowest 2
		//     9-10 use 3
		//     11-12 use 4
		//     13-14 use 5
		//     15-16 use 6
		//     17 use 7
		//     18 use 8
		//     19 use 9
		//     20 use 10
		// average the differentials
		// multiply by .96
		// truncate after 1 decimal place
		List<Score> eligibleScores = getLast20Scores();
		int numElligibleScores = eligibleScores.size();
		int scoresToUse = 0;
		if (numElligibleScores < 5) {
			//cannot calc handicap
		} else if (numElligibleScores >= 5 && numElligibleScores < 7) {
			scoresToUse = 1;
		} else if (numElligibleScores >= 7 && numElligibleScores < 9) {
			scoresToUse = 2;
		} else if (numElligibleScores >= 9 && numElligibleScores < 11) {
			scoresToUse = 3;
		} else if (numElligibleScores >= 11 && numElligibleScores < 13) {
			scoresToUse = 4;
		} else if (numElligibleScores >= 13 && numElligibleScores < 15) {
			scoresToUse = 5;
		} else if (numElligibleScores >= 15 && numElligibleScores < 17) {
			scoresToUse = 6;
		} else if (numElligibleScores == 17) {
			scoresToUse = 7;
		} else if (numElligibleScores == 18) {
			scoresToUse = 8;
		} else if (numElligibleScores == 19) {
			scoresToUse = 9;
		} else if (numElligibleScores == 20) {
			scoresToUse = 10;
		}
		if (scoresToUse > 0) {
			//sort by differential asc, lowest first
			Collections.sort(eligibleScores, new Comparator<Score>() {
			    public int compare(Score o1, Score o2) {
			        return (int) (o1.getDifferential() - o2.getDifferential());
			    }});
			
			List<Score> bestDifferentials = eligibleScores.subList(0, scoresToUse);
			double totalDifferential = 0;
			for (Score score : bestDifferentials) {
				totalDifferential += score.getDifferential();
				score.counter = true;
			}
			handicap = new BigDecimal((totalDifferential / scoresToUse)*0.96).setScale(1,BigDecimal.ROUND_DOWN);
			Handicap newHandicap = new Handicap(handicap, Calendar.getInstance().getTime());
			newHandicap.save();
			handicapHistory.add(newHandicap);
			this.save();
			handicapCalculated = true;
		}
		return handicapCalculated;
	}

	public BigDecimal getHandicap() {
		return handicapHistory.size()>0?handicapHistory.get(0).handicap:null;
	}

	public void addClubRole(ClubRole clubRole) {
		clubRoles.add(clubRole);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	@Transient
	public List<Score> getLast20Scores() {
		List<Score> sortedDescScores = scores;
		Collections.sort(sortedDescScores, new Comparator<Score>() {
		    public int compare(Score o1, Score o2) {
		        return o2.date.compareTo(o1.date);
		    }});
		List<Score> temp = sortedDescScores.subList(0, Math.min(20, sortedDescScores.size()));
		
		//now sort by date asc
		Collections.sort(temp, new Comparator<Score>() {
		    public int compare(Score o1, Score o2) {
		        return o1.date.compareTo(o2.date);
		    }});
		return temp;
	}

	public boolean hasClubRole(String roleName) {
		boolean retVal = false;
		if (roleName != null) {
			for (ClubRole clubRole: clubRoles) {
				if (roleName.equalsIgnoreCase(clubRole.role.name)) {
					retVal = true;
					break;
				}
			}
		}
		return retVal;
	}
	
	public Score getLastScore() {
		return scores.size() > 0 ? scores.get(0) : new Score();
	}

    public static User connect(String email, String password) {
        return find("byUsernameAndPassword", email, password).first();
    }

	@Override
	public String toString() {
		return name;
	}
	
    public boolean hasRole(String roleName) {
    	log.info("Checking for role "+roleName + " in "+clubRoles);
    	boolean retVal = false;
    	for (ClubRole clubRole: clubRoles) {
    		if (roleName.equalsIgnoreCase(clubRole.role.name)) {
    			retVal = true;
    			break;
    		}
    	}
    	log.info("Checking for role "+roleName+", returning "+retVal);
    	return retVal;
    }
    
    public boolean isClubListVisible() {
    	boolean retVal = hasRole("Admin");
    	return retVal;
    }

    public boolean isCourseListVisible() {
    	boolean retVal = hasRole("Admin") || hasRole("ClubAdmin");
    	return retVal;
    }

    public boolean isMemberRequestsVisible() {
    	boolean retVal = hasRole("Admin") || hasRole("ClubAdmin");
    	return retVal;
    }

    public boolean isTeeTimeListVisible() {
    	boolean retVal = hasRole("Admin") || hasRole("ClubAdmin") || hasRole("TeeTimeChair") || hasRole("Member");
    	return retVal;
    }

    public boolean isTeeTimeListDeleteable() {
    	boolean retVal = hasRole("Admin") || hasRole("ClubAdmin") || hasRole("TeeTimeChair");
    	return retVal;
    }

    public boolean isTeeTimeListUpdateable() {
    	boolean retVal = hasRole("Admin") || hasRole("ClubAdmin") || hasRole("TeeTimeChair");
    	return retVal;
    }

    public boolean isScoreListVisible() {
    	boolean retVal = hasRole("Admin") || hasRole("ClubAdmin") || hasRole("TeeTimeChair") || hasRole("Member");
    	return retVal;
    }

    public boolean isAccountVisible() {
    	boolean retVal = (hasRole("Admin") || hasRole("ClubAdmin") || hasRole("TeeTimeChair") || hasRole("Member"));
    	return retVal;
    }

    public boolean isTournamentListVisible() {
    	boolean retVal = isScoreListVisible();
    	return retVal;
    }

	public void setHandicap(BigDecimal handicap) {
		this.handicap = handicap;
	}

//    public boolean isAbleToModifyOthersScores() {
//    	boolean retVal = isClubSelected() && (hasRole("Admin") || hasRole("ClubAdmin"));
//    	return retVal;
//    }

}

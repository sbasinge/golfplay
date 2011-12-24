package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import play.data.validation.Required;

@Entity
public class TeeTime extends BaseEntity {
	@Temporal(TemporalType.TIMESTAMP)
	public Date date;
	
	@ManyToOne
	@Required
	public Course course;
	
	@ManyToOne
	public TeeSet teeSet;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="teetime")
	public List<TeeTimeParticipant> participants = new ArrayList<TeeTimeParticipant>();

    @ManyToOne
    public Tournament tournament;

    @ManyToOne
    public Club club;
    
	@Column
	public boolean notificationOn;

	@Column
	public boolean complete;
	
	public int numPlayers;
	
	@OneToOne
	public User organizer;
	
	public TeeTime() {}
	
	
	public TeeTime(Date date, TeeSet teeSet, Course course, List<User> players, boolean notificationOn, int numPlayers) {
		super();
		this.date = date;
		this.course = course;
		this.teeSet = teeSet;
		this.notificationOn = notificationOn;
		this.numPlayers = numPlayers;
		for (User user : players) {
			TeeTimeParticipant participant = new TeeTimeParticipant(this, user);
			participants.add(participant);
			user.teeTimes.add(this);
		}

	}

	public void signUp(User user) {
		if (participants.size() < numPlayers) {
			participants.add(new TeeTimeParticipant(this,user));
			user.teeTimes.add(this);
		} else {
			throw new IllegalStateException("All available spots are taken.");
		}
	}
	
	@Transient
	public int getNumOpenSpots() {
		int retVal = 0;
		retVal = numPlayers - participants.size();
		return retVal;
	}


	public boolean hasScores() {
		boolean retVal = false;
		for (TeeTimeParticipant participant : participants) {
			if (participant.score != null && participant.score.grossScore != null) {
				retVal = true;
				break;
			}
		}
		return retVal;
	}


	public boolean isUserSignedUp(User currentUser) {
		boolean retVal = false;
		for (TeeTimeParticipant participant : participants) {
			if (participant.user.equals(currentUser)) {
				retVal = true;
				break;
			}
		}
		return retVal;
	}
	
	public boolean isInTheFuture() {
		boolean retVal = true;
//		if (getDate().after(Calendar.getInstance().getTime())) {
//			retVal = true;
//		}
		return retVal;
	}
	
	public void removeParticipant(User user) {
		TeeTimeParticipant participant = null;
		for (TeeTimeParticipant participant2 : participants) {
			if (participant2.user.equals(user)) {
				participant = participant2;
				break;
			}
		}
		if (participant != null) {
			TeeTime teetime = participant.teetime;
			user.teeTimes.remove(teetime);
			participants.remove(participant);
		}
	}


	@Override
	public String toString() {
		return "TeeTime [date=" + date + ", course=" + course + ", teeSet=" + teeSet + ", participants=" + participants + ", notificationOn=" + notificationOn + ", complete=" + complete + ", numPlayers=" + numPlayers + ", organizer="
				+ organizer + "]";
	}
}

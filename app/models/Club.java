package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import play.data.validation.Required;
import play.data.validation.Unique;

@Entity
@NamedQueries(value = { 
		@NamedQuery(name = "findClubByName", query = "select c from Club c where c.name = :name") }
)
public class Club extends BaseEntity {

	@Unique
	@Required
	public String name;

	public String websiteUrl;
	public String rssFeedUrl;

	public Club() {};
	public Club(String name) {this.name=name;}
	
    @ManyToMany(mappedBy="clubs")
    public List<User> members = new ArrayList<User>();

    @OneToMany(cascade={CascadeType.ALL})
    public List<MembershipRequest> membershipRequests = new ArrayList<MembershipRequest>();

	public void addMemberShipRequest(MembershipRequest membershipRequest) {
		membershipRequests.add(membershipRequest);
		membershipRequest.user.membershipRequests.add(membershipRequest);
	}
	public void removeMemberShipRequest(MembershipRequest membershipRequest) {
		membershipRequests.remove(membershipRequest);
		membershipRequest.user.membershipRequests.remove(membershipRequest);
	}

	public void addClubRole(Role role) {
		ClubRole clubRole = new ClubRole();
		clubRole.club = this;
		clubRole.role = role;
	}
	
	public void addMember(User user) {
		if (user != null) {
			members.add(user);
			user.clubs.add(this);
		}
	}
	
	public List<User> locateMembersWithRole(String roleName) {
		List<User> retVal = new ArrayList<User>();
		for (User user : members) {
			if (user.hasClubRole(roleName)) {
				retVal.add(user);
			}
		}
		return retVal;
	}
	@Override
	public String toString() {
		return "Club [id="+id+", name=" + name +" ]";
	}
}

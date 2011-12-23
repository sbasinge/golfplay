package controllers;

import java.util.List;

import models.Club;
import models.ClubRole;
import models.MembershipRequest;
import models.Role;
import models.User;
import notifiers.Notifier;
import play.db.jpa.JPA;
import play.mvc.With;

@With(Secure.class)
public class Members extends Application {

	public static void list() {
		Club club = Club.find("byName", renderArgs.get("selectedClub")).first();
		if (club != null) {
			List<User> members = club.members;
			List<MembershipRequest> membershipRequests = club.membershipRequests;
			render(members, membershipRequests);
		}
	}

	public static void listForClub(Long id) {
		Club club = Club.findById(id);
		if (club != null) {
			List<User> members = club.members;
			List<MembershipRequest> membershipRequests = club.membershipRequests;
			render(members, membershipRequests);
		}
	}

    public static void calculateHandicap(String id) {
    	User member = User.findById(id);
    	member.calculateHandicap();
    	Notifier.instance().handicapCalculated(member);
        list();
    }

    public static void view(String id) {
    	User member = User.findById(id);
        renderArgs.put("member", member);
        Scores.list();
    }

    public static void accept(Long requestId) {
    	MembershipRequest membershipRequest = MembershipRequest.findById(requestId);
    	Club clubSelection = membershipRequest.club;
    	clubSelection.addMember(membershipRequest.user);
        clubSelection.removeMemberShipRequest(membershipRequest);
        ClubRole clubRole = JPA.em().createNamedQuery("findClubRoleByNameAndClub",ClubRole.class).setParameter("roleName", Role.MEMBER_ROLE_NAME).setParameter("clubName", clubSelection.name).getSingleResult();
        membershipRequest.user.addClubRole(clubRole);
        membershipRequest.delete();
        Notifier.instance().membershipAccepted(membershipRequest);
    	
    }

    public static void reject(Long requestId) {
    	MembershipRequest membershipRequest = MembershipRequest.findById(requestId);
    	Club clubSelection = membershipRequest.club;
        clubSelection.removeMemberShipRequest(membershipRequest);
        membershipRequest.delete();
        Notifier.instance().membershipRejected(membershipRequest);
    }

    public static void cancel() {
    	Clubs.list();
    }

}

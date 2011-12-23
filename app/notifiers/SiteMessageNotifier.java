package notifiers;

import java.io.Serializable;

import models.MembershipRequest;
import models.Role;
import models.Score;
import models.SiteMessage;
import models.TeeTime;
import models.TeeTimeParticipant;
import models.User;
import play.mvc.With;
import controllers.Secure;

@With(Secure.class)
public class SiteMessageNotifier implements MessageNotifier, Serializable {
	private static final long serialVersionUID = -1186513111105203298L;
	private User user;
	
//    @Before
//    private void something() {
//        if(Security.isConnected()) {
//            user = User.find("byUsername", session.get("username")).first();
//        }
//    }
    
	public void registrationCompleted(MembershipRequest membershipRequest) {
		SiteMessage message = new SiteMessage(membershipRequest.user,"Registration has been completed.");
		message.save();
		for (User user : membershipRequest.club.locateMembersWithRole(Role.CLUBADMIN_ROLE_NAME)) {
			SiteMessage adminMessage = new SiteMessage(user,membershipRequest.user.name+" has registered to join your club.  Please accept/reject their membership using the Members page.");
			adminMessage.save();
		}
	}

	public void membershipRejected(MembershipRequest membershipRequest) {
		SiteMessage message = new SiteMessage(membershipRequest.user,"Membership to club "+membershipRequest.club.name+" has been rejected.");
		message.save();

	}

	public void membershipAccepted(MembershipRequest membershipRequest) {
		SiteMessage message = new SiteMessage(membershipRequest.user,"Membership to club "+membershipRequest.club.name+" has been accepted.");
		message.save();
	}

	public void handicapCalculated(User user) {
		if (user.handicapCalculateNotificationType.notifyOnSite) {
			SiteMessage message = new SiteMessage(user,"Handicap recalculated to "+user.getHandicap());
			message.save();
		}
	}

	public void scoreUpdated(Score score) {
		SiteMessage message = new SiteMessage(score.user,"A new score has been added for "+score.teeSet.course.name+" on "+ score.date);
		message.save();
		
	}

	public void teetimeAdded(TeeTime teetime) {
		if (teetime.notificationOn) {
//			for (User user : identity.getSelectedClub().getMembers()) {
//				if (user.getNewTeeTimeNotificationType().isNotifyOnSite()) {
//					SiteMessage message = new SiteMessage(user,"Tee Time has been added at "+teetime.getCourse().getName()+" on "+sdf.format(teetime.getDate()));
//					entityManager.persist(message);
//				}
//			}
//			entityManager.flush();
		}
	}

	public void teetimeUpdated(TeeTimeParticipant participant) {
		if (participant.teetime.notificationOn) {
//			for (User user : identity.getSelectedClub().getMembers()) {
//				if (!user.getUsername().equals(participant.user.getUsername()) && user.getTeeTimeFullNotificationType().isNotifyOnSite()) {
//					SiteMessage message = new SiteMessage(user,participant.user.name+" has reserved a spot on the Tee Time at "+participant.getTeetime().getCourse().getName()+" on "+sdf.format(participant.getTeetime().getDate())+". There are now "+participant.getTeetime().getNumOpenSpots()+" spots left.");
//					entityManager.persist(message);
//				}
//			}
//			entityManager.flush();
		}
	}

	public void teetimeDeleted(TeeTime teetime) {
		if (teetime.notificationOn) {
//			for (User user : identity.getSelectedClub().getMembers()) {
//				if (user.getTeeTimeFullNotificationType().isNotifyOnSite()) {
//					SiteMessage message = new SiteMessage(user,"Tee Time has been deleted for "+teetime.getCourse().getName()+" on "+sdf.format(teetime.getDate()));
//					entityManager.persist(message);
//				}
//			}
//			entityManager.flush();
		}
	}

}

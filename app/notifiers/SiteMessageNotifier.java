package notifiers;

import java.io.Serializable;

import models.Club;
import models.MembershipRequest;
import models.Role;
import models.Score;
import models.SiteMessage;
import models.TeeTime;
import models.TeeTimeParticipant;
import models.User;

public class SiteMessageNotifier implements MessageNotifier, Serializable {
	private static final long serialVersionUID = -1186513111105203298L;
	
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

	public void teetimeAdded(TeeTime teetime, Club club) {
		if (teetime.notificationOn && club != null) {
			for (User user : club.members) {
				if (user.newTeeTimeNotificationType.notifyOnSite) {
					SiteMessage message = new SiteMessage(user,"Tee Time has been added at "+teetime.course.name+" on "+teetime.date);
					message.save();
				}
			}
		}
	}

	public void teetimeUpdated(TeeTimeParticipant participant, Club club) {
		if (participant.teetime.notificationOn && club != null) {
			for (User user : club.members) {
				if (!user.username.equals(participant.user.username) && user.teeTimeFullNotificationType.notifyOnSite) {
					SiteMessage message = new SiteMessage(user,participant.user.name+" has reserved a spot on the Tee Time at "+participant.teetime.course.name+" on "+participant.teetime.date+". There are now "+participant.teetime.getNumOpenSpots()+" spots left.");
					message.save();
				}
			}
		}
	}

	public void teetimeDeleted(TeeTime teetime, Club club) {
		if (teetime.notificationOn && club != null) {
			for (User user : club.members) {
				if (user.teeTimeFullNotificationType.notifyOnSite) {
					SiteMessage message = new SiteMessage(user,"Tee Time has been deleted for "+teetime.course.name+" on "+teetime.date);
					message.save();
				}
			}
		}
	}

}

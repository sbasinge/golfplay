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
import play.libs.F.Promise;

public class SiteMessageNotifier implements MessageNotifier, Serializable {
	private static final long serialVersionUID = -1186513111105203298L;
	
	public Promise registrationCompleted(MembershipRequest membershipRequest) {
		SiteMessage message = new SiteMessage(membershipRequest.user,"Registration has been completed.");
		message.create();
		for (User user : membershipRequest.club.locateMembersWithRole(Role.CLUBADMIN_ROLE_NAME)) {
			SiteMessage adminMessage = new SiteMessage(user,membershipRequest.user.name+" has registered to join your club.  Please accept/reject their membership using the Members page.");
			adminMessage.create();
		}
		return null;
	}

	public Promise membershipRejected(MembershipRequest membershipRequest) {
		SiteMessage message = new SiteMessage(membershipRequest.user,"Membership to club "+membershipRequest.club.name+" has been rejected.");
		message.create();
		return null;
	}

	public Promise membershipAccepted(MembershipRequest membershipRequest) {
		SiteMessage message = new SiteMessage(membershipRequest.user,"Membership to club "+membershipRequest.club.name+" has been accepted.");
		message.create();
		return null;
	}

	public Promise handicapCalculated(User user) {
		if (user.handicapCalculateNotificationType.notifyOnSite) {
			SiteMessage message = new SiteMessage(user,"Handicap recalculated to "+user.getHandicap());
			message.create();
		}
		return null;
	}

	public Promise scoreUpdated(Score score) {
		SiteMessage message = new SiteMessage(score.user,"A new score has been added for "+score.teeSet.course.name+" on "+ score.date);
		message.create();
		return null;
	}

	public Promise teetimeAdded(TeeTime teetime) {
		Club club = teetime.club;
		if (teetime.notificationOn && club != null) {
			for (User user : club.members) {
				if (user.newTeeTimeNotificationType.notifyOnSite) {
					SiteMessage message = new SiteMessage(user,"Tee Time has been added at "+teetime.course.name+" on "+teetime.date);
					message.create();
				}
			}
		}
		return null;
	}

	public Promise teetimeUpdated(TeeTimeParticipant participant) {
		Club club = participant.teetime.club;
		if (participant.teetime.notificationOn && club != null) {
			for (User user : club.members) {
				if (!user.username.equals(participant.user.username) && user.teeTimeFullNotificationType.notifyOnSite) {
					SiteMessage message = new SiteMessage(user,participant.user.name+" has reserved a spot on the Tee Time at "+participant.teetime.course.name+" on "+participant.teetime.date+". There are now "+participant.teetime.getNumOpenSpots()+" spots left.");
					message.create();
				}
			}
		}
		return null;
	}

	public Promise teetimeDeleted(TeeTime teetime) {
		Club club = teetime.club;
		if (teetime.notificationOn && club != null) {
			for (User user : club.members) {
				if (user.teeTimeFullNotificationType.notifyOnSite) {
					SiteMessage message = new SiteMessage(user,"Tee Time has been deleted for "+teetime.course.name+" on "+teetime.date);
					message.create();
				}
			}
		}
		return null;
	}

}

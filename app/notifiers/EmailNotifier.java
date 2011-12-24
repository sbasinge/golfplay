package notifiers;

import models.Club;
import models.MembershipRequest;
import models.Role;
import models.Score;
import models.TeeTime;
import models.TeeTimeParticipant;
import models.User;
import play.Logger;
import play.libs.F.Promise;
import play.mvc.Mailer;

public class EmailNotifier extends Mailer implements MessageNotifier {

	public static void lostPassword(User user) {
		String newpassword = user.password;
		setFrom("Robot <robot@thecompany.com>");
		setSubject("Your password has been reset");
		addRecipient(user.email);
		send(user, newpassword);
	}

	public static void registrationCompletedEmail(MembershipRequest membershipRequest) {
		Logger.warn("Membership completed notification.");
		for (User admin : membershipRequest.club.locateMembersWithRole(Role.CLUBADMIN_ROLE_NAME)) {
			User user = membershipRequest.user;
			setSubject("Welcome %s", user.name);
			//		addRecipient(admin.email);
			addRecipient("sbasinge@gmail.com");
			setFrom(membershipRequest.club.name+" <donotreply@"+membershipRequest.club.name+".com>");
//		EmailAttachment attachment = new EmailAttachment();
//		attachment.setDescription("A pdf document");
//		attachment.setPath(Play.getFile("rules.pdf").getPath());
//		addAttachment(attachment);
			send(membershipRequest);
		}
	}

	public static void membershipAcceptedEmail(MembershipRequest membershipRequest) {
		Logger.warn("Membership accepted notification.");
		User user = membershipRequest.user;
		setSubject("Congratulations %s", user.name);
//		addRecipient(user.email);
		addRecipient("sbasinge@gmail.com");
		setFrom(membershipRequest.club.name+" <donotreply@"+membershipRequest.club.name+".com>");
		send(membershipRequest);
	}

	public static void membershipRejectedEmail(MembershipRequest membershipRequest) {
		Logger.warn("Membership rejected notification.");
		User user = membershipRequest.user;
		setSubject("So sorry %s", user.name);
//		addRecipient(user.email);
		addRecipient("sbasinge@gmail.com");
		setFrom(membershipRequest.club.name+" <donotreply@"+membershipRequest.club.name+".com>");
		send(membershipRequest);
	}

	public static void handicapCalculatedEmail(User user) {
		Logger.warn("Handicap calculated notification.");
		if (user.handicapCalculateNotificationType.notifyByEmail || user.handicapCalculateNotificationType.notifyByText) {
			setSubject("Handicap News for %s", user.name);
//			addRecipient(user.email);
			addRecipient("sbasinge@gmail.com");
			setFrom("handicaps <donotreply@golfplus.com>");
			send(user);
		}
	}

	public static void scoreUpdatedEmail(Score score) {
		setSubject("Handicap News for %s", score.user.name);
//		addRecipient(user.email);
		addRecipient("sbasinge@gmail.com");
		setFrom("handicaps <donotreply@golfplus.com>");
		send(score);
	}

	public static void teetimeAddedEmail(TeeTime teetime, Club club) {
		if (teetime.notificationOn && club != null) {
			for (User user : club.members) {
				if (user.handicapCalculateNotificationType.notifyByEmail || user.handicapCalculateNotificationType.notifyByText) {
					setSubject("Teetime Added for %s", teetime.course.name);
					//		addRecipient(user.email);
					addRecipient("sbasinge@gmail.com");
					setFrom("handicaps <donotreply@golfplus.com>");
					send(teetime);
				}
			}
		}
	}

	public static void teetimeUpdatedEmail(TeeTimeParticipant participant, Club club) {
		if (participant.teetime.notificationOn && club != null) {
			for (User user : club.members) {
				if (!user.username.equals(participant.user.username) && (user.handicapCalculateNotificationType.notifyByEmail || user.handicapCalculateNotificationType.notifyByText)) {
					setSubject("Teetime Updated %s", participant.user.name);
					//		addRecipient(user.email);
					addRecipient("sbasinge@gmail.com");
					setFrom("handicaps <donotreply@golfplus.com>");
					send(participant);
					
				}
			}
		}

	}

	public static void teetimeDeletedEmail(TeeTime teetime, Club club) {
		if (teetime.notificationOn && club != null) {
			for (User user : club.members) {
				if (user.handicapCalculateNotificationType.notifyByEmail || user.handicapCalculateNotificationType.notifyByText) {
					setSubject("Teetime Deleted %s", teetime.course.name);
					//		addRecipient(user.email);
					addRecipient("sbasinge@gmail.com");
					setFrom("handicaps <donotreply@golfplus.com>");
					send(teetime);
				}
			}
		}
	}

	public Promise registrationCompleted(MembershipRequest membershipRequest) {
		EmailNotifier.registrationCompletedEmail(membershipRequest);
		return null;
	}
	
	public Promise membershipRejected(MembershipRequest membershipRequest) {
		EmailNotifier.membershipRejectedEmail(membershipRequest);
		return null;
	}

	public Promise membershipAccepted(MembershipRequest membershipRequest) {
		EmailNotifier.membershipAcceptedEmail(membershipRequest);
		return null;
	}

	public Promise handicapCalculated(User user) {
		EmailNotifier.handicapCalculatedEmail(user);
		return null;
	}

	public Promise scoreUpdated(Score score) {
		EmailNotifier.scoreUpdatedEmail(score);
		return null;
	}

	public Promise teetimeAdded(TeeTime teetime, Club club) {
		EmailNotifier.teetimeAddedEmail(teetime, club);
		return null;
	}

	public Promise teetimeUpdated(TeeTimeParticipant participant, Club club) {
		EmailNotifier.teetimeUpdatedEmail(participant, club);
		return null;
	}

	public Promise teetimeDeleted(TeeTime teetime, Club club) {
		EmailNotifier.teetimeDeletedEmail(teetime, club);
		return null;
	}
}

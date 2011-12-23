package notifiers;

import models.Club;
import models.MembershipRequest;
import models.Score;
import models.TeeTime;
import models.TeeTimeParticipant;
import models.User;
import play.Logger;
import play.mvc.Mailer;

public class EmailNotifier extends Mailer implements MessageNotifier {

	public void lostPassword(User user) {
		String newpassword = user.password;
		setFrom("Robot <robot@thecompany.com>");
		setSubject("Your password has been reset");
		addRecipient(user.email);
		send(user, newpassword);
	}

	public static void registrationCompletedEmail(MembershipRequest membershipRequest) {
		Logger.warn("Membership completed notification.");
		User user = membershipRequest.user;
		setSubject("Welcome %s", user.name);
//		addRecipient(user.email);
		addRecipient("sbasinge@gmail.com");
		setFrom(membershipRequest.club.name+" <donotreply@"+membershipRequest.club.name+".com>");
//		EmailAttachment attachment = new EmailAttachment();
//		attachment.setDescription("A pdf document");
//		attachment.setPath(Play.getFile("rules.pdf").getPath());
//		addAttachment(attachment);
		send(membershipRequest);
	}

	public static void membershipAcceptedEmail(MembershipRequest membershipRequest) {
		Logger.warn("Membership accepted notification.");
		User user = membershipRequest.user;
		setSubject("Welcome %s", user.name);
//		addRecipient(user.email);
		addRecipient("sbasinge@gmail.com");
		setFrom(membershipRequest.club.name+" <donotreply@"+membershipRequest.club.name+".com>");
		send(membershipRequest);
	}

	public static void membershipRejectedEmail(MembershipRequest membershipRequest) {
		Logger.warn("Membership rejected notification.");
		User user = membershipRequest.user;
		setSubject("Welcome %s", user.name);
//		addRecipient(user.email);
		addRecipient("sbasinge@gmail.com");
		setFrom(membershipRequest.club.name+" <donotreply@"+membershipRequest.club.name+".com>");
		send(membershipRequest);
	}

	public void registrationCompleted(MembershipRequest membershipRequest) {
		EmailNotifier.registrationCompletedEmail(membershipRequest);
	}
	
	public void membershipRejected(MembershipRequest membershipRequest) {
		EmailNotifier.membershipRejectedEmail(membershipRequest);
	}

	public void membershipAccepted(MembershipRequest membershipRequest) {
		EmailNotifier.membershipAcceptedEmail(membershipRequest);
	}

	public void handicapCalculated(User user) {
		// log.warn("Handicap calculated notification.");
		String subject = "You're handicap has been updated....";
		String text = "You're current handicap is a " + user.getHandicap();
		String[] sendTo = { user.email };

		if (user.handicapCalculateNotificationType.notifyByEmail || user.handicapCalculateNotificationType.notifyByText) {
			// try {
			// sendEmailMessage(sendTo, subject, text, emailFromAddress);
			// } catch (MessagingException e) {
			// log.error("Error sending email.",e);
			// }
		}
	}

	public void scoreUpdated(Score score) {
		// TODO Auto-generated method stub

	}

	public void teetimeAdded(TeeTime teetime, Club club) {
		// TODO Auto-generated method stub

	}

	public void teetimeUpdated(TeeTimeParticipant participant, Club club) {
		// TODO Auto-generated method stub

	}

	public void teetimeDeleted(TeeTime teetime, Club club) {
		// TODO Auto-generated method stub

	}
}

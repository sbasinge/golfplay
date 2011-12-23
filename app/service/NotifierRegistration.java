package service;

import java.util.ArrayList;
import java.util.List;

import models.MembershipRequest;
import models.Score;
import models.TeeTime;
import models.TeeTimeParticipant;
import models.User;
import notifiers.MessageNotifier;

public class NotifierRegistration {

	public static List<MessageNotifier> registrants = new ArrayList<MessageNotifier>();
	
	public static void registrationCompleted(MembershipRequest membershipRequest) {
		for (MessageNotifier notifier : registrants) {
			notifier.registrationCompleted(membershipRequest);
		}
	}

	public static void membershipRejected(MembershipRequest membershipRequest) {
		for (MessageNotifier notifier : registrants) {
			notifier.membershipRejected(membershipRequest);
		}
		
	}

	public static void membershipAccepted(MembershipRequest membershipRequest) {
		for (MessageNotifier notifier : registrants) {
			notifier.membershipAccepted(membershipRequest);
		}
		
	}

	public static void handicapCalculated(User user) {
		for (MessageNotifier notifier : registrants) {
			notifier.handicapCalculated(user);
		}
		
	}
	
	public static void scoreUpdated(Score score) {
		for (MessageNotifier notifier : registrants) {
			notifier.scoreUpdated(score);
		}
		
	}

	public static void teetimeAdded(TeeTime teetime) {
		for (MessageNotifier notifier : registrants) {
			notifier.teetimeAdded(teetime);
		}
		
	}

	public static void teetimeUpdated(TeeTimeParticipant participant) {
		for (MessageNotifier notifier : registrants) {
			notifier.teetimeUpdated(participant);
		}
		
	}

	public static void teetimeDeleted(TeeTime teetime) {
		for (MessageNotifier notifier : registrants) {
			notifier.teetimeDeleted(teetime);
		}
		
	}

}

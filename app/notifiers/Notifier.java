package notifiers;

import java.util.ArrayList;
import java.util.List;

import models.MembershipRequest;
import models.Score;
import models.TeeTime;
import models.TeeTimeParticipant;
import models.User;
import play.libs.F.Promise;

public class Notifier implements MessageNotifier {

	private static List<MessageNotifier> registrants = new ArrayList<MessageNotifier>();
	private static Notifier instance;
	
	private Notifier() {
	}
	
	public static synchronized Notifier instance() {
		if (instance == null)
			instance = new Notifier();
		return instance;
	}
	
	public synchronized void addRegistrant(MessageNotifier registrant) {
		registrants.add(registrant);
	}
	
	public Promise registrationCompleted(MembershipRequest membershipRequest) {
		for (MessageNotifier notifier : registrants) {
			notifier.registrationCompleted(membershipRequest);
		}
		return null;
	}

	public Promise membershipRejected(MembershipRequest membershipRequest) {
		for (MessageNotifier notifier : registrants) {
			notifier.membershipRejected(membershipRequest);
		}
		return null;
	}

	public Promise membershipAccepted(MembershipRequest membershipRequest) {
		for (MessageNotifier notifier : registrants) {
			notifier.membershipAccepted(membershipRequest);
		}
		return null;
	}

	public Promise handicapCalculated(User user) {
		for (MessageNotifier notifier : registrants) {
			notifier.handicapCalculated(user);
		}
		return null;
	}
	
	public Promise scoreUpdated(Score score) {
		for (MessageNotifier notifier : registrants) {
			notifier.scoreUpdated(score);
		}
		return null;
	}

	public Promise teetimeAdded(TeeTime teetime) {
		for (MessageNotifier notifier : registrants) {
			notifier.teetimeAdded(teetime);
		}
		return null;
	}

	public Promise teetimeUpdated(TeeTimeParticipant participant) {
		for (MessageNotifier notifier : registrants) {
			notifier.teetimeUpdated(participant);
		}
		return null;
	}

	public Promise teetimeDeleted(TeeTime teetime) {
		for (MessageNotifier notifier : registrants) {
			notifier.teetimeDeleted(teetime);
		}
		return null;
	}

}

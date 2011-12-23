package notifiers;

import java.util.ArrayList;
import java.util.List;

import models.Club;
import models.MembershipRequest;
import models.Score;
import models.TeeTime;
import models.TeeTimeParticipant;
import models.User;

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
	
	public void registrationCompleted(MembershipRequest membershipRequest) {
		for (MessageNotifier notifier : registrants) {
			notifier.registrationCompleted(membershipRequest);
		}
	}

	public void membershipRejected(MembershipRequest membershipRequest) {
		for (MessageNotifier notifier : registrants) {
			notifier.membershipRejected(membershipRequest);
		}
		
	}

	public void membershipAccepted(MembershipRequest membershipRequest) {
		for (MessageNotifier notifier : registrants) {
			notifier.membershipAccepted(membershipRequest);
		}
		
	}

	public void handicapCalculated(User user) {
		for (MessageNotifier notifier : registrants) {
			notifier.handicapCalculated(user);
		}
		
	}
	
	public void scoreUpdated(Score score) {
		for (MessageNotifier notifier : registrants) {
			notifier.scoreUpdated(score);
		}
		
	}

	public void teetimeAdded(TeeTime teetime, Club club) {
		for (MessageNotifier notifier : registrants) {
			notifier.teetimeAdded(teetime, club);
		}
		
	}

	public void teetimeUpdated(TeeTimeParticipant participant, Club club) {
		for (MessageNotifier notifier : registrants) {
			notifier.teetimeUpdated(participant, club);
		}
		
	}

	public void teetimeDeleted(TeeTime teetime, Club club) {
		for (MessageNotifier notifier : registrants) {
			notifier.teetimeDeleted(teetime, club);
		}
		
	}

}

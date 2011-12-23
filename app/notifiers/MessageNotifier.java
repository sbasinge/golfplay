package notifiers;

import models.Club;
import models.MembershipRequest;
import models.Score;
import models.TeeTime;
import models.TeeTimeParticipant;
import models.User;


public interface MessageNotifier {

	public abstract void registrationCompleted(MembershipRequest membershipRequest);

	public abstract void membershipRejected(MembershipRequest membershipRequest);

	public abstract void membershipAccepted(MembershipRequest membershipRequest);

	public abstract void handicapCalculated(User user);
	
	public abstract void scoreUpdated(Score score);

	public abstract void teetimeAdded(TeeTime teetime, Club club);

	public abstract void teetimeUpdated(TeeTimeParticipant participant, Club club);

	public abstract void teetimeDeleted(TeeTime teetime, Club club);

}
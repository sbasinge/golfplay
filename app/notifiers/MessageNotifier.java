package notifiers;

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

	public abstract void teetimeAdded(TeeTime teetime);

	public abstract void teetimeUpdated(TeeTimeParticipant participant);

	public abstract void teetimeDeleted(TeeTime teetime);

}
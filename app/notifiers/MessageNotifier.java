package notifiers;

import models.MembershipRequest;
import models.Score;
import models.TeeTime;
import models.TeeTimeParticipant;
import models.User;
import play.libs.F.Promise;


public interface MessageNotifier {

	public abstract Promise registrationCompleted(MembershipRequest membershipRequest);

	public abstract Promise membershipRejected(MembershipRequest membershipRequest);

	public abstract Promise membershipAccepted(MembershipRequest membershipRequest);

	public abstract Promise handicapCalculated(User user);
	
	public abstract Promise scoreUpdated(Score score);

	public abstract Promise teetimeAdded(TeeTime teetime);

	public abstract Promise teetimeUpdated(TeeTimeParticipant participant);

	public abstract Promise teetimeDeleted(TeeTime teetime);

}
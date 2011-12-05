package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class MembershipRequest extends BaseEntity {

	private static final long serialVersionUID = 7778805947985040773L;
	@ManyToOne
	public User user;
	
	@ManyToOne
	public Club club;

	public MembershipRequest() {};
	public MembershipRequest(User user, Club club) {
		this.user = user;
		this.club = club;
	}
	
	
}

package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class SiteMessage extends BaseEntity {
	
	@OneToOne
	public User user;
	
	public boolean messageRead = false;
	
	public String messageText;
	
	public SiteMessage() {};
	public SiteMessage(User user, String message) {
		this.user = user;
		this.messageText = message;
	};
	

}

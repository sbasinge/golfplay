package models;

import javax.persistence.Entity;

@Entity
public class NotificationPreference extends BaseEntity {
	public boolean notifyByEmail;
	public boolean notifyByText;
	public boolean notifyOnSite;
	
	@Override
	public String toString() {
		return "NotificationPreference [notifyByEmail=" + notifyByEmail + ", notifyByText=" + notifyByText + ", notifyOnSite=" + notifyOnSite + "]";
	}
}

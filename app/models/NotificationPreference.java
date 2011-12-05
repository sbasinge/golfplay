package models;

import javax.persistence.Entity;

@Entity
public class NotificationPreference extends BaseEntity {
	public boolean notifyByEmail;
	public boolean notifyByText;
	public boolean notifyOnSite;
}

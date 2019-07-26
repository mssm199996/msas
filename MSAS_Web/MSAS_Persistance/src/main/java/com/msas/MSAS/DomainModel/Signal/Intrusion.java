package com.msas.MSAS.DomainModel.Signal;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.msas.MSAS.DomainModel.Others.SalleEvent;

@Entity
@Table(name = "INTRUSIONS")
public class Intrusion extends SalleEvent {

	private Set<Notification> notifications = new HashSet<Notification>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "intrusion", orphanRemoval = true)
	public Set<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(Set<Notification> notifications) {
		this.notifications = notifications;
	}

	public void addNotification(Notification notification) {
		this.notifications.add(notification);
		notification.setIntrusion(this);
	}

	public void removeNotification(Notification notification) {
		this.notifications.remove(notification);
		notification.setIntrusion(null);
	}
}

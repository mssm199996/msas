package com.msas.MSAS.DomainModel.Personnel;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.msas.MSAS.AdditionalBeans.FromComboBoxAddableBean;
import com.msas.MSAS.DomainModel.Authentification.Salle;
import com.msas.MSAS.DomainModel.Signal.Notification;

@Entity
@Table(name = "PERSONNELS_SURVEILLANCE")
public class PersonnelSurveillance implements FromComboBoxAddableBean {

	private Integer id;
	private String designation, numeroTelephone, email;

	private Set<Notification> notifications = new HashSet<Notification>();
	private Set<Salle> salles = new HashSet<Salle>();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getNumeroTelephone() {
		return numeroTelephone;
	}

	public void setNumeroTelephone(String numeroTelephone) {
		this.numeroTelephone = numeroTelephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "personnelSurveillance", orphanRemoval = true)
	public Set<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(Set<Notification> notifications) {
		this.notifications = notifications;
	}

	public void addNotification(Notification notification) {
		this.notifications.add(notification);
		notification.setPersonnelSurveillance(this);
	}

	public void removeNotification(Notification notification) {
		this.notifications.remove(notification);
		notification.setPersonnelSurveillance(null);
	}

	@Override
	public String toString() {
		return this.designation;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	public Set<Salle> getSalles() {
		return salles;
	}

	public void setSalles(Set<Salle> salles) {
		this.salles = salles;
	}

	public void addSalle(Salle salle) {
		this.salles.add(salle);
	}

	public void removeSalle(Salle salle) {
		this.salles.remove(salle);
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof PersonnelSurveillance
				&& this.getId() == (((PersonnelSurveillance) o).getId());
	}

	@Override
	public int hashCode() {
		if (this.getId() == null)
			return super.hashCode();

		return this.getId();
	}
}

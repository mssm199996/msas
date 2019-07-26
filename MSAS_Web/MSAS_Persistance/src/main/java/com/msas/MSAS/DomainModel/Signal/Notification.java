package com.msas.MSAS.DomainModel.Signal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.msas.MSAS.DomainModel.Personnel.PersonnelSurveillance;

@Entity
@Table(name = "NOTIFICATIONS")
public class Notification {

	private Integer id;
	private Intrusion intrusion;
	private PersonnelSurveillance personnelSurveillance;
	private TypeNotification typeNotification;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Intrusion getIntrusion() {
		return intrusion;
	}

	public void setIntrusion(Intrusion intrusion) {
		this.intrusion = intrusion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public PersonnelSurveillance getPersonnelSurveillance() {
		return personnelSurveillance;
	}

	public void setPersonnelSurveillance(PersonnelSurveillance personnelSurveillance) {
		this.personnelSurveillance = personnelSurveillance;
	}

	public TypeNotification getTypeNotification() {
		return typeNotification;
	}

	public void setTypeNotification(TypeNotification typeNotification) {
		this.typeNotification = typeNotification;
	}

	public static enum TypeNotification {
		SMS, EMAIL;
	}
}

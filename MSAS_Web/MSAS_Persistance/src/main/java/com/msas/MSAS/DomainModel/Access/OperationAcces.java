package com.msas.MSAS.DomainModel.Access;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import com.msas.MSAS.DomainModel.Others.SalleEvent;
import com.msas.MSAS.DomainModel.Personnel.Personnel;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class OperationAcces extends SalleEvent {

	private Personnel personnel;

	@ManyToOne(fetch = FetchType.LAZY)
	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}
}

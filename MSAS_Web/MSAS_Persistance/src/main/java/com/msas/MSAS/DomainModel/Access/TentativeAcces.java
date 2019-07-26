package com.msas.MSAS.DomainModel.Access;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TENTATIVES_ACCES")
public class TentativeAcces extends OperationAcces {

	private boolean autorised;

	public boolean isAutorised() {
		return autorised;
	}

	public void setAutorised(boolean autorised) {
		this.autorised = autorised;
	}
}

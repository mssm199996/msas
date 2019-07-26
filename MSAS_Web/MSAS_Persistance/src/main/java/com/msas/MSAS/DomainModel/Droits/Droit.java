package com.msas.MSAS.DomainModel.Droits;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.msas.MSAS.DomainModel.Authentification.Salle;
import com.msas.MSAS.DomainModel.Personnel.Personnel;

@Entity
@Table(name = "DROITS")
public class Droit {

	private Integer id;
	private Salle salle;
	private Personnel personnel;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Salle getSalle() {
		return salle;
	}

	public void setSalle(Salle salle) {
		this.salle = salle;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Droit
				&& this.getPersonnel().equals(((Droit) o).getPersonnel())
				&& this.getSalle().equals(((Droit) o).getSalle());
	}

	@Override
	public int hashCode() {
		int a = this.personnel.getId();
		int b = this.salle.getId();

		return (int) (0.5 * (a + b) * (a + b + 1) + b);
	}

	@Override
	public String toString() {
		return "Droit d'accès à la salle " + this.salle
				+ " concernant le personnel: " + this.personnel;
	}
}

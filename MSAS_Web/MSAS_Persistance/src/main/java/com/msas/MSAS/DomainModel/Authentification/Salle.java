package com.msas.MSAS.DomainModel.Authentification;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.msas.MSAS.AdditionalBeans.FromComboBoxAddableBean;
import com.msas.MSAS.DomainModel.Access.TentativeAcces;
import com.msas.MSAS.DomainModel.Droits.Droit;
import com.msas.MSAS.DomainModel.Personnel.PersonnelSurveillance;
import com.msas.MSAS.DomainModel.Signal.Intrusion;

@Entity
@Table(name = "SALLES")
public class Salle implements FromComboBoxAddableBean {

	private Integer id;
	private String designation;
	private Materiel materiel;

	private Set<Droit> droits = new HashSet<Droit>();
	private Set<Intrusion> instrusions = new HashSet<Intrusion>();
	private Set<TentativeAcces> tentativesAcces = new HashSet<TentativeAcces>();
	private Set<PersonnelSurveillance> personnelsSurveillance = new HashSet<PersonnelSurveillance>();

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

	@OneToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "ID_MATERIEL", unique = true)
	public Materiel getMateriel() {
		return materiel;
	}

	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;

		if (materiel != null)
			materiel.setSalle(this);
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "salle", orphanRemoval = true)
	public Set<Droit> getDroits() {
		return droits;
	}

	public void setDroits(Set<Droit> droits) {
		this.droits = droits;
	}

	public void addDroit(Droit droit) {
		this.droits.add(droit);
		droit.setSalle(this);
	}

	public void removeDroit(Droit droit) {
		this.droits.remove(droit);
		droit.setSalle(null);
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "salle")
	public Set<Intrusion> getInstrusions() {
		return instrusions;
	}

	public void setInstrusions(Set<Intrusion> instrusions) {
		this.instrusions = instrusions;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "salle")
	public Set<TentativeAcces> getTentativesAcces() {
		return tentativesAcces;
	}

	public void setTentativesAcces(Set<TentativeAcces> tentativesAcces) {
		this.tentativesAcces = tentativesAcces;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "salles")
	public Set<PersonnelSurveillance> getPersonnelsSurveillance() {
		return personnelsSurveillance;
	}

	public void setPersonnelsSurveillance(
			Set<PersonnelSurveillance> personnelsSurveillance) {
		this.personnelsSurveillance = personnelsSurveillance;
	}

	@Override
	public String toString() {
		return this.designation;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Salle && this.getId() == ((Salle) o).getId();
	}

	@Override
	public int hashCode() {
		if (this.getId() == null)
			return 0;

		return this.getId();
	}
}

package com.msas.MSAS.DomainModel.Personnel;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.msas.MSAS.DomainModel.Access.TentativeAcces;
import com.msas.MSAS.DomainModel.Categorizers.Grade;
import com.msas.MSAS.DomainModel.Categorizers.Metier;
import com.msas.MSAS.DomainModel.Droits.Droit;

@Entity
@Table(name = "PERSONNELS")
public class Personnel {

	private Integer id;
	private String nom, prenom, adresse, numeroTelephone;
	private Metier metier;
	private Grade grade;
	private PieceIdentitee pieceIdentitee;

	private Set<Droit> droits = new HashSet<Droit>();
	private Set<TentativeAcces> tentativesAcces = new HashSet<TentativeAcces>();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getNumeroTelephone() {
		return numeroTelephone;
	}

	public void setNumeroTelephone(String numeroTelephone) {
		this.numeroTelephone = numeroTelephone;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Metier getMetier() {
		return metier;
	}

	public void setMetier(Metier metier) {
		this.metier = metier;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_PIECE_IDENTITEE")
	public PieceIdentitee getPieceIdentitee() {
		return pieceIdentitee;
	}

	public void setPieceIdentitee(PieceIdentitee pieceIdentiee) {
		this.pieceIdentitee = pieceIdentiee;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "personnel")
	public Set<Droit> getDroits() {
		return droits;
	}

	public void setDroits(Set<Droit> droits) {
		this.droits = droits;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "personnel")
	public Set<TentativeAcces> getTentativesAcces() {
		return tentativesAcces;
	}

	public void setTentativesAcces(Set<TentativeAcces> tentativesAcces) {
		this.tentativesAcces = tentativesAcces;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Personnel
				&& this.getId() == ((Personnel) o).getId();
	}

	@Override
	public int hashCode() {
		if (this.getId() == null)
			return 0;
		return this.getId();
	}
}

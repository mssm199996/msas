package com.msas.MSAS.DomainModel.Others;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import com.msas.MSAS.DomainModel.Authentification.Salle;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class SalleEvent {

	private Integer id;
	private Salle salle;
	private LocalDate date;
	private LocalTime heure;

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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getHeure() {
		return heure;
	}

	public void setHeure(LocalTime heure) {
		this.heure = heure;
	}
}

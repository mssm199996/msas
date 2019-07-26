package com.msas.MSAS.gsm;

import java.time.LocalDate;
import java.time.LocalTime;

public class SMS {

	private Integer idSms;
	private String correspondantSms, contenuSms;
	private LocalDate dateSms;
	private LocalTime heureSms;

	public int getIdSms() {
		return this.idSms;
	}

	public void setIdSms(int idSms) {
		this.idSms = idSms;
	}

	public String getCorrespondantSms() {
		return this.correspondantSms;
	}

	public void setCorrespondantSms(String correspondantSms) {
		this.correspondantSms = correspondantSms;
	}

	public String getContenuSms() {
		return this.contenuSms;
	}

	public void setContenuSms(String contenuSms) {
		this.contenuSms = contenuSms;
	}

	public LocalDate getDateSms() {
		return this.dateSms;
	}

	public void setDateSms(LocalDate dateSms) {
		this.dateSms = dateSms;
	}

	public LocalTime getHeureSms() {
		return this.heureSms;
	}

	public void setHeureSms(LocalTime heureSms) {
		this.heureSms = heureSms;
	}

	@Override
	public String toString() {
		return this.correspondantSms + ": " + this.dateSms.toString() + " "
				+ this.heureSms.toString() + " => " + this.contenuSms;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof SMS && this.getIdSms() == ((SMS) o).getIdSms();
	}
}
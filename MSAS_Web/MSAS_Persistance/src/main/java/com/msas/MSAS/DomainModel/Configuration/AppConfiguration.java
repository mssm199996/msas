package com.msas.MSAS.DomainModel.Configuration;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CONFIGURATIONS")
public class AppConfiguration {

	private Integer id;
	private String emailIntrusions, mdpIntrusions, gsmPortNumberIntrusions;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmailIntrusions() {
		return emailIntrusions;
	}

	public void setEmailIntrusions(String emailIntrusions) {
		this.emailIntrusions = emailIntrusions;
	}

	public String getMdpIntrusions() {
		return mdpIntrusions;
	}

	public void setMdpIntrusions(String mdpIntrusions) {
		this.mdpIntrusions = mdpIntrusions;
	}

	public String getGsmPortNumberIntrusions() {
		return gsmPortNumberIntrusions;
	}

	public void setGsmPortNumberIntrusions(String gsmPortNumberIntrusions) {
		this.gsmPortNumberIntrusions = gsmPortNumberIntrusions;
	}
}

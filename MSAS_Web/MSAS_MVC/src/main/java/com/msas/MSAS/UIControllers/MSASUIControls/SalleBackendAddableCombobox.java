package com.msas.MSAS.UIControllers.MSASUIControls;

import com.msas.MSAS.DomainModel.Authentification.Salle;
import com.msas.MSAS.Repositories.SalleRepository;
import com.msas.MSAS.UIControllers.UsefulModules.MSAddableComboBoxes.MSBackendAddableComboBox;

public class SalleBackendAddableCombobox extends
		MSBackendAddableComboBox<Salle, SalleRepository> {

	private static final long serialVersionUID = -4650746319247101484L;

	public SalleBackendAddableCombobox(SalleRepository repository) {
		super(repository);
	}

	@Override
	public String getAddTitle() {
		return "Nouvelle salle";
	}

	@Override
	public String getHeader() {
		return "Veuillez inscrire le nom de la salle";
	}

	@Override
	public String getContent() {
		return "Nom";
	}

	@Override
	public Salle constructEntity() {
		return new Salle();
	}
}

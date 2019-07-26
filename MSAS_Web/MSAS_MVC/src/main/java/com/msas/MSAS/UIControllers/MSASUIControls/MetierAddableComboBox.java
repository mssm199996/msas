package com.msas.MSAS.UIControllers.MSASUIControls;

import java.util.List;

import com.msas.MSAS.DomainModel.Categorizers.Metier;
import com.msas.MSAS.Repositories.MetierRepository;
import com.msas.MSAS.UIControllers.UsefulModules.MSAddableComboBoxes.MSAddableComboBox;

public class MetierAddableComboBox extends
		MSAddableComboBox<Metier, MetierRepository> {

	private static final long serialVersionUID = 5761668257068910148L;
	
	public MetierAddableComboBox(List<Metier> dataset,
			MetierRepository repository) {
		super(dataset, repository);
	}

	@Override
	public String getAddTitle() {
		return "Nouveau métier";
	}

	@Override
	public String getHeader() {
		return "Veuillez inscrire le nom du métier";
	}

	@Override
	public String getContent() {
		return "Désignation";
	}

	@Override
	public Metier constructEntity() {
		return new Metier();
	}
}

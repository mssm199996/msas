package com.msas.MSAS.UIControllers.MSASUIControls;

import com.msas.MSAS.DomainModel.Authentification.Salle;
import com.msas.MSAS.DomainModel.Droits.Droit;
import com.msas.MSAS.DomainModel.Personnel.Personnel;
import com.msas.MSAS.Repositories.SalleRepository;
import com.msas.MSAS.UIControllers.UsefulModules.Button.MSAffector;
import com.vaadin.flow.component.grid.Grid;

public class PersonnelDroitAffector extends
		MSAffector<Salle, Personnel, SalleRepository> {

	private static final long serialVersionUID = -4847958464727464368L;

	public PersonnelDroitAffector(Grid<Personnel> childsGrid,
			SalleBackendAddableCombobox salleAddableCombobox) {
		super(TypeAffectation.ATTACH, "Affecter un droit d'accès", childsGrid,
				salleAddableCombobox);
	}

	@Override
	public String getNomFonction() {
		return "G.Droits d'accés";
	}

	@Override
	public String getNomOperation() {
		return "Affectation d'un personnel à une salle";
	}

	@Override
	public void onLinking(Salle parent, Personnel child,
			SalleRepository parentRepository) {

		Droit droit = new Droit();
		droit.setPersonnel(child);
		droit.setSalle(parent);

		parent.addDroit(droit);
	}

	@Override
	public Salle fethParentWithChilds(Salle parent,
			SalleRepository parentRepository) {

		return parentRepository.findByEntityWithDroits(parent);
	}
}

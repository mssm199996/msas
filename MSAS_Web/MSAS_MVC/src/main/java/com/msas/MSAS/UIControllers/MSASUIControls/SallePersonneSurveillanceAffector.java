package com.msas.MSAS.UIControllers.MSASUIControls;

import com.msas.MSAS.DomainModel.Authentification.Salle;
import com.msas.MSAS.DomainModel.Personnel.PersonnelSurveillance;
import com.msas.MSAS.Repositories.PersonnelSurveillanceRepository;
import com.msas.MSAS.UIControllers.UsefulModules.Button.MSAffector;
import com.msas.MSAS.UIControllers.UsefulModules.MSAddableComboBoxes.MSAddableComboBox;
import com.vaadin.flow.component.grid.Grid;

public class SallePersonneSurveillanceAffector
		extends
		MSAffector<PersonnelSurveillance, Salle, PersonnelSurveillanceRepository> {

	private static final long serialVersionUID = -7960721196992755874L;

	public SallePersonneSurveillanceAffector(
			Grid<Salle> childsGrid,
			MSAddableComboBox<PersonnelSurveillance, PersonnelSurveillanceRepository> parentHolder) {
		super(TypeAffectation.ATTACH,
				"Affecter à un personnel de surveillance", childsGrid,
				parentHolder);
	}

	@Override
	public PersonnelSurveillance fethParentWithChilds(
			PersonnelSurveillance parent,
			PersonnelSurveillanceRepository parentRepository) {

		return parentRepository.findByEntityWithSalles(parent);
	}

	@Override
	public void onLinking(PersonnelSurveillance parent, Salle child,
			PersonnelSurveillanceRepository parentRepository) {
		parent.addSalle(child);
	}

	@Override
	public String getNomFonction() {
		return "Mise à jour de la salle";
	}

	@Override
	public String getNomOperation() {
		return "Affectation d'une / des salle(s) à un personnel de surveillance";
	}
}

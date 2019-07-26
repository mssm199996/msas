package com.msas.MSAS.UIControllers.MSASUIControls;

import com.msas.MSAS.DomainModel.Personnel.PersonnelSurveillance;
import com.msas.MSAS.Repositories.PersonnelSurveillanceRepository;
import com.msas.MSAS.UIControllers.UsefulModules.MSAddableComboBoxes.MSBackendAddableComboBox;

public class PersonnelSurveillanceBackendAddableCombobox
		extends
		MSBackendAddableComboBox<PersonnelSurveillance, PersonnelSurveillanceRepository> {

	private static final long serialVersionUID = -7805870090712012515L;

	public PersonnelSurveillanceBackendAddableCombobox(
			PersonnelSurveillanceRepository repository) {
		super(repository);
	}

	@Override
	public String getAddTitle() {
		return "Nouveau personnel de surveillance";
	}

	@Override
	public String getHeader() {
		return "Désignation du personnel de surveillance";
	}

	@Override
	public String getContent() {
		return "Désignation";
	}

	@Override
	public PersonnelSurveillance constructEntity() {
		return new PersonnelSurveillance();
	}
}

package com.msas.MSAS.UIControllers.Platform.Routes.Personnel;

import org.springframework.beans.factory.annotation.Autowired;

import com.msas.MSAS.DomainModel.Personnel.PersonnelSurveillance;
import com.msas.MSAS.Repositories.PersonnelSurveillanceRepository;
import com.msas.MSAS.UIControllers.Cruds.Personnel.PersonnelSurveillanceCrud;
import com.msas.MSAS.UIControllers.Platform.MSASCrudPlatform;
import com.vaadin.flow.router.Route;

@Route("personnelSurveillanceList")
public class PersonnelSurveillancePlatform
		extends
		MSASCrudPlatform<PersonnelSurveillance, PersonnelSurveillanceRepository, PersonnelSurveillanceCrud> {

	private static final long serialVersionUID = 5200181407423936483L;

	@Autowired
	private PersonnelSurveillanceCrud personnelSurveillanceCrud;

	@Override
	protected PersonnelSurveillanceCrud constructMsCrud() {
		return this.personnelSurveillanceCrud;
	}
}

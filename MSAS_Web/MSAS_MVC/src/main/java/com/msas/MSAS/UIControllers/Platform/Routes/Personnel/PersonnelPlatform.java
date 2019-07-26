package com.msas.MSAS.UIControllers.Platform.Routes.Personnel;

import org.springframework.beans.factory.annotation.Autowired;

import com.msas.MSAS.DomainModel.Personnel.Personnel;
import com.msas.MSAS.Repositories.PersonnelRepository;
import com.msas.MSAS.UIControllers.Cruds.Personnel.PersonnelCrud;
import com.msas.MSAS.UIControllers.Platform.MSASCrudPlatform;
import com.vaadin.flow.router.Route;

@Route("personnelList")
public class PersonnelPlatform extends
		MSASCrudPlatform<Personnel, PersonnelRepository, PersonnelCrud> {

	private static final long serialVersionUID = 8376041505277756835L;

	@Autowired
	private PersonnelCrud personnelCrud;

	@Override
	protected PersonnelCrud constructMsCrud() {
		return personnelCrud;
	}
}

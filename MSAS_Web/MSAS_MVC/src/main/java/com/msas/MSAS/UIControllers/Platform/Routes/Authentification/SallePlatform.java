package com.msas.MSAS.UIControllers.Platform.Routes.Authentification;

import org.springframework.beans.factory.annotation.Autowired;

import com.msas.MSAS.DomainModel.Authentification.Salle;
import com.msas.MSAS.Repositories.SalleRepository;
import com.msas.MSAS.UIControllers.Cruds.Personnel.SalleCrud;
import com.msas.MSAS.UIControllers.Platform.MSASCrudPlatform;
import com.vaadin.flow.router.Route;

@Route("salleList")
public class SallePlatform extends
		MSASCrudPlatform<Salle, SalleRepository, SalleCrud> {

	private static final long serialVersionUID = -7330877217115565759L;

	@Autowired
	private SalleRepository salleRepository;

	@Autowired
	private SalleCrud salleCrud;

	@Override
	protected SalleCrud constructMsCrud() {
		return this.salleCrud;
	}
}

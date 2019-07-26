package com.msas.MSAS.UIControllers.Platform.Routes.Authentification;

import org.springframework.beans.factory.annotation.Autowired;

import com.msas.MSAS.DomainModel.Authentification.Materiel;
import com.msas.MSAS.Repositories.MaterielRepository;
import com.msas.MSAS.Repositories.SalleRepository;
import com.msas.MSAS.UIControllers.Cruds.Personnel.MaterielCrud;
import com.msas.MSAS.UIControllers.Platform.MSASCrudPlatform;
import com.vaadin.flow.router.Route;

@Route("materielList")
public class MaterielPlatform extends
		MSASCrudPlatform<Materiel, MaterielRepository, MaterielCrud> {

	private static final long serialVersionUID = 8613921466909105600L;

	@Autowired
	private MaterielRepository materielRepository;

	@Autowired
	private SalleRepository salleRepository;

	@Autowired
	private MaterielCrud materielCrud;

	@Override
	protected MaterielCrud constructMsCrud() {
		return this.materielCrud;
	}
}

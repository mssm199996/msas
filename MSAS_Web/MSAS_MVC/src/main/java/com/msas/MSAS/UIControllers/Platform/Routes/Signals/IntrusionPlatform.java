package com.msas.MSAS.UIControllers.Platform.Routes.Signals;

import org.springframework.beans.factory.annotation.Autowired;

import com.msas.MSAS.DomainModel.Signal.Intrusion;
import com.msas.MSAS.Repositories.IntrusionRepository;
import com.msas.MSAS.UIControllers.Cruds.Signal.IntrusionCrud;
import com.msas.MSAS.UIControllers.Platform.MSASCrudPlatform;
import com.vaadin.flow.router.Route;

@Route("intrusionList")
public class IntrusionPlatform extends
		MSASCrudPlatform<Intrusion, IntrusionRepository, IntrusionCrud> {
	
	private static final long serialVersionUID = 2942595839135089458L;

	@Autowired
	private IntrusionCrud intrusionCrud;

	@Override
	protected IntrusionCrud constructMsCrud() {
		return this.intrusionCrud;
	}
}

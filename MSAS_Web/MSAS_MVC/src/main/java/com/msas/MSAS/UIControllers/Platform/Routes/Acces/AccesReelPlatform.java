package com.msas.MSAS.UIControllers.Platform.Routes.Acces;

import org.springframework.beans.factory.annotation.Autowired;

import com.msas.MSAS.DomainModel.Access.AccesReel;
import com.msas.MSAS.Repositories.AccesReelRepository;
import com.msas.MSAS.UIControllers.Cruds.Acces.AccesReelCrud;
import com.vaadin.flow.router.Route;

@Route("accesReelsList")
public class AccesReelPlatform extends
		OperationAccesPlatform<AccesReel, AccesReelRepository, AccesReelCrud> {

	private static final long serialVersionUID = 6180899873910601715L;

	@Autowired
	private AccesReelCrud accesReelCrud;
	
	@Override
	protected AccesReelCrud constructMsCrud() {
		return this.accesReelCrud;
	}
}

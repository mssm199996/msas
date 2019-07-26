package com.msas.MSAS.UIControllers.Platform.Routes.Acces;

import org.springframework.beans.factory.annotation.Autowired;

import com.msas.MSAS.DomainModel.Access.TentativeAcces;
import com.msas.MSAS.Repositories.TentativeAccesRepository;
import com.msas.MSAS.UIControllers.Cruds.Acces.TentativeAccesCrud;
import com.vaadin.flow.router.Route;

@Route("tentativeAccesList")
public class TentativeAccesPlatform
		extends
		OperationAccesPlatform<TentativeAcces, TentativeAccesRepository, TentativeAccesCrud> {

	private static final long serialVersionUID = -6244205327343688885L;

	@Autowired
	private TentativeAccesCrud tentativeAccesCrud;

	@Override
	protected TentativeAccesCrud constructMsCrud() {
		return this.tentativeAccesCrud;
	}
}

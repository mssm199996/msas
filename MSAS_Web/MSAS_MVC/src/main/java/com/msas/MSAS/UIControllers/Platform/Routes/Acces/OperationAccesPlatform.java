package com.msas.MSAS.UIControllers.Platform.Routes.Acces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msas.MSAS.DomainModel.Access.OperationAcces;
import com.msas.MSAS.UIControllers.Cruds.Acces.OperationAccesCrud;
import com.msas.MSAS.UIControllers.Platform.MSASCrudPlatform;

public abstract class OperationAccesPlatform<T extends OperationAcces, R extends JpaRepository<T, Integer>, C extends OperationAccesCrud<T, R, ?>>
		extends MSASCrudPlatform<T, R, C> {

	private static final long serialVersionUID = 2007351929931374079L;

}

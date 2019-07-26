package com.msas.MSAS.UIControllers.Cruds.Acces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msas.MSAS.DomainModel.Access.OperationAcces;
import com.msas.MSAS.UIControllers.Cruds.Others.SalleEventCrud;
import com.msas.MSAS.UIControllers.Grids.OperationAccess.OperationAccesGrid;

public abstract class OperationAccesCrud<T extends OperationAcces, R extends JpaRepository<T, ?>, G extends OperationAccesGrid<T, R>>
		extends SalleEventCrud<T, R, G> {

	private static final long serialVersionUID = -6262188726404959332L;

}

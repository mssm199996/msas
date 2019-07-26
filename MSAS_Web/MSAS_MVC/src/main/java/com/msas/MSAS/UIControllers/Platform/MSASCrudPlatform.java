package com.msas.MSAS.UIControllers.Platform;

import javax.annotation.PostConstruct;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msas.MSAS.UIControllers.UsefulModules.MSCruds.MSCrud;

public abstract class MSASCrudPlatform<T, R extends JpaRepository<T, ?>, C extends MSCrud<T, R, ?>>
		extends MSASPlatform {

	public static final long serialVersionUID = 2780010914854527561L;

	private C crud;

	@PostConstruct
	private void onPlateformAllocatedCallback() {
		this.crud = this.constructMsCrud();
		this.verticalLayout.add(this.crud);
	}

	protected abstract C constructMsCrud();
}

package com.msas.MSAS.UIControllers.Cruds.Personnel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.msas.MSAS.DomainModel.Authentification.Materiel;
import com.msas.MSAS.Repositories.MaterielRepository;
import com.msas.MSAS.Repositories.SalleRepository;
import com.msas.MSAS.UIControllers.Grids.Personnel.MaterielGrid;
import com.msas.MSAS.UIControllers.UsefulModules.MSCruds.MSCrud;
import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class MaterielCrud extends
		MSCrud<Materiel, MaterielRepository, MaterielGrid> {

	private static final long serialVersionUID = -4961947697514189516L;

	@Autowired
	private MaterielRepository materielRepository;

	@Autowired
	private SalleRepository salleRepository;

	@Override
	protected MaterielGrid constructGrid() {
		return new MaterielGrid(this.materielRepository, this.salleRepository);
	}

	@Override
	protected Map<AbstractSinglePropertyField<?, ?>, String> filters() {
		return new HashMap<>();
	}

	@Override
	protected List<Materiel> fetchEntitiesFromDatabase(Pageable pageable) {
		return this.materielRepository.findAllWithSalle(pageable);
	}

	@Override
	protected Integer countExpectedFetch() {
		return (int) this.materielRepository.count();
	}

	@Override
	protected Map<AbstractSinglePropertyField<?, ?>, VaadinIcon> filtersIcons() {
		return new HashMap<>();
	}
}

package com.msas.MSAS.UIControllers.MSASUIControls;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.msas.MSAS.DomainModel.Droits.Droit;
import com.msas.MSAS.DomainModel.Personnel.Personnel;
import com.msas.MSAS.Repositories.DroitRepository;
import com.msas.MSAS.UIControllers.Grids.Droits.DroitGrid;
import com.msas.MSAS.UIControllers.UsefulModules.GridCells.MSChildsAsTable;
import com.msas.MSAS.UIControllers.UsefulModules.Grids.MSGrid;
import com.vaadin.flow.component.icon.VaadinIcon;

public class PersonnelDroitAsChildTableRenderer extends
		MSChildsAsTable<Personnel, Droit, DroitRepository> {

	private static final long serialVersionUID = 8903242567002479928L;

	public PersonnelDroitAsChildTableRenderer() {
		super("success", VaadinIcon.BUILDING_O);
	}

	@Override
	protected String getDialogTitle() {
		return "Droit d'accés d'un personnel";
	}

	@Override
	protected String getDialogHeaderText() {
		return "";
	}

	@Override
	protected VaadinIcon getDialogHeaderIcon() {
		return VaadinIcon.BUILDING;
	}

	@Override
	public String getColumnKey() {
		return "personnel.droits";
	}

	@Override
	protected Integer fetchChildsSizeFromDatabase(Personnel parent,
			DroitRepository repository) {
		return repository.countBySalleAndPersonnel(null, parent);
	}

	@Override
	protected MSGrid<Droit, DroitRepository> instanciateGrid(
			DroitRepository repository) {
		return new DroitGrid(repository);
	}

	@Override
	protected List<Droit> fetchChildsFromDatabase(Personnel parent,
			DroitRepository repository, Pageable pageable) {
		return repository.findBySalleAndPersonnelWithSalleAndPersonnel(null,
				parent, pageable);
	}
}

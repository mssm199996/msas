package com.msas.MSAS.UIControllers.MSASUIControls;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.msas.MSAS.DomainModel.Authentification.Salle;
import com.msas.MSAS.DomainModel.Droits.Droit;
import com.msas.MSAS.Repositories.DroitRepository;
import com.msas.MSAS.UIControllers.Grids.Droits.DroitGrid;
import com.msas.MSAS.UIControllers.UsefulModules.GridCells.MSChildsAsTable;
import com.msas.MSAS.UIControllers.UsefulModules.Grids.MSGrid;
import com.vaadin.flow.component.icon.VaadinIcon;

public class SalleDroitAsChildTableRenderer extends
		MSChildsAsTable<Salle, Droit, DroitRepository> {

	private static final long serialVersionUID = -8937980102582436690L;

	public SalleDroitAsChildTableRenderer() {
		super("success", VaadinIcon.USER);
	}

	@Override
	protected MSGrid<Droit, DroitRepository> instanciateGrid(
			DroitRepository repository) {

		return new DroitGrid(repository);
	}

	@Override
	protected List<Droit> fetchChildsFromDatabase(Salle parent,
			DroitRepository repository, Pageable pageable) {

		return repository.findBySalleAndPersonnelWithSalleAndPersonnel(parent,
				null, pageable);
	}

	@Override
	protected Integer fetchChildsSizeFromDatabase(Salle parent,
			DroitRepository repository) {

		return repository.countBySalleAndPersonnel(parent, null);
	}

	@Override
	protected String getDialogTitle() {
		return "Personnel ayant le droit d'accès";
	}

	@Override
	protected String getDialogHeaderText() {
		return "";
	}

	@Override
	protected VaadinIcon getDialogHeaderIcon() {
		return VaadinIcon.USER;
	}

	@Override
	public String getColumnKey() {
		return "salle.droits";
	}
}

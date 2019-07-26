package com.msas.MSAS.UIControllers.MSASUIControls;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.msas.MSAS.DomainModel.Authentification.Salle;
import com.msas.MSAS.DomainModel.Personnel.PersonnelSurveillance;
import com.msas.MSAS.Repositories.PersonnelSurveillanceRepository;
import com.msas.MSAS.UIControllers.Grids.Personnel.PersonnelSurveillanceGrid;
import com.msas.MSAS.UIControllers.UsefulModules.GridCells.MSChildsAsTable;
import com.msas.MSAS.UIControllers.UsefulModules.Grids.MSGrid;
import com.vaadin.flow.component.icon.VaadinIcon;

public class SallePersonnelSurveillanceAsChildTableRenderer
		extends
		MSChildsAsTable<Salle, PersonnelSurveillance, PersonnelSurveillanceRepository> {

	private static final long serialVersionUID = -7009252337914884413L;

	public SallePersonnelSurveillanceAsChildTableRenderer() {
		super("warning", VaadinIcon.USER_STAR);
	}

	@Override
	protected MSGrid<PersonnelSurveillance, PersonnelSurveillanceRepository> instanciateGrid(
			PersonnelSurveillanceRepository repository) {
		return new PersonnelSurveillanceGrid(repository);
	}

	@Override
	protected List<PersonnelSurveillance> fetchChildsFromDatabase(Salle parent,
			PersonnelSurveillanceRepository repository, Pageable pageable) {

		return repository.findBySalles(parent);
	}

	@Override
	protected Integer fetchChildsSizeFromDatabase(Salle parent,
			PersonnelSurveillanceRepository repository) {

		return repository.countBySalles(parent);
	}

	@Override
	protected String getDialogTitle() {
		return "Personnel de surveillance chargé de la salle";
	}

	@Override
	protected String getDialogHeaderText() {
		return "";
	}

	@Override
	protected VaadinIcon getDialogHeaderIcon() {
		return VaadinIcon.USER_STAR;
	}

	@Override
	public String getColumnKey() {
		return "salle.personnelsSurveillance";
	}
}

package com.msas.MSAS.UIControllers.Cruds.Personnel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.msas.MSAS.DomainModel.Personnel.PersonnelSurveillance;
import com.msas.MSAS.Repositories.PersonnelSurveillanceRepository;
import com.msas.MSAS.UIControllers.Forms.Personnel.PersonnelSurveillanceForm;
import com.msas.MSAS.UIControllers.Grids.Personnel.PersonnelSurveillanceGrid;
import com.msas.MSAS.UIControllers.UsefulModules.Forms.MSForm;
import com.msas.MSAS.UIControllers.UsefulModules.Grids.MSEditableDeletableGrid;
import com.msas.MSAS.UIControllers.UsefulModules.MSCruds.MSAddableCrud;
import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class PersonnelSurveillanceCrud extends
		MSAddableCrud<PersonnelSurveillance, PersonnelSurveillanceRepository> {

	private static final long serialVersionUID = 1914567650868003076L;

	@Autowired
	private PersonnelSurveillanceForm personnelSurveillanceForm;

	@Autowired
	private PersonnelSurveillanceRepository personnelServeillanceRepository;

	@Override
	protected String addButtonLabel() {
		return "Nouveau personnel de surveillance";
	}

	@Override
	protected MSForm<PersonnelSurveillance> getForm() {
		return this.personnelSurveillanceForm;
	}

	@Override
	protected Map<AbstractSinglePropertyField<?, ?>, String> filters() {
		return new HashMap<>();
	}

	@Override
	protected Map<AbstractSinglePropertyField<?, ?>, VaadinIcon> filtersIcons() {
		return new HashMap<>();
	}

	@Override
	protected List<PersonnelSurveillance> fetchEntitiesFromDatabase(
			Pageable pageable) {
		return this.personnelServeillanceRepository.findAll();
	}

	@Override
	protected Integer countExpectedFetch() {
		return (int) this.personnelServeillanceRepository.count();
	}

	@Override
	protected MSEditableDeletableGrid<PersonnelSurveillance, PersonnelSurveillanceRepository> constructGrid() {
		return new PersonnelSurveillanceGrid(
				this.personnelServeillanceRepository);
	}
}

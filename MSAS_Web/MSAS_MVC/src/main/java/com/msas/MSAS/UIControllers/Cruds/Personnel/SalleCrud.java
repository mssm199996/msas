package com.msas.MSAS.UIControllers.Cruds.Personnel;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.msas.MSAS.DomainModel.Authentification.Salle;
import com.msas.MSAS.Repositories.DroitRepository;
import com.msas.MSAS.Repositories.PersonnelSurveillanceRepository;
import com.msas.MSAS.Repositories.SalleRepository;
import com.msas.MSAS.UIControllers.Forms.Personnel.SalleForm;
import com.msas.MSAS.UIControllers.Grids.Personnel.SalleGrid;
import com.msas.MSAS.UIControllers.MSASUIControls.PersonnelSurveillanceBackendAddableCombobox;
import com.msas.MSAS.UIControllers.MSASUIControls.SallePersonneSurveillanceAffector;
import com.msas.MSAS.UIControllers.MSASUIControls.SallePersonnelSurveillanceDetacher;
import com.msas.MSAS.UIControllers.Others.IconsHolder;
import com.msas.MSAS.UIControllers.UsefulModules.Forms.MSForm;
import com.msas.MSAS.UIControllers.UsefulModules.MSCruds.MSAddableCrud;
import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class SalleCrud extends MSAddableCrud<Salle, SalleRepository> {

	private static final long serialVersionUID = -6119750212643237237L;

	private TextField designationFilter, numeroSerieMaterielFilter;

	@Autowired
	private SalleForm salleForm;

	@Autowired
	private SalleRepository salleRepository;

	@Autowired
	private PersonnelSurveillanceRepository personnelSurveillanceRepository;

	@Autowired
	private DroitRepository droitRepository;

	@Override
	protected String addButtonLabel() {
		return "Nouvelle salle";
	}

	@Override
	protected MSForm<Salle> getForm() {
		return this.salleForm;
	}

	@Override
	protected Map<AbstractSinglePropertyField<?, ?>, String> filters() {
		this.designationFilter = new TextField();
		this.numeroSerieMaterielFilter = new TextField();

		Map<AbstractSinglePropertyField<?, ?>, String> result = new LinkedHashMap<AbstractSinglePropertyField<?, ?>, String>();
		result.put(this.designationFilter, "Désignation");
		result.put(this.numeroSerieMaterielFilter,
				"N° de série du matériel associé");

		return result;
	}

	@Override
	protected List<Salle> fetchEntitiesFromDatabase(Pageable pageable) {
		String designation = this.designationFilter.getValue();
		String numeroSerieMateriel = this.numeroSerieMaterielFilter.getValue();

		return this.salleRepository.findAll(designation, numeroSerieMateriel,
				pageable);

	}

	@Override
	protected Integer countExpectedFetch() {
		String designation = this.designationFilter.getValue();
		String numeroSerieMateriel = this.numeroSerieMaterielFilter.getValue();

		return this.salleRepository.count(designation, numeroSerieMateriel);
	}

	@Override
	protected SalleGrid constructGrid() {
		return new SalleGrid(this.salleRepository, this.droitRepository,
				this.personnelSurveillanceRepository);
	}

	@Override
	protected Map<AbstractSinglePropertyField<?, ?>, VaadinIcon> filtersIcons() {
		HashMap<AbstractSinglePropertyField<?, ?>, VaadinIcon> result = new LinkedHashMap<>();
		result.put(this.designationFilter,
				IconsHolder.extractIcon("salle.designation"));
		result.put(this.numeroSerieMaterielFilter,
				IconsHolder.extractIcon("materiel.serialNumber"));

		return result;
	}

	@Override
	protected Set<Component> getRightSideToolBarComponents() {
		SallePersonneSurveillanceAffector sallePersonnelSurveillanceAffector = new SallePersonneSurveillanceAffector(
				this.grid, new PersonnelSurveillanceBackendAddableCombobox(
						this.personnelSurveillanceRepository));

		SallePersonnelSurveillanceDetacher sallePersonnelSurveillanceDetacheur = new SallePersonnelSurveillanceDetacher(
				this.grid, new PersonnelSurveillanceBackendAddableCombobox(
						this.personnelSurveillanceRepository));

		Set<Component> result = super.getRightSideToolBarComponents();
		result.add(sallePersonnelSurveillanceAffector);
		result.add(sallePersonnelSurveillanceDetacheur);

		return result;
	}
}

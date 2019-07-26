package com.msas.MSAS.UIControllers.Cruds.Personnel;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.msas.MSAS.DomainModel.Categorizers.Grade;
import com.msas.MSAS.DomainModel.Categorizers.Metier;
import com.msas.MSAS.DomainModel.Personnel.Personnel;
import com.msas.MSAS.Repositories.DroitRepository;
import com.msas.MSAS.Repositories.GradeRepository;
import com.msas.MSAS.Repositories.MetierRepository;
import com.msas.MSAS.Repositories.PersonnelRepository;
import com.msas.MSAS.Repositories.SalleRepository;
import com.msas.MSAS.UIControllers.Forms.Personnel.PersonnelForm;
import com.msas.MSAS.UIControllers.Grids.Personnel.PersonnelGrid;
import com.msas.MSAS.UIControllers.MSASUIControls.PersonnelDroitAffector;
import com.msas.MSAS.UIControllers.MSASUIControls.PersonnelDroitDetacher;
import com.msas.MSAS.UIControllers.MSASUIControls.SalleBackendAddableCombobox;
import com.msas.MSAS.UIControllers.Others.IconsHolder;
import com.msas.MSAS.UIControllers.UsefulModules.Forms.MSForm;
import com.msas.MSAS.UIControllers.UsefulModules.MSComboBox.MSComboBox;
import com.msas.MSAS.UIControllers.UsefulModules.MSCruds.MSAddableCrud;
import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class PersonnelCrud extends
		MSAddableCrud<Personnel, PersonnelRepository> {

	private static final long serialVersionUID = -2309551420027304264L;

	private TextField numeroSerieFilter;
	private TextField nomPrenomFilter;
	private MSComboBox<Metier> metierFilter;
	private MSComboBox<Grade> gradeFilter;

	@Autowired
	private PersonnelForm personnelForm;

	@Autowired
	private PersonnelRepository personnelRepository;

	@Autowired
	private GradeRepository gradeRepository;

	@Autowired
	private MetierRepository metierRepository;

	@Autowired
	private SalleRepository salleRepository;

	@Autowired
	private DroitRepository droitRepository;

	@Autowired
	private List<Metier> metiersList;

	@Autowired
	private List<Grade> gradesList;

	@Override
	protected PersonnelGrid constructGrid() {
		return new PersonnelGrid(this.personnelRepository, this.droitRepository);
	}

	@Override
	protected Map<AbstractSinglePropertyField<?, ?>, String> filters() {
		this.numeroSerieFilter = new TextField();
		this.nomPrenomFilter = new TextField();
		this.metierFilter = new MSComboBox<>(this.metiersList);
		this.gradeFilter = new MSComboBox<>(this.gradesList);

		HashMap<AbstractSinglePropertyField<?, ?>, String> result = new LinkedHashMap<AbstractSinglePropertyField<?, ?>, String>();
		result.put(this.numeroSerieFilter, "N° de série");
		result.put(this.nomPrenomFilter, "Nom et prénom");
		result.put(this.metierFilter, "Métier");
		result.put(this.gradeFilter, "Grade");

		return result;
	}

	@Override
	protected String addButtonLabel() {
		return "Nouveau personnel";
	}

	@Override
	protected MSForm<Personnel> getForm() {
		return this.personnelForm;
	}

	@Override
	protected List<Personnel> fetchEntitiesFromDatabase(Pageable pageable) {
		String nomPrenom = this.nomPrenomFilter.getValue();
		String serialNumber = this.numeroSerieFilter.getValue();

		Metier metier = this.metierFilter.getValue();
		Grade grade = this.gradeFilter.getValue();

		return this.personnelRepository.findAll(nomPrenom, serialNumber,
				metier, grade, pageable);
	}

	@Override
	protected Integer countExpectedFetch() {
		String nomPrenom = this.nomPrenomFilter.getValue();
		String serialNumber = this.numeroSerieFilter.getValue();

		Metier metier = this.metierFilter.getValue();
		Grade grade = this.gradeFilter.getValue();

		return this.personnelRepository.count(nomPrenom, serialNumber, metier,
				grade);
	}

	@Override
	protected Map<AbstractSinglePropertyField<?, ?>, VaadinIcon> filtersIcons() {
		Map<AbstractSinglePropertyField<?, ?>, VaadinIcon> result = new HashMap<>();
		result.put(this.metierFilter,
				IconsHolder.extractIcon("metier.designation"));
		result.put(this.numeroSerieFilter, IconsHolder
				.extractIcon("personnel.pieceIdentitee.serialNumber"));
		result.put(this.nomPrenomFilter,
				IconsHolder.extractIcon("personnel.nom"));
		result.put(this.gradeFilter,
				IconsHolder.extractIcon("grade.designation"));

		return result;
	}

	@Override
	protected Set<Component> getRightSideToolBarComponents() {
		PersonnelDroitAffector personnelDroitAffector = new PersonnelDroitAffector(
				this.grid,
				new SalleBackendAddableCombobox(this.salleRepository));

		PersonnelDroitDetacher personnelDroitDetacher = new PersonnelDroitDetacher(
				this.grid,
				new SalleBackendAddableCombobox(this.salleRepository));

		Set<Component> result = super.getRightSideToolBarComponents();
		result.add(personnelDroitAffector);
		result.add(personnelDroitDetacher);

		return result;
	}
}

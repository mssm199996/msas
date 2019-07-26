package com.msas.MSAS.UIControllers.Forms.Personnel;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.msas.MSAS.DomainModel.Authentification.Materiel;
import com.msas.MSAS.DomainModel.Authentification.Salle;
import com.msas.MSAS.Repositories.MaterielRepository;
import com.msas.MSAS.Repositories.SalleRepository;
import com.msas.MSAS.UIControllers.MSASUIControls.MaterielBackendComboBox;
import com.msas.MSAS.UIControllers.Others.IconsHolder;
import com.msas.MSAS.UIControllers.UsefulModules.Forms.MSForm;
import com.msas.MSAS.UIControllers.UsefulModules.MSComboBox.MSBackendComboBox;
import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class SalleForm extends MSForm<Salle> {

	private static final long serialVersionUID = -4102905074480976369L;

	private TextField designation;
	private MSBackendComboBox<Materiel, MaterielRepository> materiel;

	@Autowired
	private SalleRepository salleRepository;

	@Autowired
	private MaterielRepository materielRepository;

	@Autowired
	private List<Materiel> materielsList;

	@Override
	public Map<String, Component> beanFieldsToDisplayedUIFields() {
		Map<String, Component> result = new HashMap<String, Component>();
		result.put("designation", this.designation);
		result.put("materiel", this.materiel);

		return result;
	}

	@Override
	public Map<String, AbstractSinglePropertyField<?, ?>> beanFieldsToUIFields() {
		Map<String, AbstractSinglePropertyField<?, ?>> result = new LinkedHashMap<String, AbstractSinglePropertyField<?, ?>>();
		result.put("designation", this.designation);
		result.put("materiel", this.materiel);

		return result;
	}

	@Override
	public Map<String, String> beanFieldsToLabels() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("designation", "Désignation");
		result.put("materiel", "Matériel");

		return result;
	}

	@Override
	public Class<Salle> getEntityClassForBinder() {
		return Salle.class;
	}

	@Override
	public Salle instanciateEntity() {
		return new Salle();
	}

	@Override
	protected void constructFormFields() {
		this.designation = new TextField();
		this.materiel = new MaterielBackendComboBox(this.materielRepository);
	}

	@Override
	protected JpaRepository<Salle, ?> getRepository() {
		return this.salleRepository;
	}

	@Override
	public String getFormTitle() {
		return "Formulaire d'une salle";
	}

	@Override
	public Map<String, VaadinIcon> beanFieldsToIcons() {
		Map<String, VaadinIcon> result = new HashMap<>();
		result.put("designation", IconsHolder.extractIcon("salle.designation"));
		result.put("materiel", IconsHolder.extractIcon("materiel.serialNumber"));

		return result;
	}

	@Override
	protected Focusable<?> getOnShowFocusableComponent() {
		return this.designation;
	}
}

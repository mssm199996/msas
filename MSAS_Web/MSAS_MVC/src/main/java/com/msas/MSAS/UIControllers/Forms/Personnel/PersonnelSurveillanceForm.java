package com.msas.MSAS.UIControllers.Forms.Personnel;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.msas.MSAS.DomainModel.Personnel.PersonnelSurveillance;
import com.msas.MSAS.Repositories.PersonnelSurveillanceRepository;
import com.msas.MSAS.UIControllers.Others.IconsHolder;
import com.msas.MSAS.UIControllers.UsefulModules.Forms.MSForm;
import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class PersonnelSurveillanceForm extends MSForm<PersonnelSurveillance> {

	private static final long serialVersionUID = -2640262570704091601L;

	@Autowired
	private PersonnelSurveillanceRepository personnelSurveillanceRepository;

	private TextField designation, numeroTelephone, email;

	@Override
	public String getFormTitle() {
		return "Formulaire d'un personnel de surveillance";
	}

	@Override
	public Map<String, Component> beanFieldsToDisplayedUIFields() {
		Map<String, Component> result = new LinkedHashMap<>();
		result.put("designation", this.designation);
		result.put("numeroTelephone", this.numeroTelephone);
		result.put("email", this.email);

		return result;
	}

	@Override
	public Map<String, AbstractSinglePropertyField<?, ?>> beanFieldsToUIFields() {
		Map<String, AbstractSinglePropertyField<?, ?>> result = new LinkedHashMap<>();
		result.put("designation", this.designation);
		result.put("numeroTelephone", this.numeroTelephone);
		result.put("email", this.email);

		return result;
	}

	@Override
	public Map<String, VaadinIcon> beanFieldsToIcons() {
		Map<String, VaadinIcon> result = new LinkedHashMap<>();
		result.put("designation",
				IconsHolder.extractIcon("personnelSurveillance.designation"));
		result.put("numeroTelephone", IconsHolder
				.extractIcon("personnelSurveillance.numeroTelephone"));
		result.put("email",
				IconsHolder.extractIcon("personnelSurveillance.email"));

		return result;
	}

	@Override
	public Map<String, String> beanFieldsToLabels() {
		Map<String, String> result = new LinkedHashMap<>();
		result.put("designation", "Désignation");
		result.put("numeroTelephone", "N° de téléphone");
		result.put("email", "E-mail");

		return result;
	}

	@Override
	public Class<PersonnelSurveillance> getEntityClassForBinder() {
		return PersonnelSurveillance.class;
	}

	@Override
	public PersonnelSurveillance instanciateEntity() {
		return new PersonnelSurveillance();
	}

	@Override
	protected Focusable<?> getOnShowFocusableComponent() {
		return this.designation;
	}

	@Override
	protected void constructFormFields() {
		this.designation = new TextField();
		this.numeroTelephone = new TextField();
		this.email = new TextField();
	}

	@Override
	protected JpaRepository<PersonnelSurveillance, ?> getRepository() {
		return this.personnelSurveillanceRepository;
	}
}

package com.msas.MSAS.UIControllers.Forms.Personnel;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.msas.MSAS.DomainModel.Categorizers.Grade;
import com.msas.MSAS.DomainModel.Categorizers.Metier;
import com.msas.MSAS.DomainModel.Personnel.Personnel;
import com.msas.MSAS.DomainModel.Personnel.PieceIdentitee;
import com.msas.MSAS.Repositories.GradeRepository;
import com.msas.MSAS.Repositories.MetierRepository;
import com.msas.MSAS.Repositories.PersonnelRepository;
import com.msas.MSAS.UIControllers.MSASUIControls.GradeAddableComboBox;
import com.msas.MSAS.UIControllers.MSASUIControls.MetierAddableComboBox;
import com.msas.MSAS.UIControllers.Others.IconsHolder;
import com.msas.MSAS.UIControllers.UsefulModules.Forms.MSForm;
import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class PersonnelForm extends MSForm<Personnel> {

	private static final long serialVersionUID = -7913469837849175448L;

	private TextField nom, prenom, numeroTelephone, pieceJointe;
	private TextArea adresse;

	private MetierAddableComboBox metierAddableComboBox;
	private GradeAddableComboBox gradeAddableComboBox;

	@Autowired
	private PersonnelRepository repository;

	@Autowired
	private MetierRepository metierRepository;

	@Autowired
	private GradeRepository gradeRepository;

	@Autowired
	private List<Metier> metiersList;

	@Autowired
	private List<Grade> gradesList;

	@Override
	public Class<Personnel> getEntityClassForBinder() {
		return Personnel.class;
	}

	@Override
	public Personnel instanciateEntity() {
		PieceIdentitee pieceIdentitee = new PieceIdentitee();
		Personnel personnel = new Personnel();

		personnel.setPieceIdentitee(pieceIdentitee);
		pieceIdentitee.setPersonnel(personnel);

		return personnel;
	}

	@Override
	public Map<String, String> beanFieldsToLabels() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("nom", "Nom");
		result.put("prenom", "Prénom");
		result.put("adresse", "Adresse");
		result.put("numeroTelephone", "N° de téléphone");
		result.put("metier", "Métier");
		result.put("grade", "Grade");
		result.put("pieceIdentitee.serialNumber", "N° de série");

		return result;
	}

	@Override
	public Map<String, AbstractSinglePropertyField<?, ?>> beanFieldsToUIFields() {
		Map<String, AbstractSinglePropertyField<?, ?>> result = new LinkedHashMap<String, AbstractSinglePropertyField<?, ?>>();
		result.put("nom", this.nom);
		result.put("prenom", this.prenom);
		result.put("adresse", this.adresse);
		result.put("numeroTelephone", this.numeroTelephone);
		result.put("metier", this.metierAddableComboBox.getComboBox());
		result.put("grade", this.gradeAddableComboBox.getComboBox());
		result.put("pieceIdentitee.serialNumber", this.pieceJointe);

		return result;
	}

	@Override
	public Map<String, Component> beanFieldsToDisplayedUIFields() {
		Map<String, Component> result = new LinkedHashMap<String, Component>();
		result.put("nom", this.nom);
		result.put("prenom", this.prenom);
		result.put("adresse", this.adresse);
		result.put("numeroTelephone", this.numeroTelephone);
		result.put("metier", this.metierAddableComboBox);
		result.put("grade", this.gradeAddableComboBox);
		result.put("pieceIdentitee.serialNumber", this.pieceJointe);

		return result;
	}

	@Override
	protected void constructFormFields() {
		this.nom = new TextField();
		this.prenom = new TextField();
		this.adresse = new TextArea();
		this.numeroTelephone = new TextField();
		this.pieceJointe = new TextField();

		this.metierAddableComboBox = new MetierAddableComboBox(
				this.metiersList, this.metierRepository);
		this.gradeAddableComboBox = new GradeAddableComboBox(this.gradesList,
				this.gradeRepository);
	}

	@Override
	protected JpaRepository<Personnel, ?> getRepository() {
		return this.repository;
	}

	@Override
	public String getFormTitle() {
		return "Formulaire d'un personnel";
	}

	@Override
	public Map<String, VaadinIcon> beanFieldsToIcons() {
		Map<String, VaadinIcon> result = new HashMap<>();
		result.put("nom", IconsHolder.extractIcon("personnel.nom"));
		result.put("prenom", IconsHolder.extractIcon("personnel.prenom"));
		result.put("adresse", IconsHolder.extractIcon("personnel.adresse"));
		result.put("metier", IconsHolder.extractIcon("metier.designation"));
		result.put("grade", IconsHolder.extractIcon("grade.designation"));
		result.put("pieceIdentitee.serialNumber", IconsHolder
				.extractIcon("personnel.pieceIdentitee.serialNumber"));
		result.put("numeroTelephone",
				IconsHolder.extractIcon("personnel.numeroTelephone"));

		return result;
	}

	@Override
	protected Focusable<?> getOnShowFocusableComponent() {
		return this.nom;
	}
}

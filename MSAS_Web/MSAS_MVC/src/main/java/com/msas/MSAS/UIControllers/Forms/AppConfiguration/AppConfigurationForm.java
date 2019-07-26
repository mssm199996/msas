package com.msas.MSAS.UIControllers.Forms.AppConfiguration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.msas.MSAS.DomainModel.Configuration.AppConfiguration;
import com.msas.MSAS.Repositories.AppConfigurationRepository;
import com.msas.MSAS.UIControllers.MSASUIControls.GsmModemComboBox;
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
public class AppConfigurationForm extends MSForm<AppConfiguration> {

	private static final long serialVersionUID = -8704256733383039935L;

	@Autowired
	private AppConfigurationRepository appConfigurationRepository;

	@Autowired
	private AppConfiguration mainConfiguration;

	private TextField emailIntrusions, mdpIntrusions;
	private GsmModemComboBox gsmPortNumberIntrusions;

	public AppConfigurationForm() {
		super();

		this.setUpdate(true);
	}

	@PostConstruct
	public void initEntity() {
		this.setBindedEntity(this.mainConfiguration);
	}

	@Override
	public String getFormTitle() {
		return "MSAS Configuration (veuillez redémarrez l'application aprés validation)";
	}

	@Override
	public Map<String, Component> beanFieldsToDisplayedUIFields() {
		Map<String, Component> result = new LinkedHashMap<String, Component>();
		result.put("emailIntrusions", this.emailIntrusions);
		result.put("mdpIntrusions", this.mdpIntrusions);
		result.put("gsmPortNumberIntrusions", this.gsmPortNumberIntrusions);

		return result;
	}

	@Override
	public Map<String, AbstractSinglePropertyField<?, ?>> beanFieldsToUIFields() {
		Map<String, AbstractSinglePropertyField<?, ?>> result = new HashMap<>();
		result.put("emailIntrusions", this.emailIntrusions);
		result.put("mdpIntrusions", this.mdpIntrusions);
		result.put("gsmPortNumberIntrusions", this.gsmPortNumberIntrusions);

		return result;
	}

	@Override
	public Map<String, VaadinIcon> beanFieldsToIcons() {
		Map<String, VaadinIcon> result = new HashMap<>();
		result.put("emailIntrusions", VaadinIcon.MAILBOX);
		result.put("mdpIntrusions", VaadinIcon.KEY);
		result.put("gsmPortNumberIntrusions", VaadinIcon.TOOLS);

		result.put("mainAccountUser", VaadinIcon.USER);
		result.put("mainAccountPassword", VaadinIcon.PASSWORD);

		return result;
	}

	@Override
	public Map<String, String> beanFieldsToLabels() {
		Map<String, String> result = new HashMap<>();
		result.put("emailIntrusions", "E-mail de signalisation");
		result.put("mdpIntrusions", "Mot de passe");
		result.put("gsmPortNumberIntrusions", "Port du modem GSM");

		return result;
	}

	@Override
	public Class<AppConfiguration> getEntityClassForBinder() {
		return AppConfiguration.class;
	}

	@Override
	public AppConfiguration instanciateEntity() {
		return null;
	}

	@Override
	protected Focusable<?> getOnShowFocusableComponent() {
		return this.emailIntrusions;
	}

	@Override
	protected void constructFormFields() {
		this.emailIntrusions = new TextField();
		this.gsmPortNumberIntrusions = new GsmModemComboBox();
		this.mdpIntrusions = new TextField();
	}

	@Override
	protected JpaRepository<AppConfiguration, ?> getRepository() {
		return this.appConfigurationRepository;
	}
}

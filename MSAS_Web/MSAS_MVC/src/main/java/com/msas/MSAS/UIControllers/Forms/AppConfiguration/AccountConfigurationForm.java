package com.msas.MSAS.UIControllers.Forms.AppConfiguration;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.msas.MSAS.DomainModel.Authentification.Compte;
import com.msas.MSAS.Repositories.CompteRepository;
import com.msas.MSAS.UIControllers.UsefulModules.Forms.MSForm;
import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class AccountConfigurationForm extends MSForm<Compte> {

	private static final long serialVersionUID = 5929502864793251944L;

	@Autowired
	private CompteRepository compteRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private TextField username;
	private PasswordField password;

	public AccountConfigurationForm() {
		super();

		this.setUpdate(true);
	}

	@PostConstruct
	public void initEntity() {
		this.setBindedEntity(this.getLoggedInAccount());
	}

	@Override
	public String getFormTitle() {
		return "Compte d'administration";
	}

	@Override
	public Map<String, Component> beanFieldsToDisplayedUIFields() {
		Map<String, Component> result = new LinkedHashMap<>();
		result.put("username", this.username);
		result.put("password", this.password);

		return result;
	}

	@Override
	public Map<String, AbstractSinglePropertyField<?, ?>> beanFieldsToUIFields() {
		Map<String, AbstractSinglePropertyField<?, ?>> result = new LinkedHashMap<>();
		result.put("username", this.username);
		result.put("password", this.password);

		return result;
	}

	@Override
	public Map<String, VaadinIcon> beanFieldsToIcons() {
		Map<String, VaadinIcon> result = new LinkedHashMap<>();
		result.put("username", VaadinIcon.USER);
		result.put("password", VaadinIcon.PASSWORD);

		return result;
	}

	@Override
	public Map<String, String> beanFieldsToLabels() {
		Map<String, String> result = new LinkedHashMap<>();
		result.put("username", "Nom d'utilisateur");
		result.put("password", "Mot de passe");

		return result;
	}

	@Override
	public Class<Compte> getEntityClassForBinder() {
		return Compte.class;
	}

	@Override
	public Compte instanciateEntity() {
		return null;
	}

	@Override
	protected Focusable<?> getOnShowFocusableComponent() {
		return this.username;
	}

	@Override
	protected void constructFormFields() {
		this.username = new TextField();
		this.password = new PasswordField();
	}

	@Override
	protected JpaRepository<Compte, ?> getRepository() {
		return this.compteRepository;
	}

	@Override
	public void onBeforeSaveCallback(Compte compte) {
		compte.setPassword(this.passwordEncoder.encode(this.password.getValue()));
	}

	@Override
	public void onOpenCallback() {
		this.password.setValue("");
	}
}

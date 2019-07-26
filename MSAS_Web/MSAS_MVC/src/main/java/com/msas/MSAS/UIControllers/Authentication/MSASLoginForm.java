package com.msas.MSAS.UIControllers.Authentication;

import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginI18n.ErrorMessage;
import com.vaadin.flow.component.login.LoginOverlay;

@HtmlImport("styles/authentification/msas_login_form.html")
public class MSASLoginForm extends LoginOverlay {

	private static final long serialVersionUID = 7031302605042464540L;

	public MSASLoginForm() {
		super();
		
		LoginI18n loginUi = this.constructLoginI18n();

		this.setAction("login");
		this.setTitle("MSAS");
		this.setDescription("Web based authentication system");
		this.setForgotPasswordButtonVisible(false);
		this.onEnabledStateChanged(true);
		this.setOpened(true);
		this.setI18n(loginUi);
	}

	public LoginI18n constructLoginI18n() {
		String errorTitle = "Erreur d'authentification";
		String errorMessageContent = "Nom d'utilisateur ou mot de passe incorrect";

		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setTitle(errorTitle);
		errorMessage.setMessage(errorMessageContent);

		LoginI18n i18n = LoginI18n.createDefault();
		i18n.setAdditionalInformation("@MSAS Copyright");
		i18n.setErrorMessage(errorMessage);

		return i18n;
	}
}

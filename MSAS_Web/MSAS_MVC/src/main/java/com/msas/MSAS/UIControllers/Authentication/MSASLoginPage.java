package com.msas.MSAS.UIControllers.Authentication;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;

@Route(value = "loginPage")
@Theme(value = Material.class)
@StyleSheet("styles/authentification/msas_login_page.css")
public class MSASLoginPage extends HorizontalLayout implements
		AfterNavigationObserver {

	private static final long serialVersionUID = 8673461297922218502L;

	private MSASLoginForm loginForm;
	private VerticalLayout container;

	public MSASLoginPage() {
		super();

		this.initComponents();
	}

	private void initComponents() {
		this.loginForm = new MSASLoginForm();

		this.container = new VerticalLayout();
		this.container.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		this.container.add(this.loginForm);

		this.addClassName("login-page-container");
		this.setDefaultVerticalComponentAlignment(Alignment.CENTER);
		this.setHeightFull();
		this.add(this.container);
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		boolean isError = event.getLocation().getQueryParameters()
				.getParameters().containsKey("error");
		this.loginForm.setError(isError);
	}
}

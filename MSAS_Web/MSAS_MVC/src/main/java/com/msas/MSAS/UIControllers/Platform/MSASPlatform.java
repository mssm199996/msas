package com.msas.MSAS.UIControllers.Platform;

import org.springframework.beans.factory.annotation.Autowired;

import com.msas.MSAS.UIControllers.Forms.AppConfiguration.AccountConfigurationForm;
import com.msas.MSAS.UIControllers.Forms.AppConfiguration.AppConfigurationForm;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@HtmlImport("styles/custom_themes/ms-custom-theme.html")
public abstract class MSASPlatform extends AppLayout {

	protected static final long serialVersionUID = 9204683450055949048L;
	protected AppLayoutMenu menu;
	protected AppLayoutMenuItem logoutMenuItem;
	protected VerticalLayout verticalLayout;

	@Autowired
	private AppConfigurationForm appConfigurationForm;
	
	@Autowired
	private AccountConfigurationForm accountConfigurationForm;

	public MSASPlatform() {
		this.initBranding();
		this.initMenu();
		this.initContent();
	}

	protected void initContent() {
		this.verticalLayout = new VerticalLayout();
		this.verticalLayout.setWidth("100%");
		this.verticalLayout.setHeight("100%");

		this.setContent(this.verticalLayout);
	}

	protected void initBranding() {
		Image img = new Image("/frontend/images/msas-logo.png", "MSAS Logo");
		img.setHeight("40px");

		this.setBranding(img);
	}

	protected void initMenu() {
		this.logoutMenuItem = new AppLayoutMenuItem(VaadinIcon.EXIT.create(),
				"Déconnexion");

		AppLayoutMenuItem personnel = new AppLayoutMenuItem(
				VaadinIcon.USERS.create(), "G.Personnel", "personnelList");

		AppLayoutMenuItem salle = new AppLayoutMenuItem(
				VaadinIcon.BUILDING_O.create(), "G.Salles", "salleList");

		AppLayoutMenuItem materiel = new AppLayoutMenuItem(
				VaadinIcon.LAPTOP.create(), "G.Matériel", "materielList");

		AppLayoutMenuItem tentativeAcces = new AppLayoutMenuItem(
				VaadinIcon.SIGN_IN.create(), "Tentatives d'accès",
				"tentativeAccesList");

		AppLayoutMenuItem accesReels = new AppLayoutMenuItem(
				VaadinIcon.EXCHANGE.create(), "Historique d'accés",
				"accesReelsList");

		AppLayoutMenuItem personnelSurveillance = new AppLayoutMenuItem(
				VaadinIcon.SHIELD.create(), "Personnels de surveillance",
				"personnelSurveillanceList");

		AppLayoutMenuItem intrusions = new AppLayoutMenuItem(
				VaadinIcon.ALARM.create(), "Intrusions", "intrusionList");

		AppLayoutMenuItem config = new AppLayoutMenuItem(
				VaadinIcon.TOOLBOX.create(), "Paramètres");
		config.addMenuItemClickListener(event -> {
			this.appConfigurationForm.open();
		});
		
		AppLayoutMenuItem compte = new AppLayoutMenuItem(
				VaadinIcon.USER.create(), "Compte");
		compte.addMenuItemClickListener(event -> {
			this.accountConfigurationForm.open();
		});

		this.menu = this.createMenu();
		this.menu.addMenuItems(personnel, salle, materiel);
		this.menu.addMenuItems(tentativeAcces, accesReels);
		this.menu.addMenuItems(personnelSurveillance, intrusions);
		this.menu.addMenuItems(compte, config);
		this.menu.addMenuItem(this.logoutMenuItem);
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);

		UI ui = getUI().get();

		this.logoutMenuItem.addMenuItemClickListener(event -> {
			ui.getPage().executeJavaScript("window.location.href='/logout'");
			ui.getSession().close();
		});

		ui.setPollInterval(3000);
	}

	public AppLayoutMenuItem createVerticalSeparator() {
		AppLayoutMenuItem separator = new AppLayoutMenuItem(
				VaadinIcon.LINE_V.create());
		separator.addClassName("p-0");
		separator.addClassName("v-menu-separator");

		return separator;
	}
}

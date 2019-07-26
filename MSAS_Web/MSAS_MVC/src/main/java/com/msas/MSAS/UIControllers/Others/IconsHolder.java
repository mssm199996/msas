package com.msas.MSAS.UIControllers.Others;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.vaadin.flow.component.icon.VaadinIcon;

@Component
public class IconsHolder {

	public static Map<String, VaadinIcon> ICONS_MAP = new HashMap<>();

	public static void registerIcon(String key, VaadinIcon vaadinIcon) {
		IconsHolder.ICONS_MAP.put(key, vaadinIcon);
	}

	public static VaadinIcon extractIcon(String key) {
		return IconsHolder.ICONS_MAP.get(key);
	}

	public IconsHolder() {
		registerIcon("metier.designation", VaadinIcon.TOOLBOX);
		registerIcon("grade.designation", VaadinIcon.PYRAMID_CHART);

		registerIcon("personnel.nom", VaadinIcon.USER_STAR);
		registerIcon("personnel.prenom", VaadinIcon.USER_CHECK);
		registerIcon("personnel.pieceIdentitee.serialNumber", VaadinIcon.USER_CARD);
		registerIcon("personnel.numeroTelephone", VaadinIcon.PHONE);
		registerIcon("personnel.adresse", VaadinIcon.HOME);

		registerIcon("salle.designation", VaadinIcon.BUILDING);

		registerIcon("materiel.serialNumber", VaadinIcon.LAPTOP);
		registerIcon("materiel.etat", VaadinIcon.CHECK);

		registerIcon("date", VaadinIcon.CALENDAR);
		registerIcon("heure", VaadinIcon.TIMER);

		registerIcon("tentativeAcces.autorised", VaadinIcon.KEY);
		registerIcon("accesReel.typeAcces", VaadinIcon.EXCHANGE);
		
		registerIcon("personnelSurveillance.designation", VaadinIcon.USERS);
		registerIcon("personnelSurveillance.numeroTelephone", VaadinIcon.PHONE);
		registerIcon("personnelSurveillance.email", VaadinIcon.ENVELOPE);

	}
}

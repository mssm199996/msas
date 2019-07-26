package com.msas.MSAS.UIControllers.Grids.Personnel;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import com.msas.MSAS.DomainModel.Personnel.PersonnelSurveillance;
import com.msas.MSAS.Repositories.PersonnelSurveillanceRepository;
import com.msas.MSAS.UIControllers.Others.IconsHolder;
import com.msas.MSAS.UIControllers.UsefulModules.GridCells.MSRenderer;
import com.msas.MSAS.UIControllers.UsefulModules.Grids.MSEditableDeletableGrid;
import com.vaadin.flow.component.icon.VaadinIcon;

public class PersonnelSurveillanceGrid
		extends
		MSEditableDeletableGrid<PersonnelSurveillance, PersonnelSurveillanceRepository> {

	private static final long serialVersionUID = -6516569536322051631L;

	public PersonnelSurveillanceGrid(PersonnelSurveillanceRepository repository) {
		super(PersonnelSurveillance.class, repository);
	}

	@Override
	protected Map<String, String> propertyNamesToColumnHeadersTranslator() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("designation", "Désignation");
		result.put("numeroTelephone", "N° de téléphone");
		result.put("email", "E-mail");

		return result;
	}

	@Override
	protected Map<String, VaadinIcon> propertyNamesToColumnIconsTranslator() {
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
	protected String[] simplePropertyNamesToShow() {
		return new String[] { "designation", "numeroTelephone", "email" };
	}

	@Override
	protected Collection<MSRenderer<PersonnelSurveillance>> getAdditionalColumns() {
		return new HashSet<>();
	}
}

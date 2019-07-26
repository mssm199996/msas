package com.msas.MSAS.UIControllers.Grids.Others;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msas.MSAS.DomainModel.Others.SalleEvent;
import com.msas.MSAS.UIControllers.Others.IconsHolder;
import com.msas.MSAS.UIControllers.UsefulModules.Grids.MSGrid;
import com.vaadin.flow.component.icon.VaadinIcon;

public abstract class SalleEventGrid<T extends SalleEvent, R extends JpaRepository<T, ?>>
		extends MSGrid<T, R> {

	private static final long serialVersionUID = -3752751821110853380L;

	public SalleEventGrid(Class<T> entityClass, R repository) {
		super(entityClass, repository);
	}

	@Override
	protected Map<String, String> propertyNamesToColumnHeadersTranslator() {
		Map<String, String> result = new HashMap<>();
		result.put("salle.designation", "Désignation de la salle");
		result.put("date", "Date");
		result.put("heure", "Heure");

		return result;
	}

	@Override
	protected Map<String, VaadinIcon> propertyNamesToColumnIconsTranslator() {
		Map<String, VaadinIcon> result = new HashMap<>();
		result.put("salle.designation",
				IconsHolder.extractIcon("salle.designation"));

		result.put("date", IconsHolder.extractIcon("date"));
		result.put("heure", IconsHolder.extractIcon("heure"));

		return result;
	}
}

package com.msas.MSAS.UIControllers.Cruds.Others;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msas.MSAS.DomainModel.Others.SalleEvent;
import com.msas.MSAS.UIControllers.Grids.Others.SalleEventGrid;
import com.msas.MSAS.UIControllers.Others.IconsHolder;
import com.msas.MSAS.UIControllers.UsefulModules.MSCruds.MSCrud;
import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.VaadinIcon;

public abstract class SalleEventCrud<T extends SalleEvent, R extends JpaRepository<T, ?>, G extends SalleEventGrid<T, R>>
		extends MSCrud<T, R, G> {

	private static final long serialVersionUID = 6209446365483631211L;

	protected DatePicker dateDebut, dateFin;

	@Override
	protected Map<AbstractSinglePropertyField<?, ?>, String> filters() {
		this.dateDebut = new DatePicker();
		this.dateDebut.setValue(LocalDate.now());

		this.dateFin = new DatePicker();
		this.dateFin.setValue(LocalDate.now());

		Map<AbstractSinglePropertyField<?, ?>, String> result = new LinkedHashMap<>();
		result.put(this.dateDebut, "De");
		result.put(this.dateFin, "Â");

		return result;
	}

	@Override
	protected Map<AbstractSinglePropertyField<?, ?>, VaadinIcon> filtersIcons() {
		Map<AbstractSinglePropertyField<?, ?>, VaadinIcon> result = new HashMap<>();
		result.put(this.dateDebut, IconsHolder.extractIcon("date"));
		result.put(this.dateFin, IconsHolder.extractIcon("date"));

		return result;
	}
}

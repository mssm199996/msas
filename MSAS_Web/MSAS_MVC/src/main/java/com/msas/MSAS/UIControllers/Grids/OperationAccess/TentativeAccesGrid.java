package com.msas.MSAS.UIControllers.Grids.OperationAccess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.msas.MSAS.DomainModel.Access.TentativeAcces;
import com.msas.MSAS.Repositories.TentativeAccesRepository;
import com.msas.MSAS.UIControllers.MSASUIControls.TentativeAccesAutorisationCheckBoxRenderer;
import com.msas.MSAS.UIControllers.Others.IconsHolder;
import com.msas.MSAS.UIControllers.UsefulModules.GridCells.MSRenderer;
import com.vaadin.flow.component.icon.VaadinIcon;

public class TentativeAccesGrid extends
		OperationAccesGrid<TentativeAcces, TentativeAccesRepository> {

	private static final long serialVersionUID = -4966002637466101392L;

	public TentativeAccesGrid(TentativeAccesRepository repository) {
		super(TentativeAcces.class, repository);
	}

	@Override
	protected Map<String, String> propertyNamesToColumnHeadersTranslator() {
		Map<String, String> result = super
				.propertyNamesToColumnHeadersTranslator();
		result.put("tentativeAcces.autorised", "Autorisé(e) ?");

		return result;
	}

	@Override
	protected Map<String, VaadinIcon> propertyNamesToColumnIconsTranslator() {
		Map<String, VaadinIcon> result = super
				.propertyNamesToColumnIconsTranslator();
		result.put("tentativeAcces.autorised",
				IconsHolder.extractIcon("tentativeAcces.autorised"));

		return result;
	}

	@Override
	protected Collection<MSRenderer<TentativeAcces>> getAdditionalColumns() {
		Set<MSRenderer<TentativeAcces>> result = new HashSet<>();
		result.add(new TentativeAccesAutorisationCheckBoxRenderer());

		return result;
	}
}

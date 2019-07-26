package com.msas.MSAS.UIControllers.Grids.OperationAccess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.msas.MSAS.DomainModel.Access.AccesReel;
import com.msas.MSAS.Repositories.AccesReelRepository;
import com.msas.MSAS.UIControllers.MSASUIControls.AccesReelTypeRenderer;
import com.msas.MSAS.UIControllers.Others.IconsHolder;
import com.msas.MSAS.UIControllers.UsefulModules.GridCells.MSRenderer;
import com.vaadin.flow.component.icon.VaadinIcon;

public class AccesReelGrid extends
		OperationAccesGrid<AccesReel, AccesReelRepository> {

	private static final long serialVersionUID = 8214960413340468612L;

	public AccesReelGrid(AccesReelRepository repository) {
		super(AccesReel.class, repository);
	}

	@Override
	protected Map<String, String> propertyNamesToColumnHeadersTranslator() {
		Map<String, String> result = super
				.propertyNamesToColumnHeadersTranslator();
		result.put("accesReel.typeAcces", "Entrée / Sortie ?");

		return result;
	}

	@Override
	protected Map<String, VaadinIcon> propertyNamesToColumnIconsTranslator() {
		Map<String, VaadinIcon> result = super
				.propertyNamesToColumnIconsTranslator();
		result.put("accesReel.typeAcces",
				IconsHolder.extractIcon("accesReel.typeAcces"));

		return result;
	}

	@Override
	protected Collection<MSRenderer<AccesReel>> getAdditionalColumns() {
		Set<MSRenderer<AccesReel>> result = new HashSet<>();
		result.add(new AccesReelTypeRenderer());

		return result;
	}
}

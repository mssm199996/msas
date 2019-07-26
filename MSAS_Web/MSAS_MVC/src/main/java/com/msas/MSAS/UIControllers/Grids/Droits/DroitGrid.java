package com.msas.MSAS.UIControllers.Grids.Droits;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.msas.MSAS.DomainModel.Droits.Droit;
import com.msas.MSAS.Repositories.DroitRepository;
import com.msas.MSAS.UIControllers.Others.IconsHolder;
import com.msas.MSAS.UIControllers.UsefulModules.GridCells.DeleteButtonRenderer;
import com.msas.MSAS.UIControllers.UsefulModules.GridCells.MSRenderer;
import com.msas.MSAS.UIControllers.UsefulModules.Grids.MSDeletableGrid;
import com.vaadin.flow.component.icon.VaadinIcon;

public class DroitGrid extends
		MSDeletableGrid<Droit, DroitRepository, DeleteButtonRenderer<Droit>> {

	private static final long serialVersionUID = 1462354563808829714L;

	public DroitGrid(DroitRepository repository) {
		super(Droit.class, repository);
	}

	@Override
	protected Map<String, String> propertyNamesToColumnHeadersTranslator() {
		Map<String, String> result = new HashMap<>();
		result.put("personnel.pieceIdentitee.serialNumber",
				"N° de série du personnel");
		result.put("personnel.nom", "Nom du personnel");
		result.put("personnel.prenom", "Prénom du personnel");

		result.put("salle.designation", "Désignation de la salle");

		return result;
	}

	@Override
	protected Map<String, VaadinIcon> propertyNamesToColumnIconsTranslator() {
		Map<String, VaadinIcon> result = new HashMap<>();
		result.put("personnel.pieceIdentitee.serialNumber", IconsHolder
				.extractIcon("personnel.pieceIdentitee.serialNumber"));

		result.put("personnel.nom", IconsHolder.extractIcon("personnel.nom"));
		result.put("personnel.prenom",
				IconsHolder.extractIcon("personnel.prenom"));

		result.put("salle.designation",
				IconsHolder.extractIcon("salle.designation"));

		return result;
	}

	@Override
	protected String[] simplePropertyNamesToShow() {
		return new String[] { "personnel.pieceIdentitee.serialNumber",
				"personnel.nom", "personnel.prenom", "salle.designation" };
	}

	@Override
	protected Collection<MSRenderer<Droit>> getAdditionalColumns() {
		return new HashSet<>();
	}
}

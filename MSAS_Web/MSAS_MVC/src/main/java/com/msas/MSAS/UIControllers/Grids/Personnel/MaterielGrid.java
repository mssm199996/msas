package com.msas.MSAS.UIControllers.Grids.Personnel;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import com.msas.MSAS.DomainModel.Authentification.Materiel;
import com.msas.MSAS.DomainModel.Authentification.Salle;
import com.msas.MSAS.Repositories.MaterielRepository;
import com.msas.MSAS.Repositories.SalleRepository;
import com.msas.MSAS.UIControllers.MSASUIControls.EtatMaterielCheckBoxRenderer;
import com.msas.MSAS.UIControllers.Others.IconsHolder;
import com.msas.MSAS.UIControllers.UsefulModules.GridCells.DeleteButtonRenderer;
import com.msas.MSAS.UIControllers.UsefulModules.GridCells.MSRenderer;
import com.msas.MSAS.UIControllers.UsefulModules.Grids.MSDeletableGrid;
import com.vaadin.flow.component.icon.VaadinIcon;

public class MaterielGrid
		extends
		MSDeletableGrid<Materiel, MaterielRepository, DeleteButtonRenderer<Materiel>> {

	private static final long serialVersionUID = -4308639509170268849L;

	private SalleRepository salleRepository;

	public MaterielGrid(MaterielRepository repository,
			SalleRepository salleRepository) {
		super(Materiel.class, repository);

		this.salleRepository = salleRepository;
	}

	@Override
	protected Map<String, String> propertyNamesToColumnHeadersTranslator() {
		HashMap<String, String> result = new LinkedHashMap<String, String>();
		result.put("salle", "Désignation de la salle associée");
		result.put("serialNumber", "N° de série du matériel");
		result.put("etat", "Etat (Actif / Inactif)");

		return result;
	}

	@Override
	protected String[] simplePropertyNamesToShow() {
		return new String[] { "salle", "serialNumber" };
	}

	@Override
	public Consumer<Materiel> beforeDeleteCallback() {
		return (materiel -> {
			Salle salle = materiel.getSalle();

			if (salle != null) {
				salle.setMateriel(null);
				this.salleRepository.save(salle);
				materiel.setSalle(null);
			}
		});
	}

	@Override
	protected Map<String, VaadinIcon> propertyNamesToColumnIconsTranslator() {
		HashMap<String, VaadinIcon> result = new LinkedHashMap<>();
		result.put("salle", IconsHolder.extractIcon("salle.designation"));
		result.put("serialNumber",
				IconsHolder.extractIcon("materiel.serialNumber"));
		result.put("etat", IconsHolder.extractIcon("materiel.etat"));

		return result;
	}

	@Override
	protected Collection<MSRenderer<Materiel>> getAdditionalColumns() {
		Set<MSRenderer<Materiel>> result = new LinkedHashSet<>();
		result.add(new EtatMaterielCheckBoxRenderer());

		return result;
	}
}

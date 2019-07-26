package com.msas.MSAS.UIControllers.Grids.Personnel;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.msas.MSAS.DomainModel.Authentification.Salle;
import com.msas.MSAS.Repositories.DroitRepository;
import com.msas.MSAS.Repositories.PersonnelSurveillanceRepository;
import com.msas.MSAS.Repositories.SalleRepository;
import com.msas.MSAS.UIControllers.MSASUIControls.EtatMaterielSalleCheckBoxRenderer;
import com.msas.MSAS.UIControllers.MSASUIControls.SalleDroitAsChildTableRenderer;
import com.msas.MSAS.UIControllers.MSASUIControls.SallePersonnelSurveillanceAsChildTableRenderer;
import com.msas.MSAS.UIControllers.Others.IconsHolder;
import com.msas.MSAS.UIControllers.UsefulModules.GridCells.MSRenderer;
import com.msas.MSAS.UIControllers.UsefulModules.Grids.MSEditableDeletableGrid;
import com.vaadin.flow.component.icon.VaadinIcon;

public class SalleGrid extends MSEditableDeletableGrid<Salle, SalleRepository> {

	private static final long serialVersionUID = 3729138215170191960L;

	private SalleDroitAsChildTableRenderer salleDroitAsChildTableRenderer;
	private SallePersonnelSurveillanceAsChildTableRenderer sallePersonnelSurveillanceAsChildTableRenderer;

	public SalleGrid(SalleRepository repository,
			DroitRepository droitRepository,
			PersonnelSurveillanceRepository personnelSurveillanceRepository) {
		super(Salle.class, repository);

		this.salleDroitAsChildTableRenderer.setRepository(droitRepository);
		this.sallePersonnelSurveillanceAsChildTableRenderer
				.setRepository(personnelSurveillanceRepository);
	}

	@Override
	protected Map<String, String> propertyNamesToColumnHeadersTranslator() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("designation", "Désignation");
		result.put("materiel", "N° de série du matériel associé");
		result.put("materiel.etat",
				"Etat du matériel associé (Actif / Inactif)");

		return result;
	}

	@Override
	protected String[] simplePropertyNamesToShow() {
		return new String[] { "designation", "materiel" };
	}

	@Override
	protected Map<String, VaadinIcon> propertyNamesToColumnIconsTranslator() {
		Map<String, VaadinIcon> result = new HashMap<>();
		result.put("designation", IconsHolder.extractIcon("salle.designation"));
		result.put("materiel", IconsHolder.extractIcon("materiel.serialNumber"));
		result.put("materiel.etat", IconsHolder.extractIcon("materiel.etat"));

		return result;
	}

	@Override
	protected Collection<MSRenderer<Salle>> getAdditionalColumns() {
		this.salleDroitAsChildTableRenderer = new SalleDroitAsChildTableRenderer();
		this.sallePersonnelSurveillanceAsChildTableRenderer = new SallePersonnelSurveillanceAsChildTableRenderer();
		
		Set<MSRenderer<Salle>> result = new LinkedHashSet<>();
		result.add(new EtatMaterielSalleCheckBoxRenderer());
		result.add(this.salleDroitAsChildTableRenderer);
		result.add(this.sallePersonnelSurveillanceAsChildTableRenderer);

		return result;
	}
}

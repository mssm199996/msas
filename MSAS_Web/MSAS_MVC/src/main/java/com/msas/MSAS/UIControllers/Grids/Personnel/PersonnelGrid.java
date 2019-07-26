package com.msas.MSAS.UIControllers.Grids.Personnel;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.msas.MSAS.DomainModel.Personnel.Personnel;
import com.msas.MSAS.Repositories.DroitRepository;
import com.msas.MSAS.Repositories.PersonnelRepository;
import com.msas.MSAS.UIControllers.MSASUIControls.PersonnelDroitAsChildTableRenderer;
import com.msas.MSAS.UIControllers.Others.IconsHolder;
import com.msas.MSAS.UIControllers.UsefulModules.GridCells.MSRenderer;
import com.msas.MSAS.UIControllers.UsefulModules.Grids.MSEditableDeletableGrid;
import com.vaadin.flow.component.icon.VaadinIcon;

public class PersonnelGrid extends
		MSEditableDeletableGrid<Personnel, PersonnelRepository> {

	private static final long serialVersionUID = 2309149258862179576L;

	private PersonnelDroitAsChildTableRenderer personnelDroitAsChildTableRenderer;

	public PersonnelGrid(PersonnelRepository repository,
			DroitRepository droitRepository) {
		super(Personnel.class, repository);

		this.personnelDroitAsChildTableRenderer.setRepository(droitRepository);
	}

	@Override
	protected Map<String, String> propertyNamesToColumnHeadersTranslator() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("nom", "Nom");
		result.put("prenom", "Prénom");
		result.put("numeroTelephone", "N° Téléphone");
		result.put("pieceIdentitee", "N° d'identitée");
		result.put("metier", "Métier");
		result.put("grade", "Grade");

		return result;
	}

	@Override
	protected String[] simplePropertyNamesToShow() {
		return new String[] { "nom", "prenom", "numeroTelephone", "metier",
				"grade", "pieceIdentitee" };
	}

	@Override
	protected Map<String, VaadinIcon> propertyNamesToColumnIconsTranslator() {
		Map<String, VaadinIcon> result = new HashMap<>();
		result.put("nom", IconsHolder.extractIcon("personnel.nom"));
		result.put("prenom", IconsHolder.extractIcon("personnel.prenom"));
		result.put("numeroTelephone", IconsHolder.extractIcon("personnel.numeroTelephone"));
		result.put("metier", IconsHolder.extractIcon("metier.designation"));
		result.put("grade", IconsHolder.extractIcon("grade.designation"));
		result.put("pieceIdentitee", IconsHolder
				.extractIcon("personnel.pieceIdentitee.serialNumber"));

		return result;
	}

	@Override
	protected Collection<MSRenderer<Personnel>> getAdditionalColumns() {
		this.personnelDroitAsChildTableRenderer = new PersonnelDroitAsChildTableRenderer();

		Set<MSRenderer<Personnel>> result = new HashSet<>();
		result.add(this.personnelDroitAsChildTableRenderer);

		return result;
	}

}

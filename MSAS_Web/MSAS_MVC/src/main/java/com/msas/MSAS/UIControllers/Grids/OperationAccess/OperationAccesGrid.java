package com.msas.MSAS.UIControllers.Grids.OperationAccess;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msas.MSAS.DomainModel.Access.OperationAcces;
import com.msas.MSAS.UIControllers.Grids.Others.SalleEventGrid;
import com.msas.MSAS.UIControllers.Others.IconsHolder;
import com.vaadin.flow.component.icon.VaadinIcon;

public abstract class OperationAccesGrid<T extends OperationAcces, R extends JpaRepository<T, ?>>
		extends SalleEventGrid<T, R> {

	private static final long serialVersionUID = 1425841007993253335L;

	public OperationAccesGrid(Class<T> entityClass, R repository) {
		super(entityClass, repository);
	}

	@Override
	protected String[] simplePropertyNamesToShow() {
		return new String[] { "personnel.nom", "personnel.prenom",
				"salle.designation", "date", "heure" };
	}

	@Override
	protected Map<String, String> propertyNamesToColumnHeadersTranslator() {
		Map<String, String> result = super
				.propertyNamesToColumnHeadersTranslator();
		result.put("personnel.nom", "Nom du personnel");
		result.put("personnel.prenom", "Prénom du personnel");

		return result;
	}

	@Override
	protected Map<String, VaadinIcon> propertyNamesToColumnIconsTranslator() {
		Map<String, VaadinIcon> result = super
				.propertyNamesToColumnIconsTranslator();
		result.put("personnel.nom", IconsHolder.extractIcon("personnel.nom"));
		result.put("personnel.prenom",
				IconsHolder.extractIcon("personnel.prenom"));

		return result;
	}
}

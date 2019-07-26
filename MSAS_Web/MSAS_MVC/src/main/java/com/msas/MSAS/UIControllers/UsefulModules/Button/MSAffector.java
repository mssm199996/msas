package com.msas.MSAS.UIControllers.UsefulModules.Button;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msas.MSAS.AdditionalBeans.FromComboBoxAddableBean;
import com.msas.MSAS.UIControllers.UsefulModules.AlertsDisplayer;
import com.msas.MSAS.UIControllers.UsefulModules.MSAddableComboBoxes.MSAddableComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridMultiSelectionModel;
import com.vaadin.flow.component.grid.GridMultiSelectionModel.SelectAllCheckboxVisibility;
import com.vaadin.flow.component.icon.VaadinIcon;

public abstract class MSAffector<P extends FromComboBoxAddableBean, C, R extends JpaRepository<P, ?>>
		extends MSButton {

	private static final long serialVersionUID = 9010087819617413345L;

	public MSAffector(TypeAffectation typeAffectation, String caption,
			Grid<C> childsGrid, MSAddableComboBox<P, R> parentHolder) {
		super(caption, typeAffectation.getStyle(), VaadinIcon.LINK);

		if (!(childsGrid.getSelectionModel() instanceof GridMultiSelectionModel)) {
			GridMultiSelectionModel<C> selectionModel = (GridMultiSelectionModel<C>) childsGrid
					.setSelectionMode(SelectionMode.MULTI);

			selectionModel
					.setSelectAllCheckboxVisibility(SelectAllCheckboxVisibility.VISIBLE);
		}

		this.setWidthFull();
		this.addClickListener(event -> {
			Set<C> childs = childsGrid.getSelectedItems();

			if (!childs.isEmpty()) {
				AlertsDisplayer.displayAlert(
						this.getNomFonction(),
						this.getNomOperation(),
						parentHolder,
						VaadinIcon.LINK,
						true,
						(consume) -> {
							P parent = parentHolder.getComboBox().getValue();

							if (parent != null) {
								R parentRepository = parentHolder
										.getRepository();
								P fullyParent = this.fethParentWithChilds(
										parent, parentRepository);

								for (C child : childs)
									this.onLinking(fullyParent, child,
											parentRepository);

								parentHolder.getRepository().save(fullyParent);
							} else
								AlertsDisplayer.displayErrorAlert(
										this.getNomFonction(),
										"Erreur !",
										"Aucun élement n'a été sélectionné et donc aucune opération ne sera faite",
										(consumer) -> {
										});
						});
			} else
				AlertsDisplayer.displayErrorAlert(
						this.getNomFonction(),
						"Erreur !",
						"Veuillez d'abords sélectionner des élements dans le tableau !",
						(consumer) -> {
						});
		});
	}

	public abstract P fethParentWithChilds(P parent, R parentRepository);

	public abstract void onLinking(P parent, C child, R parentRepository);

	public abstract String getNomFonction();

	public abstract String getNomOperation();

	protected static enum TypeAffectation {
		ATTACH("primary"), DETACH("warning");

		private String style;

		TypeAffectation(String style) {
			this.style = style;
		}

		public String getStyle() {
			return style;
		}

		public void setStyle(String style) {
			this.style = style;
		}
	}
}

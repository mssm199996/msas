package com.msas.MSAS.UIControllers.MSASUIControls;

import com.msas.MSAS.DomainModel.Authentification.Materiel;
import com.msas.MSAS.DomainModel.Authentification.Salle;
import com.msas.MSAS.UIControllers.UsefulModules.CheckBox.CheckBoxRenderer;

public class EtatMaterielSalleCheckBoxRenderer extends
		CheckBoxRenderer<Salle, Materiel> {

	private static final long serialVersionUID = 1852320517562262348L;

	@Override
	public Materiel getField(Salle item) {
		return item.getMateriel();
	}

	@Override
	public boolean getFieldAsBoolean(Materiel item) {
		return item.isEtat();
	}

	@Override
	public String getColumnKey() {
		return "materiel.etat";
	}
}

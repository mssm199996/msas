package com.msas.MSAS.UIControllers.MSASUIControls;

import com.msas.MSAS.DomainModel.Authentification.Materiel;
import com.msas.MSAS.UIControllers.UsefulModules.CheckBox.CheckBoxRenderer;

public class EtatMaterielCheckBoxRenderer extends
		CheckBoxRenderer<Materiel, Boolean> {

	private static final long serialVersionUID = 550118394000612877L;

	@Override
	public Boolean getField(Materiel item) {
		return item.isEtat();
	}

	@Override
	public boolean getFieldAsBoolean(Boolean item) {
		return item;
	}

	@Override
	public String getColumnKey() {
		return "etat";
	}
}

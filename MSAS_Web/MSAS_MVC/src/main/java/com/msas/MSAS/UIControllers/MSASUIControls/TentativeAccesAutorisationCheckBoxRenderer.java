package com.msas.MSAS.UIControllers.MSASUIControls;

import com.msas.MSAS.DomainModel.Access.TentativeAcces;
import com.msas.MSAS.UIControllers.UsefulModules.CheckBox.CheckBoxRenderer;

public class TentativeAccesAutorisationCheckBoxRenderer extends
		CheckBoxRenderer<TentativeAcces, Boolean> {
	
	private static final long serialVersionUID = -216718428717392667L;

	@Override
	public Boolean getField(TentativeAcces item) {
		return item.isAutorised();
	}

	@Override
	public boolean getFieldAsBoolean(Boolean item) {
		return item;
	}

	@Override
	public String getColumnKey() {
		return "tentativeAcces.autorised";
	}
}

package com.msas.MSAS.UIControllers.UsefulModules.CheckBox;

import com.msas.MSAS.UIControllers.UsefulModules.GridCells.MSRenderer;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class CheckBoxRenderer<T, S> extends MSRenderer<T> {

	private static final long serialVersionUID = 4137727923013394366L;

	@Override
	public VerticalLayout createComponent(T item) {
		VerticalLayout result = super.createComponent(item);

		Checkbox checkBox = new Checkbox();
		checkBox.setReadOnly(true);
		checkBox.setIndeterminate(false);

		S field = this.getField(item);

		if (field != null)
			checkBox.setValue(this.getFieldAsBoolean(field));
		else
			checkBox.setIndeterminate(true);

		result.add(checkBox);

		return result;
	}

	public abstract S getField(T item);

	public abstract boolean getFieldAsBoolean(S item);
}

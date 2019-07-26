package com.msas.MSAS.UIControllers.UsefulModules.GridCells;

import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;

public abstract class MSRenderer<T> extends
		ComponentRenderer<VerticalLayout, T> {

	private static final long serialVersionUID = -2644059608683361516L;

	@Override
	public VerticalLayout createComponent(T item) {
		VerticalLayout layout = new VerticalLayout();
		layout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		layout.setWidthFull();
		layout.addClassName("p-0");

		return layout;
	}

	public abstract String getColumnKey();
}

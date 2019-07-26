package com.msas.MSAS.UIControllers.MSASUIControls;

import com.msas.MSAS.DomainModel.Access.AccesReel;
import com.msas.MSAS.UIControllers.UsefulModules.GridCells.MSRenderer;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class AccesReelTypeRenderer extends MSRenderer<AccesReel> {

	private static final long serialVersionUID = 7978284235843108591L;

	@Override
	public VerticalLayout createComponent(AccesReel item) {
		VerticalLayout layout = super.createComponent(item);

		VaadinIcon iconType = null;
		String color;

		switch (item.getTypeAcces()) {
		case ENTREE:
			iconType = VaadinIcon.ARROW_LEFT;
			color = "GREEN";
			break;
		case SORTIE:
			iconType = VaadinIcon.ARROW_RIGHT;
			color = "RED";
			break;
		default:
			iconType = VaadinIcon.QUESTION_CIRCLE_O;
			color = "BLUE";
			break;
		}

		Icon icon = iconType.create();
		icon.setColor(color);
		icon.setSize("30px");

		layout.add(icon);
		
		return layout;
	}

	@Override
	public String getColumnKey() {
		return "accesReel.typeAcces";
	}
}

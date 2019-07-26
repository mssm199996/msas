package com.msas.MSAS.UIControllers.UsefulModules.MSCruds;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msas.MSAS.UIControllers.UsefulModules.Button.MSButton;
import com.msas.MSAS.UIControllers.UsefulModules.Forms.MSForm;
import com.msas.MSAS.UIControllers.UsefulModules.Grids.MSEditableDeletableGrid;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.VaadinIcon;

public abstract class MSAddableCrud<T, R extends JpaRepository<T, ?>> extends
		MSCrud<T, R, MSEditableDeletableGrid<T, R>> {

	private static final long serialVersionUID = 5212473071566643464L;

	@Override
	protected void initComponents() {
		super.initComponents();

		this.initForm();
	}

	private void initForm() {
		this.grid.getLastColumnRenderer().setForm(this.getForm());

		this.getForm().setOnPersistSuccessfullCallback(entity -> {
			this.grid.getDataProvider().refreshAll();
		});
	}

	@Override
	protected Set<Component> getRightSideToolBarComponents() {
		MSButton insertButton = new MSButton(this.addButtonLabel(), "success",
				VaadinIcon.PLUS_CIRCLE_O);
		insertButton.setWidthFull();
		insertButton.addClickListener(event -> {
			this.getForm().setBindedEntity(this.getForm().instanciateEntity());
			this.getForm().setUpdate(false);
			this.getForm().open();
		});

		Set<Component> result = super.getRightSideToolBarComponents();
		result.add(insertButton);

		return result;
	}

	protected abstract String addButtonLabel();

	protected abstract MSForm<T> getForm();
}

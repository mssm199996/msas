package com.msas.MSAS.UIControllers.UsefulModules.GridCells;

import java.util.function.Consumer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msas.MSAS.UIControllers.UsefulModules.Button.MSButton;
import com.msas.MSAS.UIControllers.UsefulModules.Forms.MSForm;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class EditDeleteButtonsRenderer<T> extends DeleteButtonRenderer<T> {

	private static final long serialVersionUID = 5191336668979615174L;

	private MSForm<T> form;
	private Consumer<T> editSuccessfullCallback;

	public EditDeleteButtonsRenderer(JpaRepository<T, ?> repository,
			MSForm<T> form, Consumer<T> editSuccessfullCallback,
			Consumer<T> deleteSuccessfullCallback,
			Consumer<T> beforeDeleteCallback) {

		super(repository, deleteSuccessfullCallback, beforeDeleteCallback);

		this.form = form;
		this.editSuccessfullCallback = editSuccessfullCallback;
	}

	@Override
	public VerticalLayout createComponent(T item) {
		VerticalLayout layout = super.createComponent(item);
		
		Icon editIcon = VaadinIcon.EDIT.create();
		editIcon.setColor("orange");

		MSButton editButton = new MSButton("primary", VaadinIcon.EDIT);
		editButton.addClickListener(event -> {
			this.form.setUpdate(true);
			this.form.setBindedEntity(item);
			this.form.open();

			if (this.editSuccessfullCallback != null)
				this.editSuccessfullCallback.accept(item);
		});

		HorizontalLayout subLayout = (HorizontalLayout) layout
				.getComponentAt(0);
		subLayout.addComponentAsFirst(editButton);

		return layout;
	}

	public MSForm<T> getForm() {
		return form;
	}

	public void setForm(MSForm<T> form) {
		this.form = form;
	}

	@Override
	public String getColumnKey() {
		return "deleteEditColumn";
	}
}
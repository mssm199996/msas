package com.msas.MSAS.UIControllers.UsefulModules.MSComboBox;

import java.util.List;
import java.util.function.Consumer;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.data.provider.ListDataProvider;

public class MSComboBox<T> extends ComboBox<T> {

	private static final long serialVersionUID = -2136348081910684339L;

	public MSComboBox() {
		this.addFocusListener(event -> {
			this.getDataProvider().refreshAll();
		});
	}

	public MSComboBox(List<T> initialData) {
		this();
		this.setDataProvider(new ListDataProvider<T>(initialData));
	}

	public MSComboBox(List<T> initialData, Consumer<T> consumer) {
		this(initialData);

		this.addValueChangeListener(event -> {
			consumer.accept(event.getValue());
		});
	}
}

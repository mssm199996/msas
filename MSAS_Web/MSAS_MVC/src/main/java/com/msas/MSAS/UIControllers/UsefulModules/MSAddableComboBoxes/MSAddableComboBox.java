package com.msas.MSAS.UIControllers.UsefulModules.MSAddableComboBoxes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msas.MSAS.AdditionalBeans.FromComboBoxAddableBean;
import com.msas.MSAS.UIControllers.UsefulModules.AlertsDisplayer;
import com.msas.MSAS.UIControllers.UsefulModules.Button.MSButton;
import com.msas.MSAS.UIControllers.UsefulModules.MSComboBox.MSComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public abstract class MSAddableComboBox<T extends FromComboBoxAddableBean, R extends JpaRepository<T, ?>>
		extends HorizontalLayout {

	private static final long serialVersionUID = -5138243359629879828L;

	private MSButton addButton;
	private MSComboBox<T> comboBox;
	private R repository;

	public MSAddableComboBox(List<T> dataset, R repository) {
		this.initComponents(dataset, repository);

		this.repository = repository;
	}

	private void initComponents(List<T> dataset, R repository) {
		this.setDefaultVerticalComponentAlignment(Alignment.CENTER);
		this.setAlignItems(Alignment.CENTER);

		this.comboBox = this.constructComboBox(dataset, repository);
		this.comboBox.setWidthFull();

		this.addButton = new MSButton("success", VaadinIcon.PLUS_CIRCLE_O,
				event -> {
					AlertsDisplayer.displayTextAlert(this.getAddTitle(), this
							.getHeader(), this.getContent(), true,
							(textField) -> {
								String designation = textField.getValue();

								T entity = this.constructEntity();
								entity.setDesignation(designation);

								repository.save(entity);
								dataset.add(entity);

								this.comboBox.getDataProvider().refreshAll();
							});
				});
		this.addButton.setWidth("60px");
		this.addButton.setHeight("30px");

		this.add(this.comboBox);
		this.add(this.addButton);
	}

	public abstract String getAddTitle();

	public abstract String getHeader();

	public abstract String getContent();

	public abstract T constructEntity();

	public MSComboBox<T> constructComboBox(List<T> dataset, R repository) {
		return new MSComboBox<T>(dataset);
	}

	public MSComboBox<T> getComboBox() {
		return comboBox;
	}

	public R getRepository() {
		return repository;
	}
}

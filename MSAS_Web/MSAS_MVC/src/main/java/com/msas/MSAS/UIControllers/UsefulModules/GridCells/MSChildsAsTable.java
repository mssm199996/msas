package com.msas.MSAS.UIControllers.UsefulModules.GridCells;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.msas.MSAS.UIControllers.UsefulModules.Button.MSButton;
import com.msas.MSAS.UIControllers.UsefulModules.Grids.MSGrid;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class MSChildsAsTable<P, C, R extends JpaRepository<C, ?>>
		extends MSRenderer<P> {

	private static final long serialVersionUID = -8707461371397133758L;

	private MSButton button;
	private String colorClass;
	private VaadinIcon vaadinIcon;
	private MSGrid<C, R> grid;
	private R repository;
	private Dialog dialog;
	private P parent;

	public MSChildsAsTable(String colorClass, VaadinIcon vaadinIcon,
			R respository) {
		this.colorClass = colorClass;
		this.vaadinIcon = vaadinIcon;
		this.repository = respository;

		this.constructGrid();
		this.constructDialog();
	}

	public MSChildsAsTable(String colorClass, VaadinIcon vaadinIcon) {
		this(colorClass, vaadinIcon, null);
	}

	@Override
	public VerticalLayout createComponent(P item) {
		VerticalLayout layout = super.createComponent(item);
		layout.setDefaultHorizontalComponentAlignment(Alignment.END);
		layout.addClassName("p-0");

		HorizontalLayout subLayout = new HorizontalLayout();
		subLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
		subLayout.addClassName("p-0");

		this.button = new MSButton(this.colorClass, this.vaadinIcon);
		this.button.addClickListener(event -> {
			this.parent = item;
			this.grid.getDataProvider().refreshAll();
			this.dialog.open();
		});

		subLayout.add(this.button);
		layout.add(subLayout);

		return layout;
	}

	private void constructGrid() {
		this.grid = this.instanciateGrid(this.repository);
		this.grid.setEntityFetcher(pageable -> {
			return this.fetchChildsFromDatabase(this.parent, this.repository,
					pageable);
		});
		this.grid.setCounter(() -> {
			return this.fetchChildsSizeFromDatabase(this.parent,
					this.repository);
		});
	}

	private void constructDialog() {
		this.dialog = new Dialog();

		H1 titleHolder = new H1(this.getDialogTitle());
		titleHolder.addClassName("mt-0");
		titleHolder.setWidthFull();

		H3 headerHolder = new H3(this.getDialogHeaderText());
		headerHolder.addClassName("mt-0");
		headerHolder.setWidthFull();

		HorizontalLayout headerRow = new HorizontalLayout();
		headerRow.add(headerHolder);
		headerRow.add(this.getDialogHeaderIcon().create());
		headerRow.setWidthFull();

		MSButton closeButton = new MSButton("Fermer", "success",
				VaadinIcon.CHECK_CIRCLE_O, event -> {
					dialog.close();
				});

		HorizontalLayout footerRow = new HorizontalLayout();
		footerRow.add(closeButton);

		VerticalLayout footer = new VerticalLayout();
		footer.setDefaultHorizontalComponentAlignment(Alignment.END);
		footer.setAlignItems(Alignment.END);
		footer.setWidthFull();
		footer.add(footerRow);

		VerticalLayout container = new VerticalLayout();
		container.add(titleHolder);
		container.add(headerHolder);
		container.add(this.grid);
		container.add(footer);

		this.dialog.setWidth("1100px");
		this.dialog.setHeight("600px");

		this.dialog.add(container);
	}

	protected abstract MSGrid<C, R> instanciateGrid(R repository);

	protected abstract List<C> fetchChildsFromDatabase(P parent, R repository,
			Pageable pageable);

	protected abstract Integer fetchChildsSizeFromDatabase(P parent,
			R repository);

	protected abstract String getDialogTitle();

	protected abstract String getDialogHeaderText();

	protected abstract VaadinIcon getDialogHeaderIcon();

	public R getRepository() {
		return repository;
	}

	public void setRepository(R repository) {
		this.repository = repository;
		this.grid.setRepository(repository);
	}
}

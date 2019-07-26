package com.msas.MSAS.UIControllers.UsefulModules.Grids;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.msas.MSAS.UIControllers.UsefulModules.GridCells.MSRenderer;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;

public abstract class MSGrid<T, R extends JpaRepository<T, ?>> extends Grid<T> {

	private static final long serialVersionUID = -8396187303852328617L;

	private R repository;
	private Function<Pageable, List<T>> entityFetcher;
	private Supplier<Integer> counter;

	public MSGrid(Class<T> entityClass, R repository) {
		super(entityClass);

		this.setDataProvider(this.constructProvider());
		this.initGridColumns();
		this.setRepository(repository);
	}

	public MSGrid(Class<T> entityClass) {
		this(entityClass, null);
	}

	private void initGridColumns() {
		this.setColumns(this.simplePropertyNamesToShow());

		Collection<MSRenderer<T>> additionalColumns = this
				.getAdditionalColumns();

		for (MSRenderer<T> columnRenderer : additionalColumns) {
			Column<?> c = this.addColumn(columnRenderer);
			c.setKey(columnRenderer.getColumnKey());
			c.setWidth("25px");
		}

		this.onBeforeInitColumnHeaders();
		this.initGridHeaders();
	}

	private CallbackDataProvider<T, Void> constructProvider() {
		return DataProvider.fromCallbacks(
				query -> {
					Pageable pageable = PageRequest.of(query.getOffset(),
							query.getLimit());

					return this.entityFetcher.apply(pageable).stream();
				}, query -> this.counter.get());
	}

	protected void initGridHeaders() {
		Map<String, VaadinIcon> iconsTranslations = this
				.propertyNamesToColumnIconsTranslator();

		Set<Entry<String, String>> namesTranslations = this
				.propertyNamesToColumnHeadersTranslator().entrySet();

		for (Entry<String, String> entry : namesTranslations) {
			String propertyName = entry.getKey();
			String columnHeader = entry.getValue();
			VaadinIcon columnIcon = iconsTranslations.get(propertyName);

			Column<T> column = this.getColumnByKey(propertyName);
			column.setSortable(false);

			Paragraph headerLabel = new Paragraph();
			headerLabel.add(columnHeader);
			headerLabel.addClassName("violet-column-header");

			Icon icon = columnIcon.create();
			icon.setColor("rgba(98, 0, 238, 0.5)");
			icon.addClassName("icon-24");
			
			HorizontalLayout headerComponent = new HorizontalLayout();
			headerComponent
					.setDefaultVerticalComponentAlignment(Alignment.CENTER);
			headerComponent.setAlignItems(Alignment.CENTER);
			headerComponent.add(icon, headerLabel);
			headerComponent.setWidthFull();

			column.setHeader(headerComponent);
		}
	}

	protected abstract Map<String, String> propertyNamesToColumnHeadersTranslator();

	protected abstract Map<String, VaadinIcon> propertyNamesToColumnIconsTranslator();

	protected abstract String[] simplePropertyNamesToShow();

	public Function<Pageable, List<T>> getEntityFetcher() {
		return entityFetcher;
	}

	public void setEntityFetcher(Function<Pageable, List<T>> entityFetcher) {
		this.entityFetcher = entityFetcher;
	}

	public Supplier<Integer> getCounter() {
		return counter;
	}

	public void setCounter(Supplier<Integer> counter) {
		this.counter = counter;
	}

	public R getRepository() {
		return this.repository;
	}

	public void setRepository(R repository) {
		this.repository = repository;
	}

	protected abstract Collection<MSRenderer<T>> getAdditionalColumns();

	protected void onBeforeInitColumnHeaders() {
	}
}

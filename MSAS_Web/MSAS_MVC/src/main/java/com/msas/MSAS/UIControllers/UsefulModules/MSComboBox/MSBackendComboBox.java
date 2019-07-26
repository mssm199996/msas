package com.msas.MSAS.UIControllers.UsefulModules.MSComboBox;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vaadin.flow.data.provider.DataProvider;

public abstract class MSBackendComboBox<T, R extends JpaRepository<T, ?>>
		extends MSComboBox<T> {

	private static final long serialVersionUID = -594616412165196126L;

	protected R repository;

	public MSBackendComboBox(R repository) {
		super();

		this.repository = repository;

		this.setDataProvider(DataProvider.fromFilteringCallbacks(query -> {
			Pageable pageable = PageRequest.of(query.getOffset(),
					query.getLimit());
			
			return this.fetchFromDatabase(pageable).stream();
		}, query -> this.fetchCount()));
	}

	public abstract List<T> fetchFromDatabase(Pageable pageable);

	public abstract int fetchCount();
}

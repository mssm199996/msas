package com.msas.MSAS.UIControllers.UsefulModules.MSComboBox;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.vaadin.flow.data.provider.DataProvider;

public abstract class MSSemiBackendComboBox<T> extends MSComboBox<T> {

	private static final long serialVersionUID = -2749559935080551840L;

	public MSSemiBackendComboBox() {
		super();

		this.setDataProvider(DataProvider.fromFilteringCallbacks(
				query -> {
					Pageable pageable = PageRequest.of(query.getOffset(),
							query.getLimit());

					return this.fetchFromDatabase(pageable).stream();
				}, query -> this.fetchCount()));
	}

	public abstract List<T> fetchFromDatabase(Pageable pageable);

	public abstract int fetchCount();
}

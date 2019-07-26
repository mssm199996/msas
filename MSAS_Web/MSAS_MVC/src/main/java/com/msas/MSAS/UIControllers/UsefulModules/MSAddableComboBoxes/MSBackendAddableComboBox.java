package com.msas.MSAS.UIControllers.UsefulModules.MSAddableComboBoxes;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.msas.MSAS.AdditionalBeans.FromComboBoxAddableBean;
import com.msas.MSAS.UIControllers.UsefulModules.MSComboBox.MSBackendComboBox;
import com.msas.MSAS.UIControllers.UsefulModules.MSComboBox.MSComboBox;

public abstract class MSBackendAddableComboBox<T extends FromComboBoxAddableBean, R extends JpaRepository<T, ?>>
		extends MSAddableComboBox<T, R> {
	
	private static final long serialVersionUID = -3522488191636841350L;

	public MSBackendAddableComboBox(R repository) {
		super(new LinkedList<>(), repository);
	}

	@Override
	public MSComboBox<T> constructComboBox(List<T> dataset, R repository) {
		return new MSBackendComboBox<T, R>(repository) {

			private static final long serialVersionUID = 1L;

			@Override
			public List<T> fetchFromDatabase(Pageable pageable) {
				return repository.findAll(pageable).getContent();
			}

			@Override
			public int fetchCount() {
				return (int) repository.count();
			}
		};
	}
}

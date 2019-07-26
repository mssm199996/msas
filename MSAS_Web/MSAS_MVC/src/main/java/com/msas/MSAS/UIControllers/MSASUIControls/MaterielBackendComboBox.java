package com.msas.MSAS.UIControllers.MSASUIControls;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.msas.MSAS.DomainModel.Authentification.Materiel;
import com.msas.MSAS.Repositories.MaterielRepository;
import com.msas.MSAS.UIControllers.UsefulModules.MSComboBox.MSBackendComboBox;

public class MaterielBackendComboBox extends
		MSBackendComboBox<Materiel, MaterielRepository> {

	private static final long serialVersionUID = -6469281502748669481L;

	public MaterielBackendComboBox(MaterielRepository materielRepository) {
		super(materielRepository);
	}

	@Override
	public List<Materiel> fetchFromDatabase(Pageable pageable) {
		return this.repository.findAvailableMateriel(pageable);
	}

	@Override
	public int fetchCount() {
		return this.repository.countAvailableMateriel();
	}
}

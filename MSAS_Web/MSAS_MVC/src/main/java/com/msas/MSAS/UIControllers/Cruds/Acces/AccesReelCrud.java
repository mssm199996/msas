package com.msas.MSAS.UIControllers.Cruds.Acces;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.msas.MSAS.DomainModel.Access.AccesReel;
import com.msas.MSAS.Repositories.AccesReelRepository;
import com.msas.MSAS.UIControllers.Grids.OperationAccess.AccesReelGrid;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class AccesReelCrud extends
		OperationAccesCrud<AccesReel, AccesReelRepository, AccesReelGrid> {

	private static final long serialVersionUID = -4518516888807130100L;

	@Autowired
	private AccesReelRepository accesReelRepository;

	@Override
	protected AccesReelGrid constructGrid() {
		return new AccesReelGrid(this.accesReelRepository);
	}

	@Override
	protected List<AccesReel> fetchEntitiesFromDatabase(Pageable pageable) {
		LocalDate dateDebut = this.dateDebut.getValue();
		LocalDate dateFin = this.dateFin.getValue();

		return this.accesReelRepository
				.findByDate(dateDebut, dateFin, pageable);
	}

	@Override
	protected Integer countExpectedFetch() {
		LocalDate dateDebut = this.dateDebut.getValue();
		LocalDate dateFin = this.dateFin.getValue();

		return this.accesReelRepository.countByDate(dateDebut, dateFin);
	}

}

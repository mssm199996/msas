package com.msas.MSAS.UIControllers.Cruds.Signal;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.msas.MSAS.DomainModel.Signal.Intrusion;
import com.msas.MSAS.Repositories.IntrusionRepository;
import com.msas.MSAS.UIControllers.Cruds.Others.SalleEventCrud;
import com.msas.MSAS.UIControllers.Grids.Signal.IntrusionGrid;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class IntrusionCrud extends
		SalleEventCrud<Intrusion, IntrusionRepository, IntrusionGrid> {

	private static final long serialVersionUID = -7163706509361796187L;

	@Autowired
	private IntrusionRepository intrusionRepository;

	@Override
	protected IntrusionGrid constructGrid() {
		return new IntrusionGrid(this.intrusionRepository);
	}

	@Override
	protected List<Intrusion> fetchEntitiesFromDatabase(Pageable pageable) {
		LocalDate dateDebut = this.dateDebut.getValue();
		LocalDate dateFin = this.dateFin.getValue();

		return this.intrusionRepository
				.findByDate(dateDebut, dateFin, pageable);
	}

	@Override
	protected Integer countExpectedFetch() {
		LocalDate dateDebut = this.dateDebut.getValue();
		LocalDate dateFin = this.dateFin.getValue();

		return this.intrusionRepository.countByDate(dateDebut, dateFin);
	}
}

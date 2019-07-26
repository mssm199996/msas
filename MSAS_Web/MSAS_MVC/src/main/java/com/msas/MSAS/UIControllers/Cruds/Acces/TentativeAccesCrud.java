package com.msas.MSAS.UIControllers.Cruds.Acces;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.msas.MSAS.DomainModel.Access.TentativeAcces;
import com.msas.MSAS.Repositories.TentativeAccesRepository;
import com.msas.MSAS.UIControllers.Grids.OperationAccess.TentativeAccesGrid;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class TentativeAccesCrud
		extends
		OperationAccesCrud<TentativeAcces, TentativeAccesRepository, TentativeAccesGrid> {

	private static final long serialVersionUID = 107366646559266423L;

	@Autowired
	private TentativeAccesRepository tentativeAccesRepository;

	@Override
	protected TentativeAccesGrid constructGrid() {
		return new TentativeAccesGrid(this.tentativeAccesRepository);
	}

	@Override
	protected List<TentativeAcces> fetchEntitiesFromDatabase(Pageable pageable) {
		LocalDate dateDebut = this.dateDebut.getValue();
		LocalDate dateFin = this.dateFin.getValue();

		return this.tentativeAccesRepository.findByDate(dateDebut, dateFin,
				pageable);
	}

	@Override
	protected Integer countExpectedFetch() {
		LocalDate dateDebut = this.dateDebut.getValue();
		LocalDate dateFin = this.dateFin.getValue();

		return this.tentativeAccesRepository.countByDate(dateDebut, dateFin);
	}
}

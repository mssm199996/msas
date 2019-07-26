package com.msas.MSAS.UIControllers.Grids.Signal;

import java.util.Collection;
import java.util.HashSet;

import com.msas.MSAS.DomainModel.Signal.Intrusion;
import com.msas.MSAS.Repositories.IntrusionRepository;
import com.msas.MSAS.UIControllers.Grids.Others.SalleEventGrid;
import com.msas.MSAS.UIControllers.UsefulModules.GridCells.MSRenderer;

public class IntrusionGrid extends
		SalleEventGrid<Intrusion, IntrusionRepository> {

	private static final long serialVersionUID = 6776945345916031518L;

	public IntrusionGrid(IntrusionRepository repository) {
		super(Intrusion.class, repository);
	}

	@Override
	protected String[] simplePropertyNamesToShow() {
		return new String[] { "salle.designation", "date", "heure" };
	}

	@Override
	protected Collection<MSRenderer<Intrusion>> getAdditionalColumns() {
		return new HashSet<>();
	}
}

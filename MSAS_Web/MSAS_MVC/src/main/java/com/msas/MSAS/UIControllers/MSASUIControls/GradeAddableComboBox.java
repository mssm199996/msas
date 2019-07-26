package com.msas.MSAS.UIControllers.MSASUIControls;

import java.util.List;

import com.msas.MSAS.DomainModel.Categorizers.Grade;
import com.msas.MSAS.Repositories.GradeRepository;
import com.msas.MSAS.UIControllers.UsefulModules.MSAddableComboBoxes.MSAddableComboBox;

public class GradeAddableComboBox extends
		MSAddableComboBox<Grade, GradeRepository> {

	private static final long serialVersionUID = 2241103177172163718L;

	public GradeAddableComboBox(List<Grade> dataset, GradeRepository repository) {
		super(dataset, repository);
	}

	@Override
	public String getAddTitle() {
		return "Nouveau grade";
	}

	@Override
	public String getHeader() {
		return "Veuillez inscrire la désignation du grade";
	}

	@Override
	public String getContent() {
		return "Désignation";
	}

	@Override
	public Grade constructEntity() {
		return new Grade();
	}
}

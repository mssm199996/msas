package com.msas.MSAS.Configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import com.msas.MSAS.DomainModel.Authentification.Materiel;
import com.msas.MSAS.DomainModel.Categorizers.Grade;
import com.msas.MSAS.DomainModel.Categorizers.Metier;
import com.msas.MSAS.Repositories.GradeRepository;
import com.msas.MSAS.Repositories.MaterielRepository;
import com.msas.MSAS.Repositories.MetierRepository;

@Component
@ApplicationScope
public class ListsHolder {

	@Autowired
	private MetierRepository metierRepository;

	@Autowired
	private GradeRepository gradeRepository;

	@Autowired
	private MaterielRepository materielRepository;

	@Bean
	public List<Metier> metiersList() {
		return this.metierRepository.findAll();
	}

	@Bean
	public List<Grade> gradesList() {
		return this.gradeRepository.findAll();
	}

	@Bean
	public List<Materiel> materielsList() {
		return this.materielRepository.findAll();
	}
}

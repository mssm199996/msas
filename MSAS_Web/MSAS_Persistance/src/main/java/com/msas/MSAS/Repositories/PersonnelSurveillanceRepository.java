package com.msas.MSAS.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.msas.MSAS.DomainModel.Authentification.Salle;
import com.msas.MSAS.DomainModel.Personnel.PersonnelSurveillance;

@Repository
public interface PersonnelSurveillanceRepository extends
		JpaRepository<PersonnelSurveillance, Integer> {

	@Query("FROM PersonnelSurveillance p LEFT OUTER JOIN FETCH p.salles WHERE p = :personnelSurveillance")
	public PersonnelSurveillance findByEntityWithSalles(
			@Param("personnelSurveillance") PersonnelSurveillance personnelSurveillance);

	public List<PersonnelSurveillance> findBySalles(@Param("salles") Salle salle);

	public Integer countBySalles(@Param("salles") Salle salle);
}

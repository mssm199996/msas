package com.msas.MSAS.Repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.msas.MSAS.DomainModel.Access.TentativeAcces;

@Repository
public interface TentativeAccesRepository extends
		JpaRepository<TentativeAcces, Integer> {

	@Query("FROM TentativeAcces tentative "
			+ "INNER JOIN FETCH tentative.personnel "
			+ "INNER JOIN FETCH tentative.salle "
			+ "WHERE ("
				+ "tentative.date BETWEEN :dateDebut AND :dateFin"
			+ ")")
	public List<TentativeAcces> findByDate(
			@Param("dateDebut") LocalDate dateDebut, 
			@Param("dateFin") LocalDate dateFin, 
			Pageable pageable);
	
	@Query("SELECT COUNT(*) "
			+ "FROM TentativeAcces tentative "
			+ "INNER JOIN tentative.personnel "
			+ "INNER JOIN tentative.salle "
			+ "WHERE ("
				+ "tentative.date BETWEEN :dateDebut AND :dateFin"
			+ ")")
	public Integer countByDate(
			@Param("dateDebut") LocalDate dateDebut, 
			@Param("dateFin") LocalDate dateFin);
}

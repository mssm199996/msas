package com.msas.MSAS.Repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.msas.MSAS.DomainModel.Signal.Intrusion;

@Repository
public interface IntrusionRepository extends JpaRepository<Intrusion, Integer> {

	@Query("FROM Intrusion intrusion "
			+ "INNER JOIN FETCH intrusion.salle "
			+ "WHERE ("
				+ "intrusion.date BETWEEN :dateDebut AND :dateFin"
			+ ")")
	public List<Intrusion> findByDate(
			@Param("dateDebut") LocalDate dateDebut, 
			@Param("dateFin") LocalDate dateFin, 
			Pageable pageable);
	
	@Query("SELECT COUNT(*) "
			+ "FROM Intrusion intrusion "
			+ "INNER JOIN intrusion.salle "
			+ "WHERE ("
				+ "intrusion.date BETWEEN :dateDebut AND :dateFin"
			+ ")")
	public Integer countByDate(
			@Param("dateDebut") LocalDate dateDebut, 
			@Param("dateFin") LocalDate dateFin);
}

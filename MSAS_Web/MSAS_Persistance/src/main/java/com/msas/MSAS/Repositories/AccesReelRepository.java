package com.msas.MSAS.Repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.msas.MSAS.DomainModel.Access.AccesReel;

@Repository
public interface AccesReelRepository extends JpaRepository<AccesReel, Integer> {

	@Query("FROM AccesReel acces "
			+ "INNER JOIN FETCH acces.personnel "
			+ "INNER JOIN FETCH acces.salle "
			+ "WHERE ("
				+ "acces.date BETWEEN :dateDebut AND :dateFin"
			+ ")")
	public List<AccesReel> findByDate(
			@Param("dateDebut") LocalDate dateDebut, 
			@Param("dateFin") LocalDate dateFin, 
			Pageable pageable);
	
	@Query("SELECT COUNT(*) "
			+ "FROM AccesReel acces "
			+ "INNER JOIN acces.personnel "
			+ "INNER JOIN acces.salle "
			+ "WHERE ("
				+ "acces.date BETWEEN :dateDebut AND :dateFin"
			+ ")")
	public Integer countByDate(
			@Param("dateDebut") LocalDate dateDebut, 
			@Param("dateFin") LocalDate dateFin);
}

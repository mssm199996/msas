package com.msas.MSAS.Repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.msas.MSAS.DomainModel.Authentification.Salle;
import com.msas.MSAS.DomainModel.Droits.Droit;
import com.msas.MSAS.DomainModel.Personnel.Personnel;

@Repository
public interface DroitRepository extends JpaRepository<Droit, Integer> {

	@Query("SELECT droit FROM Droit droit "
			+ "INNER JOIN FETCH droit.personnel personnel "
			+ "INNER JOIN FETCH personnel.pieceIdentitee "
			+ "INNER JOIN FETCH droit.salle salle "
			+ "WHERE ("
			+ 		"(:salle IS NULL OR salle = :salle) AND "
			+ 		"(:personnel IS NULL OR personnel = :personnel)"
			+ ")")
	public List<Droit> findBySalleAndPersonnelWithSalleAndPersonnel(
			@Param("salle") Salle salle, 
			@Param("personnel") Personnel personnel, 
			Pageable pageable
	);

	@Query("SELECT COUNT(*) FROM Droit droit "
			+ "INNER JOIN droit.personnel personnel "
			+ "INNER JOIN droit.salle salle "
			+ "WHERE ("
			+ 		"(:salle IS NULL OR salle = :salle) AND "
			+ 		"(:personnel IS NULL OR personnel = :personnel)"
			+ ")")
	public Integer countBySalleAndPersonnel(
			@Param("salle") Salle salle, 
			@Param("personnel") Personnel personnel
	);
}

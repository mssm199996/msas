package com.msas.MSAS.Repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.msas.MSAS.DomainModel.Categorizers.Grade;
import com.msas.MSAS.DomainModel.Categorizers.Metier;
import com.msas.MSAS.DomainModel.Personnel.Personnel;

@Repository
public interface PersonnelRepository extends JpaRepository<Personnel, Integer>{
	
	@Query(value = 
			  "FROM Personnel p "
			+ "LEFT OUTER JOIN FETCH p.pieceIdentitee pi "
			+ "LEFT OUTER JOIN FETCH p.metier m "
			+ "LEFT OUTER JOIN FETCH p.grade g "
			+ "WHERE ("
				+ "(CONCAT(p.nom, ' ', p.prenom) LIKE %:nomPrenom%) AND "
				+ "(COALESCE(pi.serialNumber, :serialNumber) LIKE %:serialNumber%) AND "
				+ "(:metier IS NULL OR m = :metier) AND "
				+ "(:grade IS NULL OR g = :grade)"
			+ ")")
	public List<Personnel> findAll(
			@Param("nomPrenom") String nomPrenom, 
			@Param("serialNumber") String serialNumber,
			@Param("metier") Metier metier,
			@Param("grade") Grade grade,
			Pageable pageable);
	
	
	@Query(value = 
			  "SELECT COUNT(p) "
			+ "FROM Personnel p "
			+ "LEFT OUTER JOIN p.pieceIdentitee pi "
			+ "LEFT OUTER JOIN p.metier m "
			+ "LEFT OUTER JOIN p.grade g "
			+ "WHERE ("
				+ "(CONCAT(p.nom, ' ', p.prenom) LIKE %:nomPrenom%) AND "
				+ "(COALESCE(pi.serialNumber, :serialNumber) LIKE %:serialNumber%) AND "
				+ "(:metier IS NULL OR m = :metier) AND "
				+ "(:grade IS NULL OR g = :grade)"
			+ ")")
	public int count(
			@Param("nomPrenom") String nomPrenom, 
			@Param("serialNumber") String serialNumber,
			@Param("metier") Metier metier,
			@Param("grade") Grade grade);

	@Query("FROM Personnel p LEFT OUTER JOIN FETCH p.pieceIdentitee pi WHERE (pi.serialNumber = :serialNumber)")
	public Personnel findBySerialNumber(@Param("serialNumber") String serialNumber);
}

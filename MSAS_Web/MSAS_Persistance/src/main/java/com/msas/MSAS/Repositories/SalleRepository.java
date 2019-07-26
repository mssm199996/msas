package com.msas.MSAS.Repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.msas.MSAS.DomainModel.Authentification.Salle;

@Repository
public interface SalleRepository extends JpaRepository<Salle, Integer> {

	@Query(value = "FROM Salle s " + "LEFT OUTER JOIN FETCH s.materiel m "
			+ "WHERE (" + "(s.designation LIKE %:designation%) AND "
			+ "(COALESCE(m.serialNumber, :serialNumber) LIKE %:serialNumber%)"
			+ ")")
	public List<Salle> findAll(@Param("designation") String designation,
			@Param("serialNumber") String serialNumber, Pageable pageable);

	@Query(value = "SELECT COUNT(s) FROM Salle s "
			+ "LEFT OUTER JOIN s.materiel m " + "WHERE ("
			+ "(s.designation LIKE %:designation%) AND "
			+ "(COALESCE(m.serialNumber, :serialNumber) LIKE %:serialNumber%)"
			+ ")")
	public int count(@Param("designation") String designation,
			@Param("serialNumber") String serialNumber);

	@Query("FROM Salle s LEFT OUTER JOIN FETCH s.droits droits LEFT OUTER JOIN FETCH droits.personnel WHERE s = :salle")
	public Salle findByEntityWithDroits(@Param("salle") Salle salle);

	@Query("FROM Salle s LEFT OUTER JOIN FETCH s.materiel m WHERE m.serialNumber = :materielSerialNumber")
	public Salle findByMaterielSerialNumber(
			@Param("materielSerialNumber") String materialSerialNumber);
}

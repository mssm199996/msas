package com.msas.MSAS.Repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msas.MSAS.DomainModel.Authentification.Materiel;

@Repository
public interface MaterielRepository extends JpaRepository<Materiel, Integer> {

	@Query("FROM Materiel m LEFT OUTER JOIN FETCH m.salle s WHERE ((SELECT COUNT(*) FROM Salle s WHERE s.materiel = m) = 0)")
	public List<Materiel> findAvailableMateriel(Pageable pageable);

	@Query("SELECT COUNT(m) FROM Materiel m WHERE ((SELECT COUNT(*) FROM Salle s WHERE s.materiel = m) = 0)")
	public int countAvailableMateriel();

	@Query("FROM Materiel m LEFT OUTER JOIN FETCH m.salle")
	public List<Materiel> findAllWithSalle(Pageable pageable);

	@Query("FROM Materiel m LEFT OUTER JOIN FETCH m.salle WHERE m.serialNumber = :serialNumber")
	public Materiel findBySerialNumberWithMateriel(@Param("serialNumber") String serialNumber);

	@Modifying
	@Transactional
	@Query("UPDATE Materiel m SET m.etat = :etat WHERE (m.lastUpdate <= :limit)")
	public void updateAllMaterielEtat(@Param("etat") boolean etat,
			@Param("limit") LocalDateTime limit);
}

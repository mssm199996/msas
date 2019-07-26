package com.msas.MSAS.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.msas.MSAS.DomainModel.Authentification.Compte;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Integer> {

	public Compte findByUsername(String username);
}

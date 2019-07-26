package com.msas.MSAS.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.msas.MSAS.DomainModel.Categorizers.Metier;

@Repository
public interface MetierRepository extends JpaRepository<Metier, Integer> {
}

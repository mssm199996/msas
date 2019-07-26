package com.msas.MSAS.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.msas.MSAS.DomainModel.Configuration.AppConfiguration;

@Repository
public interface AppConfigurationRepository extends
		JpaRepository<AppConfiguration, Integer> {

}

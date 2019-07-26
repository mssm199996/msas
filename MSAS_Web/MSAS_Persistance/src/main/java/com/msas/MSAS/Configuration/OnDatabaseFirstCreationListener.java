package com.msas.MSAS.Configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.msas.MSAS.DomainModel.Authentification.Compte;
import com.msas.MSAS.Repositories.CompteRepository;

@Configuration
public class OnDatabaseFirstCreationListener {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CompteRepository compteRepository;

	@PostConstruct
	public void fillDatabaseWithInitialData() {
		if (this.isFirstCreation()) {
			Compte compte = new Compte();
			compte.setUsername("admin");
			compte.setPassword(this.passwordEncoder.encode("admin"));

			this.compteRepository.save(compte);
		}
	}

	public boolean isFirstCreation() {
		return this.compteRepository.count() == 0;
	}
}

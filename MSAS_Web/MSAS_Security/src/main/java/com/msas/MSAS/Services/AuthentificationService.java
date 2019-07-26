package com.msas.MSAS.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.msas.MSAS.DomainModel.Authentification.Compte;
import com.msas.MSAS.Repositories.CompteRepository;

@Service
public class AuthentificationService implements UserDetailsService {

	@Autowired
	private CompteRepository compteRepository;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Compte compte = this.compteRepository.findByUsername(username);

		if (compte == null)
			throw new UsernameNotFoundException("Wrong username");

		return compte;
	}
}

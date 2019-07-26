package com.msas.MSAS.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.msas.MSAS.Services.AuthentificationService;

public class AuthenticationProvider extends DaoAuthenticationProvider {

	@Autowired
	private AuthentificationService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;

		String username = auth.getName();
		String password = auth.getCredentials().toString();
		
		UserDetails user = this.userDetailsService.loadUserByUsername(username);
		
		if (user != null && this.passwordEncoder.matches(password, user.getPassword())) {
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		}

		throw new BadCredentialsException(
				"Nom d'utilisateur ou mot de passe incorrecte");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
}

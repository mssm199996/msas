package com.msas.MSAS.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.msas.MSAS.Services.AuthentificationService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthentificationService authentificationService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(this.authentificationService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
				.disable()
				.exceptionHandling()

				// --------------------------------------------------------------

				.and()
				.authenticationProvider(authenticationProvider())
				.formLogin()
				.loginPage("/loginPage")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/personnelList", true)
				.failureUrl("/loginPage?error")

				// --------------------------------------------------------------

				.and()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/loginPage")
				.invalidateHttpSession(true)

				// --------------------------------------------------------------
				
				.and()
				.authorizeRequests()
				.antMatchers("/loginPage", "/login").anonymous()
				
				// --------------------------------------------------------------
				
				.antMatchers(
						"/logout", 
						"/admin/**").authenticated()
				
				// --------------------------------------------------------------

				.antMatchers(
						"/css/**", 
						"/font/**", 
						"/img/**", 
						"/js/**",
						"/assets/**",
						"/Scripts/**",
						"/Other/**",
						"/frontend/**",
						"/VAADIN/**",
						"/PUSH/**",
						"/UIDL/**",
						"/error/**",
						"/accessDenied/**",
						"/vaadinServlet/**",
						"/HEARTBEAT/**",
						"/resources/**",
						"/").permitAll()		
				.anyRequest().authenticated();		
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		AuthenticationProvider provider = new AuthenticationProvider();
		provider.setUserDetailsService(this.authentificationService);

		return provider;
	}
}

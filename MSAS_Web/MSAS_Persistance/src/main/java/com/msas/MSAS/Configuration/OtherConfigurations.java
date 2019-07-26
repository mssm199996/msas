package com.msas.MSAS.Configuration;

import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.msas.MSAS.DomainModel.Configuration.AppConfiguration;
import com.msas.MSAS.Repositories.AppConfigurationRepository;

@Configuration
public class OtherConfigurations {

	@Autowired
	private AppConfigurationRepository appConfigurationRepository;

	@Bean
	public AppConfiguration mainConfiguration() {
		Optional<AppConfiguration> mainConfig = this.appConfigurationRepository
				.findById(1);
		
		if (mainConfig.isPresent())
			return mainConfig.get();
		else {
			AppConfiguration config = new AppConfiguration();
			config.setEmailIntrusions("msasproject@gmail.com");
			config.setMdpIntrusions("msasproject2019");
			config.setGsmPortNumberIntrusions("");

			this.appConfigurationRepository.save(config);

			return config;
		}
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");

		return messageSource;
	}

	@Bean
	public LocalValidatorFactoryBean getValidator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());

		return bean;
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		sessionLocaleResolver.setDefaultLocale(Locale.FRANCE);

		return sessionLocaleResolver;
	}
}

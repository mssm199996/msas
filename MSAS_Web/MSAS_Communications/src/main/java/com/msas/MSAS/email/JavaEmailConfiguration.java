package com.msas.MSAS.email;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.msas.MSAS.DomainModel.Configuration.AppConfiguration;

@Configuration
public class JavaEmailConfiguration {

	@Autowired
	private AppConfiguration mainConfiguration;

	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost("smtp.gmail.com");
		javaMailSender.setPort(587);

		javaMailSender.setUsername(this.mainConfiguration.getEmailIntrusions());
		javaMailSender.setPassword(this.mainConfiguration.getMdpIntrusions());

		Properties props = javaMailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return javaMailSender;
	}
}

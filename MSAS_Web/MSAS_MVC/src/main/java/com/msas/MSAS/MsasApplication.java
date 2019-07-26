package com.msas.MSAS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MsasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsasApplication.class, args);
	}
}

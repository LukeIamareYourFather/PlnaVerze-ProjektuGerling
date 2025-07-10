package com.danger.insurance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Entry point for the Insurance Recorder Spring Boot application.
 * <p>
 * Bootstraps the entire application context, enables JPA repositories,
 * and launches the web server.
 * </p>
 */
@SpringBootApplication
@EnableJpaRepositories
public class InsuranceRecorderApplication {
	
	/**
	 * Starts the Spring Boot application for the Insurance Recorder.
	 * 
	 * @param args command-line arguments. (unused)
	 */
	public static void main(String[] args) {
		SpringApplication.run(InsuranceRecorderApplication.class, args);
	}
	
}
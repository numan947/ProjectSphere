package com.numan947.pmbackend;

import com.numan947.pmbackend.role.Role;
import com.numan947.pmbackend.role.RoleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;

/**
 * ProjectManagerBackendApplication is the entry point for the Spring Boot application.
 * It initializes the application and sets up initial roles in the database.
 *
 * Annotations:
 * - @SpringBootApplication: Indicates a Spring Boot application.
 * - @EnableAsync: Enables asynchronous processing.
 * - @EnableJpaAuditing: Enables JPA Auditing.
 */
@SpringBootApplication
@EnableAsync
@EnableJpaAuditing
public class ProjectManagerBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProjectManagerBackendApplication.class, args);
	}

	@Value("${application.roles.rolenames}")
	List<String> rolesNames;

	@Value("${application.roles.roledescriptions}")
	List<String> rolesDescriptions;

	/**
	 * CommandLineRunner bean to initialize roles in the database at startup.
	 *
	 * @param roleRepository the RoleRepository to interact with the Role entities.
	 * @return a CommandLineRunner instance.
	 */
	@Bean
	public CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
		return args -> {
			for (int i = 0; i < rolesNames.size(); i++) {
				if (roleRepository.findByName(rolesNames.get(i)).isPresent()) {
					continue;
				}
				Role role = Role.builder()
						.name(rolesNames.get(i))
						.description(rolesDescriptions.get(i))
						.build();
				roleRepository.save(role);
			}
		};
	}
}
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
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.List;

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
				role.setName(rolesNames.get(i));
				role.setDescription(rolesDescriptions.get(i));
				roleRepository.save(role);
			}
		};
	}

}

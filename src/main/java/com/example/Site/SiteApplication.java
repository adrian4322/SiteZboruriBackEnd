package com.example.Site;

import com.example.Site.models.Utilizator;
import com.example.Site.models.UtilizatorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiteApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*@Bean
	public CommandLineRunner commandLineRunner(UtilizatorService utilizatorService) {
		return args -> {
			var utilizator = Utilizator.builder()
					.username("adrian")
					.parola("adrian4322")
					.email("adrian@gmail.com")
					.build();
			utilizatorService.saveUtilizator(utilizator);
		};
	}*/

}

package com.example.aecbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AecbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AecbackendApplication.class, args);
	}

}

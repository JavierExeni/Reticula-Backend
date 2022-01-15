package com.sd.reticula;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.sd.reticula.model")
@EnableJpaRepositories("com.sd.reticula.repository")
public class RestReticulaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestReticulaApplication.class, args);
	}

}

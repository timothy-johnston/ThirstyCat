package com.tj.thirstyCat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ThirstyCatApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThirstyCatApplication.class, args);
	}

}

package com.tj.thirstycat;


import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tj.thirstycat.repository.DrinkRepository;

import entity.Drink;

@Configuration
@Slf4j
class LoadDatabase {

//	@Bean
//	CommandLineRunner initDatabase(DrinkRepository repository) {
//		return args -> {
//			log.info("Preloading " + repository.save(new Drink(1)));
//			log.info("Preloading " + repository.save(new Drink(2)));
//		};
//	}
}

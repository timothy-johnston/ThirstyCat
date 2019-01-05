package com.tj.thirstycat.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import entity.Drink;

public interface DrinkRepository extends CrudRepository<Drink, Long> {

	//Below method commented out because I think it's impelmentation is in CrudRepository
	//List<Drink> findAll();
	
}

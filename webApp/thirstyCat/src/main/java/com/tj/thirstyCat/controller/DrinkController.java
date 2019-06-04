package com.tj.thirstyCat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tj.thirstyCat.model.Drink;
import com.tj.thirstyCat.repository.DrinkRepository;

@RestController
public class DrinkController {

	@Autowired
	private DrinkRepository drinkRepository;
	
	@GetMapping("/apiTest")
	public String apiTest() {
		return "yer a coder harry";
	}
	
	@GetMapping("/allDrinks")
	public List<Drink> getAllDrinks() {
		return drinkRepository.findAll();
	}
	
	@PostMapping("/newDrink")
	public String persistDrink() {
		Drink drink = new Drink(null, null);
		drinkRepository.save(drink);
		return "good job man idk";
	}
	
}

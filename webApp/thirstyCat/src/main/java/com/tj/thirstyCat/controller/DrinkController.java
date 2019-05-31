package com.tj.thirstyCat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tj.thirstyCat.model.Drink;
import com.tj.thirstyCat.service.DrinkService;

@RestController
public class DrinkController {

	@Autowired
	private DrinkService drinkService;
	
	@PostMapping("/newDrink")
	public String persistDrink() {
		Drink drink = new Drink(null, null);
		drinkService.addDrink(drink);
		return "good job man idk";
	}
	
}

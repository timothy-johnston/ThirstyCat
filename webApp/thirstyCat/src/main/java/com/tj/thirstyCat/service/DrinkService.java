package com.tj.thirstyCat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tj.thirstyCat.model.Drink;
import com.tj.thirstyCat.model.Image;
import com.tj.thirstyCat.repository.DrinkRepository;

@Service
public class DrinkService {
	
	@Autowired
	private DrinkRepository drinkRepository;

	//Add drink info to database
	public void addDrink(Drink drink) {
		drinkRepository.save(drink);
	}
	
}

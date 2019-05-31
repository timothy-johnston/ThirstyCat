package com.tj.thirstyCat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tj.thirstyCat.model.Drink;
import com.tj.thirstyCat.repository.DrinkRepository;

@Service
public class DrinkService {

	@Autowired
	private DrinkRepository drinkRepository;
	
	public Drink addDrink(Drink drink) {
			return drinkRepository.save(drink);
	}
	
	public Drink retrieveDrink(Long id) {
		
		Drink drink = null;
		
		try {
			drink = drinkRepository.findById(id)
			        .orElseThrow(() -> new Exception());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return drink;
		
	}
		
		
	
}

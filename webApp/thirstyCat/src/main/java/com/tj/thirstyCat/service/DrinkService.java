package com.tj.thirstyCat.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tj.thirstyCat.model.Drink;
import com.tj.thirstyCat.repository.DrinkRepository;

@Service
public class DrinkService {
	
	@Autowired
	private DrinkRepository drinkRepository;

	//Add drink info to database
	public Drink addDrink(Drink drink) {
		return drinkRepository.save(drink);
	}

	public Optional<Drink> getLastDrink() {
//		return drinkRepository.findLastDrink();
		
		//Circular dependency fix if needed:
		//Change drinkRepository.findLastDrink to findLastDrinkId [return last id rather than calling this service to return last drink]
		Long lastDrinkId = drinkRepository.findLastDrinkId();
		return drinkRepository.findById(lastDrinkId);
		
	}

	public List<Drink> getAllDrinks() {
		return drinkRepository.findAll();
	}

	public Optional<Drink> getDrinkById(Long drinkId) {
		return drinkRepository.findById(drinkId);
	}
	
}

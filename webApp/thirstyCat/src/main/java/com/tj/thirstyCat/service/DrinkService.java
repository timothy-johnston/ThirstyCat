package com.tj.thirstyCat.service;

import java.util.ArrayList;
import java.util.List;

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

	public Drink getLastDrink() {
		return null;
		// TODO Auto-generated method stub
		
	}

	public List<Drink> getAllDrinks() {
		// TODO Auto-generated method stub
		return null;
	}

	public Drink getDrinkById(int drinkId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

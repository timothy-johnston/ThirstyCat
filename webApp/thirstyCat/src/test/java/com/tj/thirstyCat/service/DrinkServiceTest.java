package com.tj.thirstyCat.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.tj.thirstyCat.model.Drink;
import com.tj.thirstyCat.repository.DrinkRepository;

@RunWith(MockitoJUnitRunner.class)
public class DrinkServiceTest {

	@InjectMocks
	DrinkService drinkService;
	
	@Mock
	DrinkRepository drinkRepository;
	

	@Test
	public void addDrinkCallsRepositorySaveMethod() {
		
		Drink testDrink = new Drink(new Date(), new Date());
		
		drinkService.addDrink(testDrink);
		
		verify(drinkRepository, times(1)).save(testDrink);
		
	}
	
//	@Test
//	public void getLastDrinkGetsLastDrinkAddedToDatabase() {
//		
//		Date startDate = new Date();
//		Date endDate = new Date();
//		Drink firstAddedDrink = new Drink(startDate, endDate);
//		Drink secondAddedDrink = new Drink(null, null);
//		
//	}
	
	@Test
	public void getAllDrinksReturnsListOfAllDrinks() {
		
		List<Drink> expectedDrinks = new ArrayList<Drink>();
		Drink expected1 = new Drink();
		Drink expected2 = new Drink(new Date(), null);
		Drink expected3 = new Drink(null, new Date());
		expectedDrinks.add(expected1);
		expectedDrinks.add(expected2);
		expectedDrinks.add(expected3);
		when(drinkRepository.findAll()).thenReturn(expectedDrinks);
		
		List<Drink> returnedDrinks = drinkService.getAllDrinks();
		
		verify(drinkRepository, times(1)).findAll();
		assertEquals(expectedDrinks, returnedDrinks);
		
	}
	
	
	@Test
	public void getDrinkByIdCallsFindById() {

		Long drinkId = 3L;
		
		Drink returnedDrink = drinkService.getDrinkById(drinkId);
		
		verify(drinkRepository, times(1)).findById(drinkId);
		
	}


}

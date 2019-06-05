package com.tj.thirstyCat.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tj.thirstyCat.model.Drink;
import com.tj.thirstyCat.service.DrinkService;

@RestController("/api/drink")
public class DrinkController {

	@Autowired
	private DrinkService drinkService;
	
	/*
	 * Post request format:
	 	{
			"startTime": "2019-06-04T03:57:53.558",
			"endTime": "2019-06-04T03:59:53.558"
		}
	 */
	@PostMapping("/add")
	@ResponseBody
	public Drink persistDrink(@Valid @RequestBody Drink drink) {
		return(drinkService.addDrink(drink));
	}
	
	@GetMapping("/all")
	@ResponseBody
	public List<Drink> getAllDrinks() {
		return drinkService.getAllDrinks();
	}
	
	@GetMapping("/last")
	@ResponseBody
	public Optional<Drink> retrieveLastDrink() {
		return drinkService.getLastDrink();
	}

	@GetMapping("/drink/{drinkId}")
	@ResponseBody
	public Optional<Drink> getDrink(@PathVariable Long drinkId) {
		return drinkService.getDrinkById(drinkId);
	}
	
}

package com.tj.thirstyCat.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tj.thirstyCat.model.Drink;
import com.tj.thirstyCat.security.JwtResponse;
import com.tj.thirstyCat.security.JwtTokenUtil;
import com.tj.thirstyCat.service.CommonService;
import com.tj.thirstyCat.service.DrinkService;

@RestController
@RequestMapping("/api/drink")
public class DrinkController {

	@Autowired
	private DrinkService drinkService;
	
	@Autowired
	private CommonService commonService;
	
	
	/*
	 * Post request format:
		{
		"startTime": "2019-06-04T03:57:53.558",
		"endTime": "2019-06-04T03:59:53.558",
		"createdBy": "test"
		}
	 */
	@PostMapping("/addDrink")
	@ResponseBody
	public Drink persistDrink(@Valid @RequestBody Drink drink, HttpServletResponse response) {
		response = commonService.setResponseHeaderJWT(response);
		return(drinkService.addDrink(drink));
	}
	
	@GetMapping("/allDrinks")
	@ResponseBody
	public List<Drink> getAllDrinks(HttpServletResponse response) {
		response = commonService.setResponseHeaderJWT(response);
		return drinkService.getAllDrinks();
	}
	
	@GetMapping("/lastDrink")
	@ResponseBody
	public Optional<Drink> retrieveLastDrink(HttpServletResponse response) {
		response = commonService.setResponseHeaderJWT(response);
		return drinkService.getLastDrink();
	}

	@GetMapping("/drink/{drinkId}")
	@ResponseBody
	public Optional<Drink> getDrink(@PathVariable Long drinkId, HttpServletResponse response) {
		response = commonService.setResponseHeaderJWT(response);
		return drinkService.getDrinkById(drinkId);
	}
	
}

package com.tj.thirstyCat.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tj.thirstyCat.model.Drink;
import com.tj.thirstyCat.repository.DrinkRepository;

@RestController
public class DrinkController {

	@Autowired
	private DrinkRepository drinkRepository;
	
	//Returns csrf token
	//Is this secure? TODO: Research
	//Following https://stackoverflow.com/questions/33125598/how-to-handle-csrf-protection-with-spring-restful-web-services
	@RequestMapping(value="/csrf-token", method=RequestMethod.GET)
	public @ResponseBody String getCsrfToken(HttpServletRequest request) {
	    CsrfToken token = (CsrfToken)request.getAttribute(CsrfToken.class.getName());
	    return token.getToken();
	}
	
	@GetMapping("/apiTest")
	public String apiTest() {
		return "yer a coder harry";
	}
	
	@GetMapping("/allDrinks")
	public List<Drink> getAllDrinks() {
		return drinkRepository.findAll();
	}
	
	@PostMapping("/newDrink")
	public String persistDrink(@Valid @RequestBody Drink drink) {
//		Drink drink = new Drink(null, null);
		drinkRepository.save(drink);
		return "good job man idk";
	}
	
}

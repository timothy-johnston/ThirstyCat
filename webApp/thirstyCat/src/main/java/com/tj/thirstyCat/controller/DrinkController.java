package com.tj.thirstyCat.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tj.thirstyCat.common.AppUtil;
import com.tj.thirstyCat.model.Drink;
import com.tj.thirstyCat.repository.DrinkRepository;

@RestController
public class DrinkController {

	@Autowired
	private DrinkRepository drinkRepository;
	
	//Returns csrf token
	//Is this secure? Will probably need to fix/remove this entirely. TODO: Research
	//Following https://stackoverflow.com/questions/33125598/how-to-handle-csrf-protection-with-spring-restful-web-services
	@GetMapping(value="/csrf-token")
	public @ResponseBody String getCsrfToken(HttpServletRequest request) {
	    CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
	    return token.getToken();
	}
	
	@GetMapping("/allDrinks")
	public List<Drink> getAllDrinks() {
		return drinkRepository.findAll();
	}
	
	/*
	 * Post request format:
	 	{
			"startTime": "2019-06-04T03:57:53.558",
			"endTime": "2019-06-04T03:59:53.558"
		}
	 */
	@PostMapping("/newDrink")
	public Drink persistDrink(@Valid @RequestBody Drink drink) {
		
		try {
			drinkRepository.save(drink);
		} catch (Exception e) {
			AppUtil.logError(e, UUID.randomUUID());
		}
		
		return null;
	}

	public Drink retrieveLastDrink() {
		
		// TODO Auto-generated method stub
		
		return null;
		
	}

	public Drink getDrink(int drinkId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

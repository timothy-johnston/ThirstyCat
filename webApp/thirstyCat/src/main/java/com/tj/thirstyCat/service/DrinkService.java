package com.tj.thirstyCat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tj.thirstyCat.repository.DrinkRepository;

@Service
public class DrinkService {
	
	@Autowired
	private DrinkRepository drinkRepository;

}

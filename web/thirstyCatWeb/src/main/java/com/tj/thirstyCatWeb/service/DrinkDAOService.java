package com.tj.thirstyCatWeb.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.tj.thirstyCatWeb.entity.Drink;

//For transactions with the database. Persisting & retrieving drink information, etc.

@Repository
@Transactional
public class DrinkDAOService {

	@PersistenceContext
	private EntityManager entityManager;
	
	public long insert(Drink drink) {
		
		//Opens transaction
		entityManager.persist(drink);
		
		//Closes transaction
		return drink.getId();
		
	}
	
}

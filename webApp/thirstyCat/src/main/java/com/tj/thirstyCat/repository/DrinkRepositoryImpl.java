package com.tj.thirstyCat.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tj.thirstyCat.model.Drink;
import com.tj.thirstyCat.service.DrinkService;

@Repository
public class DrinkRepositoryImpl implements DrinkRepositoryCustom {

	//https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/select-from-where-statement.html
	//https://dzone.com/articles/add-custom-functionality-to-a-spring-data-reposito
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	DrinkService drinkService;
	
	@Override
	public Optional<Drink> findLastDrink() {
		
		//Get list of drinks sorted by decreasing drink id
		String queryString = "SELECT id FROM Drink ORDER BY id DESC";
		Query query = entityManager.createQuery(queryString);
		List<Long> idList = query.getResultList();
		
		//Use largest id to retrieve latest drink
		return drinkService.getDrinkById(idList.get(0));
		
	}
	
}

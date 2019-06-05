package com.tj.thirstyCat.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tj.thirstyCat.model.Drink;

@Repository
public class DrinkRepositoryImpl implements DrinkRepositoryCustom {

	//https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/select-from-where-statement.html
	//https://dzone.com/articles/add-custom-functionality-to-a-spring-data-reposito
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	DrinkRepository drinkRepository;
	
	@Override
	public Optional<Drink> findLastDrink() {
		
		//Get list of drinks sorted by decreasing drink id
		Query query = entityManager.createQuery("SELECT id FROM drinks ORDER BY id DESC");
		List<Long> idList = query.getResultList();
		
		//Use largest id to retrieve latest drink
		return drinkRepository.findById(idList.get(0));
		
	}
	
}

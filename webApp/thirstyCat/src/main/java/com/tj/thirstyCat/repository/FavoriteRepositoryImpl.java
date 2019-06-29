package com.tj.thirstyCat.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.tj.thirstyCat.model.Favorite;

@Repository
public class FavoriteRepositoryImpl implements FavoriteRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public List<Favorite> getFavoritesByUsername(String username) {
		
		String queryString = "FROM Favorite where username = ?1";
		Query query = entityManager.createQuery(queryString);
		query.setParameter(1, username);
		List<Favorite> favorites = query.getResultList();
		
		return favorites;
		
	}
	
}

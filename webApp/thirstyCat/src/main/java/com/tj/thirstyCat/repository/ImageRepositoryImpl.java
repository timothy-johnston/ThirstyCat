package com.tj.thirstyCat.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.tj.thirstyCat.model.Image;


@Repository
public class ImageRepositoryImpl implements ImageRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public Image getImageByDrinkId(Long drinkId) {
		
		String queryString = "FROM Image where drink_id = ?1";
		Query query = entityManager.createQuery(queryString);
		query.setParameter(1, drinkId);
		List<Image> image = query.getResultList();
		
		return image.get(0);
		
	}
	
}

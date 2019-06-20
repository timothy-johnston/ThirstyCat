package com.tj.thirstyCat.repository;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tj.thirstyCat.model.Image;
import com.tj.thirstyCat.service.ImageService;

@Repository
public class ImageRepositoryImpl implements ImageRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	ImageService imageService;
	
	@Override
	public Optional<byte[]> getImageByDrinkId(Long drinkId) {
		
		String queryString = "SELECT imageByteArray FROM Image where drink_id = ?";
		Query query = entityManager.createQuery(queryString);
		query.setParameter(1, drinkId);
		Optional<byte[]> imageBytes = (Optional<byte[]>) query.getResultList().get(0);
		
		return imageBytes;
		
	}
	
	@Override
	@Transactional
	public byte[] getLastImage() {
		
		String queryString = "SELECT imageByteArray FROM Image ORDER BY id DESC";
		Query query = entityManager.createQuery(queryString);
		byte[] imageBytes = (byte[]) query.setMaxResults(1).getResultList().get(0);
		
		return imageBytes;
		
	}
	
}

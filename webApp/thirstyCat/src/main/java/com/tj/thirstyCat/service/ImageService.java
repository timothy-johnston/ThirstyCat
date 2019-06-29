package com.tj.thirstyCat.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tj.thirstyCat.model.Favorite;
import com.tj.thirstyCat.model.Image;
import com.tj.thirstyCat.repository.FavoriteReposiotry;
import com.tj.thirstyCat.repository.ImageRepository;

@Service
public class ImageService {

	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private FavoriteReposiotry favoriteRepository;
	
	//Add image to database
	public void storeFile(Image image) {
		imageRepository.save(image);
	}

	public Image addImage(Image testImage) {
		return imageRepository.save(testImage);
	}

	public byte[] getLastImage() {
		return imageRepository.getLastImage();
	}

	public List<Image> getAllImages() {
		return imageRepository.findAll();
	}

	public Image getImageById(Long imageId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Transactional
	public Image getImageByDrinkId(Long drinkId) {
		return imageRepository.getImageByDrinkId(drinkId);
	}

	public Favorite favoriteImage(Favorite favorite) {
		return favoriteRepository.save(favorite);
	}
	
	public List<Favorite> getFavoritesByUsername(String username) {
		return favoriteRepository.getFavoritesByUsername(username);
	}
	
}

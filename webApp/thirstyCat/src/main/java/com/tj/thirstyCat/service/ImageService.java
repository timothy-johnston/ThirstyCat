package com.tj.thirstyCat.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tj.thirstyCat.model.Image;
import com.tj.thirstyCat.repository.ImageRepository;

@Service
public class ImageService {

	@Autowired
	private ImageRepository imageRepository;
	
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
	
	public Optional<byte[]> getImageByDrinkId(Long drinkId) {
		return imageRepository.getImageByDrinkId(drinkId);
	}

	public void favoriteImage(Long imageId) {
		// TODO Auto-generated method stub
		
	}
	
}

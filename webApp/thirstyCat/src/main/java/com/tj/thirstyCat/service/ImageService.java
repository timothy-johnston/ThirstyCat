package com.tj.thirstyCat.service;

import java.util.List;

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

	public Long[] addImage(Image testImage) {
		// TODO Auto-generated method stub
		return null;
		
	}

	public Image getLastImage() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Image> getAllImages() {
		// TODO Auto-generated method stub
		return null;
	}

	public Image getImageById(Long imageId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void favoriteImage(Long imageId) {
		// TODO Auto-generated method stub
		
	}
	
}

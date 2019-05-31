package com.tj.thirstyCat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
	
}

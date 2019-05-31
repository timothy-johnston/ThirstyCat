package com.tj.thirstyCat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tj.thirstyCat.repository.ImageRepository;

@Service
public class ImageService {

	@Autowired
	private ImageRepository imageRepository;
	
}

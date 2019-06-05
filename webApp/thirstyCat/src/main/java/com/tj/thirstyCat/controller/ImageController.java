package com.tj.thirstyCat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tj.thirstyCat.model.Image;
import com.tj.thirstyCat.service.ImageService;

@RestController("/api/image")
public class ImageController {

	@Autowired
	ImageService imageService;
	
	@PostMapping("/add")
	@ResponseBody
	public Long[] persistImage(Image image) {
		return imageService.addImage(image);
	}

	@GetMapping("/last")
	@ResponseBody
	public Image retrieveLastImage() {
		return imageService.getLastImage();
	}

	@GetMapping("/all")
	@ResponseBody
	public List<Image> getAllImages() {
		return imageService.getAllImages();
	}

	@GetMapping("/get/{imageId}")
	@ResponseBody
	public Image getImage(@PathVariable Long imageId) {
		return imageService.getImageById(imageId);
	}

	@GetMapping("/favorite/{imageId}")
	@ResponseBody
	public void favoriteImage(@PathVariable Long imageId) {
		imageService.favoriteImage(imageId);
	}

}

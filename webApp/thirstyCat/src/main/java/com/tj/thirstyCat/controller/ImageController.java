package com.tj.thirstyCat.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tj.thirstyCat.model.Image;
import com.tj.thirstyCat.service.ImageService;

@RestController("/api/image")
public class ImageController {

	@Autowired
	ImageService imageService;
	
	@PostMapping(value="/addImage", consumes="multipart/form-data")
	@ResponseBody
	public Image persistImage(@Valid @RequestBody MultipartFile image, String createdBy, Long drinkId) throws IOException {
		System.out.println("In the image upload controller");
		return imageService.addImage(new Image(drinkId, image.getBytes(), createdBy));
	}

	@GetMapping("/lastImage")
	@ResponseBody
	public Image retrieveLastImage() {
		return imageService.getLastImage();
	}

	@GetMapping("/allImages")
	@ResponseBody
	public List<Image> getAllImages() {
		System.out.println("In all images");
		return imageService.getAllImages();
	}

	@GetMapping("/image/{imageId}")
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

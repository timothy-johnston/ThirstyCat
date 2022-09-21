package com.tj.thirstyCat.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tj.thirstyCat.model.Favorite;
import com.tj.thirstyCat.model.Image;
import com.tj.thirstyCat.service.CommonService;
import com.tj.thirstyCat.service.ImageService;

@RestController
@RequestMapping("/api/image")
public class ImageController {

	@Autowired
	ImageService imageService;
	
	@Autowired
	private CommonService commonService;
	
	@PostMapping(value="/addImage", consumes="multipart/form-data")
	@ResponseBody
	public Image persistImage(@Valid @RequestBody MultipartFile image, String createdBy, Long drinkId, HttpServletResponse response) throws IOException {
		response = commonService.setResponseHeaderJWT(response);
		//Persist
		Image uploadedImage = new Image(drinkId, image.getBytes(), createdBy);
		return imageService.addImage(uploadedImage);
	}

	@GetMapping("/lastImage")
	@ResponseBody
	public Image retrieveLastImage(HttpServletResponse response) {
		response = commonService.setResponseHeaderJWT(response);
		return imageService.getLastImage();
	}

	@GetMapping("/allImages")
	@ResponseBody
	public List<Image> getAllImages(HttpServletResponse response) {
		response = commonService.setResponseHeaderJWT(response);
		return imageService.getAllImages();
	}
	
	@GetMapping("/imageByDrink/{drinkId}")
	@ResponseBody
	public Image getImageByDrinkId(@PathVariable Long drinkId, HttpServletResponse response) {
		response = commonService.setResponseHeaderJWT(response);
		return imageService.getImageByDrinkId(drinkId);
	}

	@PostMapping("/favorite")
	@ResponseBody
	public Favorite favoriteImage(@Valid @RequestBody Favorite favorite, HttpServletResponse response) {
		response = commonService.setResponseHeaderJWT(response);
		imageService.favoriteImage(favorite);
		return favorite;
	}
	
	@GetMapping("/favorites/{username}")
	@ResponseBody
	public List<Long> getFavoriteImageIds(@PathVariable String username, HttpServletResponse response) {
		
		response = commonService.setResponseHeaderJWT(response);
		
		//Retrieve favorites for current user, add each id to the list of ids to return
		List<Long> idList = new ArrayList<Long>();
		List<Favorite> favorites = imageService.getFavoritesByUsername(username);
		
		for(Favorite favorite : favorites) {
			idList.add(favorite.getImageId());
		}
		
		return idList;
	}

}

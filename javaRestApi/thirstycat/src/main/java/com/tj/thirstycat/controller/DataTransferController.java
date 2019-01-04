package com.tj.thirstycat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tj.thirstycat.exception.FileStorageException;
import com.tj.thirstycat.payload.UploadImageResponse;
import com.tj.thirstycat.service.FileStorageService;

@RestController
public class DataTransferController {

	private static final Logger logger = LoggerFactory.getLogger(DataTransferController.class);
	
	@Autowired
	private FileStorageService fileStorageService;
	
	//Endpoint for uploading an image
	//piControl script will hit this after the PiCam takes a picture
	@PostMapping("/api/uploadImage")
	public UploadImageResponse uploadFile(@RequestParam("image") MultipartFile file) throws FileStorageException {
		
		String fileName = fileStorageService.storeFile(file);
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(fileName).toUriString();
		
		return new UploadImageResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());

	}
	
	//Endpoint for updating number of drinks taken today
	//piControl script will hit this after Shasta takes a drink
	@GetMapping("/api/addDrink")
	@ResponseBody
	public String addDrink(@RequestParam(name = "drinks", required = true) String drinks) {
		//TODO: Update model
		return "Number of drinks updated.";
	}
	
	//Endpoint for getting number of drinks taken today
	//Users will hit this when checking # of drinks taken today
	@GetMapping("/api/drinkstoday")
	@ResponseBody
	public String getDrinksToday() {
		//TODO: Implement model
		return null;
	}
	
}

package com.tj.thirstycat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tj.thirstycat.exception.FileStorageException;
import com.tj.thirstycat.payload.UploadImageResponse;
import com.tj.thirstycat.service.FileStorageService;

@RestController
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	private FileStorageService fileStorageService;
	
	@PostMapping("/uploadImage")
	public UploadImageResponse uploadFile(@RequestParam("image") MultipartFile file) throws FileStorageException {
		
		String fileName = fileStorageService.storeFile(file);
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(fileName).toUriString();
		
		return new UploadImageResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());

	}
	
}

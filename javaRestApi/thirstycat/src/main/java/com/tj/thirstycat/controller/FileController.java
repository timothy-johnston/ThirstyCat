package com.tj.thirstycat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tj.thirstycat.service.FileStorageService;

@RestController
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	private FileStorageService fileStorageService;
	
	@PostMapping("/uploadImage")
	public UploadImageResponse uploadFile(@RequestParam("file") MultipartFile file) {
		
		String fileName = fileStorageService.storeFile(file);
		
	}
	
}

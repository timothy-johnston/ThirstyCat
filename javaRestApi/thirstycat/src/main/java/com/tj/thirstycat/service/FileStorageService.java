package com.tj.thirstycat.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.tj.thirstycat.exception.FileStorageException;
import com.tj.thirstycat.exception.MyFileNotFoundException;
import com.tj.thirstycat.property.FileStorageProperties;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;
	
	@Autowired
	public FileStorageService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
		
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception e) {
			try {															//Nested try/catch.. ? Maybe refactor
				throw new FileStorageException("Could not create directory to store uploaded files.", e);
			} catch (FileStorageException nestedException) {
				nestedException.printStackTrace();
			}
		}
	
	}
	
	public String storeFile(MultipartFile file) throws FileStorageException {
		
		//Normalize the file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		try {
			if(fileName.contains("..")) {
				throw new FileStorageException("File name contains invalid path sequence. File name: " + fileName);
			}
			
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			return fileName;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public Resource loadfileAsResource(String fileName) throws MyFileNotFoundException {
		
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException("File not found: " + fileName);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
}

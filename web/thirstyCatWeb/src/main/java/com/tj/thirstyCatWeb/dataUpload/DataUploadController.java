package com.tj.thirstyCatWeb.dataUpload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class DataUploadController {

	@GetMapping("/api/test")
	public String test() {
		return "test successful";
	}
	
	//Endpoint to upload image
	//Request must have header:
		//Content-Type : multipart/form-data
	//In body, the file key : value must be "file" : <filename>
	@PostMapping("/api/imageupload")
	public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "Upload failed. No file selected. " + new Date().toString();
		}
		
		//Save file
		try {
			//Get file
			byte[] fileBytes = file.getBytes();
			
			//Write to disk		[TODO: Refactor to persist in database]
			Path path = Paths.get(file.getOriginalFilename());
			Files.write(path, fileBytes);
			
		} catch (IOException e) {
			e.printStackTrace();
			return "Upload failed. No file selected. " + new Date().toString() + "/n" 
					+ e.getMessage();
		}
		
		return "Image uploaded successfully. " + new Date().toString();
		
	}
	
//	@PostMapping("/api/addnewdrink")
//	public String addNewDrink(
	
}

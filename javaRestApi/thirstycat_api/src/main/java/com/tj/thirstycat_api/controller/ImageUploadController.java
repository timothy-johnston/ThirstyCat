package com.tj.thirstycat_api.controller;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ImageUploadController {

	//Upload directory
	private String upload_dir = "/";
	
	//Rough health check endpoint
	@GetMapping("/api/status")
	@ResponseBody
	public String getStatus() {
		return "I live!";
	}
	
	@PostMapping("/api/uploadImage")
	public String uploadImage(@RequestParam("file") MultipartFile file, RedirectAttributes redicrectAttributes) {
		
		try {
			//Read file to byte array
			byte[] fileBytes = file.getBytes();
			//TODO: Continue following tutorial at http://www.mkyong.com/spring-boot/spring-boot-file-upload-example/
			
			if (file.isEmpty()) {
				redirectAttributes.addFlashAttribute("message", "Please select a file to upload");				//Might not need this
				return "rediret:api/uploadStatus";
			}
			
			Path path = Paths.get(upload_dir + file.getOriginalFilename());
			
			Files.write(path,  fileBytes);
			
			redirectAttributes.addFlashAttribute("message", "You successfully uploaded '" + file.getOriginalFilename() + "'");
			
		} catch (IOEception e) {
			e.printStackTrace();
		}
			
		return "redirect:/uploadStatus";
		
	}
	
}

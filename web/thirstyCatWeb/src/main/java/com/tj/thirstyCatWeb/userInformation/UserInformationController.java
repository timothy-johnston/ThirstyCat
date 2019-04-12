package com.tj.thirstyCatWeb.userInformation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


//Starting point / template for users filling out form, uploading images, etc


@Controller
public class UserInformationController {

/*
	
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
			return "redirect:api/uploadStatus";
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
			return "it didn't work";
		}
		
		
		return "redirect:/uploadStatus";
		
		
	}
	
	
	
	@GetMapping("/api/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }
    
*/
	
}

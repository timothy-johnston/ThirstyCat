package com.tj.thirstycat_api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	//Comment for test commit
	
}

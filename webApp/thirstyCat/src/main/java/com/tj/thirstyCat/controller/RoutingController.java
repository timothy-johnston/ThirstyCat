package com.tj.thirstyCat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RoutingController {
	
	@RequestMapping("/")
	String home() {
		return "home";
	}
	
	@RequestMapping("/login")
	String login(Model model) {
		return "login";
	}
	
	@GetMapping("/user")
	public String userIndex() {
		return "user/home";
	}
	
	@GetMapping("/favoritepics")
	public String favoritePics() {
		return "favoritePics";
	}

}

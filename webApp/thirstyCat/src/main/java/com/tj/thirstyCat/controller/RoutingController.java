package com.tj.thirstyCat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RoutingController {
	
	@RequestMapping("/")
	String home() {
		return "home";
	}
	
	@RequestMapping("/login")
	String login() {
		return "login";
	}

}

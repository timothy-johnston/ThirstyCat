package com.tj.thirstyCat.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RoutingController {
	
	@Value("${JWT_CRED_1}") 
	private String jwtAdminUsername;
	
	@Value("${JWT_CRED_2}") 
	private String jwtAdminPassword;
	
	@RequestMapping("/")
	public ModelAndView home() {

		//Credentials are needed for client side api calls, so pass them along in the model
		ModelAndView mav = new ModelAndView("home");
		mav.addObject("username", jwtAdminUsername);
		mav.addObject("password", jwtAdminPassword);
		
		return mav;
		
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
	public ModelAndView favoritePics() {
		
		//Credentials are needed for client side api calls, so pass them along in the model
		ModelAndView mav = new ModelAndView("favoritePics");
		mav.addObject("username", jwtAdminUsername);
		mav.addObject("password", jwtAdminPassword);
		
		return mav;
		
	}

}

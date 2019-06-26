package com.tj.thirstyCat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tj.thirstyCat.model.UserRegistration;
import com.tj.thirstyCat.service.UserService;

@Controller
@RequestMapping("/registration")
public class UserController {

	@Autowired
	private UserService userService;
	
	@ModelAttribute("user")
	public UserRegistration userRegistration() {
		return new UserRegistration();
	}
	
	@GetMapping
	public String loadRegistrationPage(Model model) {
		return "registration";
	}
	
	
	
}

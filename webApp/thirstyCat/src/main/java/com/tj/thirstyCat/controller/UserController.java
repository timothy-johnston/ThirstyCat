package com.tj.thirstyCat.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tj.thirstyCat.model.User;
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
	
	//Todo: Research BindingResult
	@PostMapping
	public String registerUser(@ModelAttribute("user") @Valid UserRegistration userRegistration, BindingResult result) {
		User user = userService.findByUsername(userRegistration.getUsername());
		
		if (user != null) {
			result.rejectValue("username", null, "The username " + userRegistration.getUsername() + " is already taken.");
		}
		
		if (result.hasErrors()) {
			return "registration";
		}
		
		//Save new user
		userService.save(userRegistration);
		
		return "redirect:/registration?sucess";
		
	}
	
}

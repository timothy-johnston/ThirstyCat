package com.tj.thirstyCat.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tj.thirstyCat.model.User;
import com.tj.thirstyCat.model.UserRegistration;
import com.tj.thirstyCat.security.JwtRequest;
import com.tj.thirstyCat.security.JwtResponse;
import com.tj.thirstyCat.security.JwtTokenUtil;
import com.tj.thirstyCat.service.UserService;

@Controller
@RequestMapping("/registration")
public class AuthControllerBasic {

	@Autowired
	private UserService userService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
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
	public String registerUser(@ModelAttribute("user") @Valid UserRegistration userRegistration, BindingResult result, HttpServletRequest request) {
		User user = userService.findByUsername(userRegistration.getUsername());
		
		if (user != null) {
			result.rejectValue("username", null, "The username " + userRegistration.getUsername() + " is already taken.");
		}
		
		if (result.hasErrors()) {
			return "registration";
		}
		
		//Save new user
		userService.save(userRegistration);
		
		//Log in
		try {
	        request.login(userRegistration.getUsername(), userRegistration.getPassword());
	    } catch (ServletException e) {
	        System.out.println("Error while logging in after registration: " + e);
	    }
		
		return "redirect:/home";
		
	}
	
}

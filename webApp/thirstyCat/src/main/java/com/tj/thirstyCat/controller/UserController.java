package com.tj.thirstyCat.controller;

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
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
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
		
		//Should be able to just call the login method here so user doesn't have to do that too.
		
		return "redirect:/registration?success";
		
	}
	
}

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
@RequestMapping("/secure")
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
	
	@GetMapping("/registration")
	public String loadRegistrationPage(Model model) {
		return "registration";
	}
	
	//Todo: Research BindingResult
	@PostMapping("/registration")
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
	
	//User retrieves JWT token from this endpoint
	@PostMapping("/authenticateJWT")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		
		//Calls method using Springs AuthenticationManager. If Authentication fails, an exception will be thrown and JWT will not be generated
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		
		//Get current user
		UserDetails user = userService.loadUserByUsername(authenticationRequest.getUsername());
		
		//Generate token for current user
		String token = jwtTokenUtil.generateToken(user);
		
		return ResponseEntity.ok(new JwtResponse(token));
		
	}
	
	//TODO: Need to research how Spring AuthenticationManager does this authentication
	private void authenticate(String username, String password) throws Exception {
		
		//TODO: Check user role. If role != ADMIN, throw exception
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
		
	}
	
}

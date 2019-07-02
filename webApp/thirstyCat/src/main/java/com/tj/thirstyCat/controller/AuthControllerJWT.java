package com.tj.thirstyCat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tj.thirstyCat.security.JwtRequest;
import com.tj.thirstyCat.security.JwtResponse;
import com.tj.thirstyCat.security.JwtTokenUtil;
import com.tj.thirstyCat.service.UserService;

@Controller
@RequestMapping("/api")
public class AuthControllerJWT {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
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
		
		//Check that user is Raspberry Pi or frontend
		if (username.equalsIgnoreCase("TC_ADMIN_A") || username.equalsIgnoreCase("TC_ADMIN_B")) {
			try {
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			} catch (DisabledException e) {
				throw new Exception("USER_DISABLED", e);
			} catch (BadCredentialsException e) {
				throw new Exception("INVALID_CREDENTIALS", e);
			}
		} else {
			throw new Exception("INVALID_PERMISSIONS");
		}
		
	}
	
	
}

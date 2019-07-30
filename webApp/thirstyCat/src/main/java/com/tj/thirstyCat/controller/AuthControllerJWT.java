package com.tj.thirstyCat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tj.thirstyCat.security.JwtRequest;
import com.tj.thirstyCat.security.JwtTokenUtil;


@Controller
@RequestMapping("/api")
public class AuthControllerJWT {
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	//User retrieves JWT token from this endpoint
	@PostMapping("/authenticateJWT")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		
		return ResponseEntity.ok(jwtTokenUtil.createAuthenticationToken(authenticationRequest));
		
	}
	

	
	
}

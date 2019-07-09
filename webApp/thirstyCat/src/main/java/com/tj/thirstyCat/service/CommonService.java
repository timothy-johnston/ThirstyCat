package com.tj.thirstyCat.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tj.thirstyCat.security.JwtResponse;
import com.tj.thirstyCat.security.JwtTokenUtil;

@Service
public class CommonService {
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	public HttpServletResponse setResponseHeaderJWT(HttpServletResponse response) {
		
		JwtResponse jwt;
		try {
			jwt = jwtTokenUtil.createAdminJWT();
			response.addHeader("auth", jwt.getToken());
		} catch (Exception e) {
			//TODO: Replace debug console output with logging
			System.out.println("Error retrieving jwt: " + e.getMessage());
			response.addHeader("auth", "Error retrieving jwt.");
		}
		
		return response;
	}
	
}

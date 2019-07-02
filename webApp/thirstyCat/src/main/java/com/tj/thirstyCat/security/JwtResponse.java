package com.tj.thirstyCat.security;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -2134559611925917133L;
	private final String jwtToken;
	
	public JwtResponse(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	
	public String getToken() {
		return this.jwtToken;
	}
	
}

package com.tj.thirstyCat.model;

import javax.validation.constraints.NotEmpty;

public class UserRegistration {

	@NotEmpty
	private String username;
	
	@NotEmpty
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}

package com.tj.thirstyCat.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.tj.thirstyCat.model.User;
import com.tj.thirstyCat.model.UserRegistration;

public interface UserService extends UserDetailsService {

	User findByUsername(String username);
	
	User save(UserRegistration registration);
	
}

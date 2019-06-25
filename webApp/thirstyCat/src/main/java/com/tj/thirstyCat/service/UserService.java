package com.tj.thirstyCat.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tj.thirstyCat.model.User;
import com.tj.thirstyCat.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User addUser(@Valid User user) {
		return userRepository.save(user);
	}

	public void deleteUserById(Long userId) {
		userRepository.deleteById(userId);
	}

}

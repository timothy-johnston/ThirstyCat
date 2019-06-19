package com.tj.thirstyCat.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tj.thirstyCat.model.User;
import com.tj.thirstyCat.service.UserService;

@RestController("/api/image")
//Need to add @ResourceMapping annotation here, put url here instead of in @RestController?
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/addUser")
	@ResponseBody
	public User persistUser(@Valid @RequestBody User user) {
		return userService.addUser(user);
	}
	
	@DeleteMapping("/deleteUser/{userId}")
	@ResponseBody
	public void deleteUser(@PathVariable Long userId) {
		userService.deleteUserById(userId);
	}

}

package com.tj.thirstyCat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tj.thirstyCat.security.JwtResponse;
import com.tj.thirstyCat.security.JwtTokenUtil;

@Controller
public class RoutingController {
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@RequestMapping(value= {"", "/", "/home"})
	public ModelAndView home() {

		//Credentials are needed for client side api calls, so pass them along in the model
		ModelAndView mav = new ModelAndView("home");

		//Create admin token and pass to client
		JwtResponse jwt;
		try {
			jwt = jwtTokenUtil.createAdminJWT();
			mav.addObject("jwt", jwt.getToken());
		} catch (Exception e) {
			// TODO Replace console debug output with logging
			System.out.println("Error retrieving jwt (RoutingController) : " + e.getMessage());
		}
		
		return mav;
		
	}
	
	@RequestMapping("/login")
	String login(Model model) {
		return "login";
	}
	
	@GetMapping("/user")
	public String userIndex() {
		return "user/home";
	}
	
	@GetMapping("/favoritepics")
	public ModelAndView favoritePics() {
		
		//Credentials are needed for client side api calls, so pass them along in the model
		ModelAndView mav = new ModelAndView("favoritePics");
		
		return mav;
		
	}

}

package com.tj.thirstyCat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tj.thirstyCat.security.JwtRequest;
import com.tj.thirstyCat.security.JwtResponse;
import com.tj.thirstyCat.security.JwtTokenUtil;

@Controller
public class RoutingController {
	
	@Value("${JWT_CRED_1}") 
	private String jwtAdminUsername;
	
	@Value("${JWT_CRED_2}") 
	private String jwtAdminPassword;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@RequestMapping(value= {"", "/", "/home"})
	public ModelAndView home() {

		//Credentials are needed for client side api calls, so pass them along in the model
		ModelAndView mav = new ModelAndView("home");

		//Create jwt token, pass it to frontend		
		try {
			JwtResponse jwt = jwtTokenUtil.createAuthenticationToken(new JwtRequest(jwtAdminUsername, jwtAdminPassword));
			mav.addObject("jwt", jwt.getToken());
		} catch (Exception e) {
			// TODO Replace debug console output with logging
			System.out.println("Error generating token: " + e.getMessage());
		}
		
		return mav;
		
	}
	
//	@RequestMapping(value= {"", "/", "/home"})
//	String homeFromOther() {
//		return "home";
//	}
	
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
		mav.addObject("username", jwtAdminUsername);
		mav.addObject("password", jwtAdminPassword);
		
		return mav;
		
	}

}

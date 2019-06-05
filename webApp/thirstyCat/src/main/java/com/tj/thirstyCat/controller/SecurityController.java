package com.tj.thirstyCat.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

	//Returns csrf token
	//Is this secure? Will probably need to fix/remove this entirely. TODO: Research
	//Following https://stackoverflow.com/questions/33125598/how-to-handle-csrf-protection-with-spring-restful-web-services
	@GetMapping(value="/csrf-token")
	public @ResponseBody String getCsrfToken(HttpServletRequest request) {
	    CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
	    return token.getToken();
	}
	
}

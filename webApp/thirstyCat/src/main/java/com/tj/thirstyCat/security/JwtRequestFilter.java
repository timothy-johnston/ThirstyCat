package com.tj.thirstyCat.security;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tj.thirstyCat.service.TokenService;
import com.tj.thirstyCat.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private UserService userService;
	
	@Autowired 
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private TokenService tokenService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		
		String requestTokenHeader = request.getHeader("Authorization");
		Enumeration<String> names = request.getHeaderNames();
		
		System.out.println(names.nextElement());
		System.out.println(names.nextElement());
		System.out.println(names.nextElement());
		System.out.println(names.nextElement());
		System.out.println(names.nextElement());
		System.out.println(names.nextElement());
		System.out.println(names.nextElement());
		System.out.println(names.nextElement());
		System.out.println(names.nextElement());
		System.out.println(names.nextElement());
		System.out.println(names.nextElement());
		System.out.println(names.nextElement());
		System.out.println(names.nextElement());
		System.out.println(names.nextElement());
		System.out.println(names.nextElement());
		System.out.println(names.nextElement());
		System.out.println(names.nextElement());
		System.out.println(request.getHeader("Host"));
		System.out.println(request.getHeader("Authorization"));
		System.out.println(request.getHeader("authorization"));
		
		
		
		String username = null;
		String jwtToken = null;
		String tokenStart = "Bearer ";
		
		//Get token, remove "Bearer" from front of it
		if (requestTokenHeader != null && requestTokenHeader.startsWith(tokenStart)) {
			jwtToken = requestTokenHeader.substring(tokenStart.length());
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				//TODO: Implement logging instead of printing to console
				System.out.println("Unable to retrieve JWT Token.");
			} catch (ExpiredJwtException e) {
				//TODO: Implement logging instead of printing to console
				System.out.println("JWT Token has expired.");
			}
		} else {
			//TODO: Implement logging instead of printing to console
			System.out.println("JWT Token does not begin with Bearer.");
		}
		
		//Validate token & ensure it has not been blacklisted
		boolean tokenIsBlacklisted = tokenService.isTokenBlacklisted(jwtToken);
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null && !tokenIsBlacklisted) {
			
			//Get user details
			UserDetails userDetails = userService.loadUserByUsername(username);
			
			//Set authentication
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			
			//Add token to blacklist & purge expired tokens from blacklist
			tokenService.updateTokenBlacklist(jwtToken);
		}
		
		chain.doFilter(request, response);
		
	}
	
}

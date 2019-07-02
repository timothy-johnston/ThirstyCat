package com.tj.thirstyCat.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

	//Note: Following https://www.javainuse.com/spring/boot-jwt
	//This class is mostly copy/paste from the tutorial, but adding my own comments to show understanding
	
	//TODO: Why this value? Research, adapt/change to fit this project if needed
	private static final long serianVersionUID = -2550185165626007488L;
	
	//Token lifetime in milliseconds
	private static final long JWT_TOKEN_VALIDITY = 10 * 60 * 1000;
	
	//Will eventually sign the generated token using this secret
	@Value("${jwt.secret}")
	private String secret;
	
	//Calls method to decode & retrieve username from token
	//Using :: operator in an argument to specify what function of the Claims class we want used
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	//Calls method to decode & retrieve expiry time from token
	//Using :: operator in an argument to specify what function of the Claims class we want used
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	//Called from either getUsername.. or getExpirationDate..
	//Claims are the individual components of the payload, or what makes up the token
	//Using a functional interface. It applies the passed in function (getUsername.. or getExpirationDate.. )
	//	to the Claims object after it is returned from getAllClaimsFromToken
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	//Parses the JWT into its individual Claims
	private Claims getAllClaimsFromToken(String token) {
		System.out.println(token);
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	//Compare token expiration time to current time
	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	//Calls the tokenGeneration function, passing the logged in user's username
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return tokenGeneration(claims, userDetails.getUsername());
	}
	
	//Creates the token. Sets the subject (in this case username) based on passed in username,
	//	the issue date = current date, the expiration date as current date + token duration,
	//	and signs the key with a cryptographic algorithm + our secret key
	private String tokenGeneration(Map<String, Object> claims, String subject) {
		
		Date currentDate = new Date(System.currentTimeMillis());
		Date expiryDate = new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY);
		
		return Jwts.builder().setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(currentDate)
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
	//Confirm validity of token
	//Token is valid if token username = current username and token has not expired or been blacklisted
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		Boolean isValid = (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
		return isValid;
	}
	
}

package com.tj.thirstyCat.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.tj.thirstyCat.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

	//Note: Following https://www.javainuse.com/spring/boot-jwt
	//This class is mostly copy/paste from the tutorial, but adding my own comments to show understanding
	
	//TODO: Why this value? Research, adapt/change to fit this project if needed
	private static final long serialVersionUID = -2550185165626007488L;
	
	//Token lifetime in milliseconds
	private static final long JWT_TOKEN_VALIDITY = 10 * 60 * 1000;
	
	@Value("${JWT_CRED_1}") 
	private String jwtAdminUsername;
	
	@Value("${JWT_CRED_2}") 
	private String jwtAdminPassword;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
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
	
	public JwtResponse createAdminJWT() throws Exception {
		
		return createAuthenticationToken(new JwtRequest(jwtAdminUsername, jwtAdminPassword));
		
	}
	
	//Accepts username and password in jwtRequest and returns a new valid JWT
	public JwtResponse createAuthenticationToken(JwtRequest jwtRequest) throws Exception {
		
		//Calls method using Springs AuthenticationManager. If Authentication fails, an exception will be thrown and JWT will not be generated
		authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
		
		//Get current user
		UserDetails user = userService.loadUserByUsername(jwtRequest.getUsername());
		
		//Generate token for current user
		String token = generateToken(user);
		
		return new JwtResponse(token);

	}
	
	//TODO: Need to research how Spring AuthenticationManager does this authentication
	private void authenticate(String username, String password) throws Exception {
		
		//Check that user is Raspberry Pi or frontend
		if (username.equalsIgnoreCase("TC_ADMIN_A") || username.equalsIgnoreCase("TC_ADMIN_B")) {
			try {
				
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
				
				Authentication authentication = authenticationManager.authenticate(token);
				
			} catch (DisabledException e) {
				throw new Exception("USER_DISABLED", e);
			} catch (BadCredentialsException e) {
				throw new Exception("INVALID_CREDENTIALS", e);
			} catch (AuthenticationException e) {
				//TODO: Implement logging rather than printing error to console (security vulnerability)
				System.out.println("Ooops. Authentication exception: " + e.getMessage());
				throw new Exception("AUTHENTICATION_EXCEPTION", e);
			} catch (Exception e) {
				//TODO: Implement logging rather than printing error to console (security vulnerability)
				System.out.println("Something bad happened... : " + e.getMessage());
				throw new Exception("A_BAD_THING_EXCEPTION", e);
			}
		} else {			
			throw new Exception("INVALID_PERMISSIONS");
		}
		
	}
	
}

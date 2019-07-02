package com.tj.thirstyCat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tj.thirstyCat.model.Token;
import com.tj.thirstyCat.repository.TokenRepository;
import com.tj.thirstyCat.security.JwtTokenUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Service
public class TokenService {

	@Autowired
	private TokenRepository tokenRepository;
	
	@Autowired 
	private JwtTokenUtil tokenUtil;
	
	//The token database table acts as a blacklist.
	//A token is used one time and then added to the blacklist
	//The below method updates the blacklist
	//It will remove all expired tokens from the blacklist
	//and add the incoming token to the blacklist so it can not be used again
	public void updateTokenBlacklist(String token) {
		purgeBlacklistOfExpiredTokens();
		addTokenToBlacklist(token);
	}

	private void addTokenToBlacklist(String token) {
		tokenRepository.save(new Token(token));
	}

	private void purgeBlacklistOfExpiredTokens() {
		
		List<Long> tokenIdsToPurge = new ArrayList<Long>();
		
		//Get all blacklisted tokens
		List<Token> blacklistedTokens = tokenRepository.findAll();
		
		//Loop over all tokens in the blacklist
		for (Token blacklistedToken : blacklistedTokens) {
			
			//Try to parse their expiry time. If expired, add id to purge list
			//Some expired tokens are throwing ExpiredJwtException - catch this and add id to purge list
			try {
				if (tokenUtil.isTokenExpired(blacklistedToken.getToken())) {
					tokenIdsToPurge.add(blacklistedToken.getId());
				}
			} catch (ExpiredJwtException e) {
				tokenIdsToPurge.add(blacklistedToken.getId());
			}
			
		}
		
		tokenRepository.deleteByIdIn(tokenIdsToPurge);
		
	}
	
	//Check if current token has been blacklisted
	public boolean isTokenBlacklisted(String token) {
		
		boolean tokenIsBlacklisted = false;
		List<Token> blacklistedTokens = tokenRepository.findAll();
		
		for (Token blacklistedToken : blacklistedTokens) {
			if (token != null && token.equals(blacklistedToken.getToken())) {
				tokenIsBlacklisted = true;
				break;
			}
		}
		
		return tokenIsBlacklisted;
		
	}
	
}

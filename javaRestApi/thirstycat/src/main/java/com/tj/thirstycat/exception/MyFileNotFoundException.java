package com.tj.thirstycat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)			//Will respond with 404
public class MyFileNotFoundException extends RuntimeException {

	public MyFileNotFoundException(String message) {
		super(message);
	}

}

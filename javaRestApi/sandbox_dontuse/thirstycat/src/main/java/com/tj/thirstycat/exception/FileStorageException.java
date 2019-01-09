package com.tj.thirstycat.exception;

public class FileStorageException extends RuntimeException {

	public FileStorageException(String message, Exception cause) {
		super(message, cause);
	}

	public FileStorageException(String message) {
		super(message);
	}

}

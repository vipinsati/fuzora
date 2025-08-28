package com.fuzora.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataHandlerException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DataHandlerException() {
		super();
	}

	public DataHandlerException(String message) {
		log.error(message);
	}	
}
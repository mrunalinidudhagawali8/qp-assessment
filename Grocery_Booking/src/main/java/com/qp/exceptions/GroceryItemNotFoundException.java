package com.qp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class GroceryItemNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7817087102492937106L;

	public GroceryItemNotFoundException(String message) {
		super(message);
	}
}

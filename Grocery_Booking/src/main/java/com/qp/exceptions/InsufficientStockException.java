package com.qp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.EXPECTATION_FAILED)
public class InsufficientStockException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InsufficientStockException(String message) {
        super(message);
    }
}

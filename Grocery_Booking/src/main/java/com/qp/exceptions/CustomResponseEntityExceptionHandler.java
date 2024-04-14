package com.qp.exceptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.qp.model.error.ErrorResponse;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * INFO: ex.getMessage() will contain complete error message along with the
	 * field error(error msg defined by the validation parameter on the model class)
	 */
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> generalException(Exception ex, WebRequest request) throws Exception {
		ErrorResponse errorResp = new ErrorResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity(errorResp, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(GroceryItemNotFoundException.class)
	public final ResponseEntity<Object> handleuserNotFoundException(Exception ex, WebRequest request) throws Exception {
		ErrorResponse errorResp = new ErrorResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity(errorResp, HttpStatus.EXPECTATION_FAILED);
	}

	@ExceptionHandler(InsufficientStockException.class)
	public final ResponseEntity<Object> handleInsufficientStockException(Exception ex, WebRequest request)
			throws Exception {
		ErrorResponse errorResp = new ErrorResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity(errorResp, HttpStatus.EXPECTATION_FAILED);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = new ArrayList<String>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		ErrorResponse error = new ErrorResponse(LocalDateTime.now(), ex.getLocalizedMessage(), errors.toString());
		return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
	}

}

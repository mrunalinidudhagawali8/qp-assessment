package com.qp.model.error;

import java.time.LocalDateTime;

public class ErrorResponse {

	public LocalDateTime timestamp;
	public String error_msg;
	public String details;

	public ErrorResponse(LocalDateTime timestamp, String error_msg, String error_details) {
		super();
		this.timestamp = timestamp;
		this.error_msg = error_msg;
		this.details = error_details;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}

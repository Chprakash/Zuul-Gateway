package com.chandra.prakash.security;

public class ValidationResponse {

	private boolean success;
	private String message;
	
	
	
	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}

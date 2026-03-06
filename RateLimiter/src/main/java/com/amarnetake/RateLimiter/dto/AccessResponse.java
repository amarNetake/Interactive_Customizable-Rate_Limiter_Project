package com.amarnetake.RateLimiter.dto;
public class AccessResponse {

    private boolean allowed;
    private String message;

    public AccessResponse(boolean allowed, String message) {
        this.allowed = allowed;
        this.message = message;
    }

	public boolean isAllowed() {
		return allowed;
	}

	public void setAllowed(boolean allowed) {
		this.allowed = allowed;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
    
}
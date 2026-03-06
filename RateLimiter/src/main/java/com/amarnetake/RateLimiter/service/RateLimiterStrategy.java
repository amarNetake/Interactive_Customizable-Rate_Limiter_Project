package com.amarnetake.RateLimiter.service;

public interface RateLimiterStrategy {
	
	boolean allowRequest(String userId);
}

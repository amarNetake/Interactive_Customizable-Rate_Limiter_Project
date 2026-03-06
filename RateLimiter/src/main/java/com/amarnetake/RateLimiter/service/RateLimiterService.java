package com.amarnetake.RateLimiter.service;


import java.util.List;

import com.amarnetake.RateLimiter.model.RateLimitConfig;
import com.amarnetake.RateLimiter.model.ViolationLog;


public interface RateLimiterService {

    void createRateLimit(RateLimitConfig config);

    boolean checkAccess(String userId);
    
    List<ViolationLog> getViolations(String userId);
}
package com.amarnetake.RateLimiter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amarnetake.RateLimiter.dto.AccessResponse;
import com.amarnetake.RateLimiter.dto.CreateRateLimitRequest;
import com.amarnetake.RateLimiter.model.RateLimitConfig;
import com.amarnetake.RateLimiter.model.RequestLog;
import com.amarnetake.RateLimiter.repository.RequestLogRepository;
import com.amarnetake.RateLimiter.service.RateLimiterService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/rate-limit")
public class RateLimiterController {

    private final RateLimiterService service;
    
    private final RequestLogRepository requestLogRepo;
    
    @Autowired
    public RateLimiterController(RateLimiterService service, RequestLogRepository requestLogRepo) {
        this.service = service;
        this.requestLogRepo = requestLogRepo;
    }

    @PostMapping
    public String createRateLimit(
            @RequestBody CreateRateLimitRequest request) {

        RateLimitConfig config = new RateLimitConfig();
        config.setUserId(request.getUserId());
        config.setAlgorithmType(request.getAlgorithmType());
        config.setLimitValue(request.getLimitValue());
        config.setWindowSizeInMillis(
                request.getWindowSizeInMillis());

        service.createRateLimit(config);

        return "Rate limit created";
    }

    @PostMapping("/access/{userId}")
    public ResponseEntity<AccessResponse> access(@PathVariable String userId) {

        boolean allowed = service.checkAccess(userId);

        if (allowed) {
            return ResponseEntity.ok(new AccessResponse(true, "Allowed"));
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(new AccessResponse(false, "Blocked"));
        }
    }
    
    @GetMapping("/stats/{userId}")
    public List<RequestLog> getStats(@PathVariable String userId) {
    	return requestLogRepo.findTop50ByUserIdOrderByTimestampDesc(userId);
    }
}
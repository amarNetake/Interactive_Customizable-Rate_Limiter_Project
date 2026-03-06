package com.amarnetake.RateLimiter.dto;

import com.amarnetake.RateLimiter.model.AlgorithmType;

import lombok.Data;

@Data
public class CreateRateLimitRequest {

    private String userId;
    private AlgorithmType algorithmType;
    private int limitValue;
    private long windowSizeInMillis;

}
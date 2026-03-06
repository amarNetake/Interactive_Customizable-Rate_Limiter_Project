package com.amarnetake.RateLimiter.service;

import com.amarnetake.RateLimiter.model.AlgorithmType;
import com.amarnetake.RateLimiter.service.impl.FixedWindowRateLimiter;
import com.amarnetake.RateLimiter.service.impl.SlidingWindowRateLimiter;
import com.amarnetake.RateLimiter.service.impl.TokenBucketRateLimiter;

public class RateLimiterFactory {

    public static RateLimiterStrategy createStrategy(
            AlgorithmType type,
            int limit,
            long windowSizeInMillis) {

        switch (type) {
            case FIXED_WINDOW:
                return new FixedWindowRateLimiter(limit, windowSizeInMillis);

            case SLIDING_WINDOW:
                return new SlidingWindowRateLimiter(limit, windowSizeInMillis);

            case TOKEN_BUCKET:
                return new TokenBucketRateLimiter(limit, windowSizeInMillis);

            default:
                throw new IllegalArgumentException("Invalid algorithm type");
        }
    }
}
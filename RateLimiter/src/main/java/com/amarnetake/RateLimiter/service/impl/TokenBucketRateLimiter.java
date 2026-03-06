package com.amarnetake.RateLimiter.service.impl;

import java.util.concurrent.ConcurrentHashMap;

import com.amarnetake.RateLimiter.service.RateLimiterStrategy;

public class TokenBucketRateLimiter implements RateLimiterStrategy {

    private final int capacity;
    private final double refillRatePerSecond;

    private final ConcurrentHashMap<String, Bucket> buckets;

    public TokenBucketRateLimiter(int capacity, long windowSizeInMillis) {
        this.capacity = capacity;

        // refill rate = capacity per window
        this.refillRatePerSecond =
                (double) capacity / (windowSizeInMillis / 1000.0);

        this.buckets = new ConcurrentHashMap<>();
    }

    @Override
    public boolean allowRequest(String userId) {

        long currentTime = System.currentTimeMillis();

        buckets.putIfAbsent(userId,
                new Bucket(capacity, currentTime));

        Bucket bucket = buckets.get(userId);

        synchronized (bucket) {

            refill(bucket, currentTime);

            if (bucket.tokens >= 1) {
                bucket.tokens -= 1;
                return true;
            } else {
                return false;
            }
        }
    }

    private void refill(Bucket bucket, long currentTime) {

        long timeElapsed = currentTime - bucket.lastRefillTime;

        double tokensToAdd =
                (timeElapsed / 1000.0) * refillRatePerSecond;

        bucket.tokens =
                Math.min(capacity, bucket.tokens + tokensToAdd);

        bucket.lastRefillTime = currentTime;
    }

    private static class Bucket {
        double tokens;
        long lastRefillTime;

        public Bucket(double tokens, long lastRefillTime) {
            this.tokens = tokens;
            this.lastRefillTime = lastRefillTime;
        }
    }
}

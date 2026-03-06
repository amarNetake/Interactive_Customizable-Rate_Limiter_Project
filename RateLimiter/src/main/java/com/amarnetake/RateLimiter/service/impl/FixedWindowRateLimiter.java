package com.amarnetake.RateLimiter.service.impl;

import java.util.concurrent.ConcurrentHashMap;

import com.amarnetake.RateLimiter.service.RateLimiterStrategy;

// Time Complexity - O(1)
// Space complexity - O(n users)

public class FixedWindowRateLimiter implements RateLimiterStrategy {

    private final int limit;
    private final long windowSizeInMillis;

    private final ConcurrentHashMap<String, Window> userWindows;

    public FixedWindowRateLimiter(int limit, long windowSizeInMillis) {
        this.limit = limit;
        this.windowSizeInMillis = windowSizeInMillis;
        this.userWindows = new ConcurrentHashMap<>();
    }

    @Override
    public boolean allowRequest(String userId) {

        long currentTime = System.currentTimeMillis();

        userWindows.putIfAbsent(userId, new Window(0, currentTime));

        Window window = userWindows.get(userId);

        synchronized (window) {

            if (currentTime - window.windowStart >= windowSizeInMillis) {
                // Reset window
                window.count = 0;
                window.windowStart = currentTime;
            }

            if (window.count < limit) {
                window.count++;
                return true;
            } else {
                return false;
            }
        }
    }

    private static class Window {
        int count;
        long windowStart;

        public Window(int count, long windowStart) {
            this.count = count;
            this.windowStart = windowStart;
        }
    }
}
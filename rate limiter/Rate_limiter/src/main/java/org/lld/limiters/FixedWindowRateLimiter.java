package org.lld.limiters;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Fixed Window: allow at most `limit` requests in each fixed time window.
 * capacity = max requests per window, rate = window length in seconds.
 */
public class FixedWindowRateLimiter implements RateLimiter {
    private final long limit;
    private final long windowMs;
    private final ConcurrentHashMap<String, Window> windows = new ConcurrentHashMap<>();

    public FixedWindowRateLimiter(long capacity, double rate) {
        this.limit = capacity;
        this.windowMs = (long) (rate * 1000);
    }

    @Override
    public boolean allowRequest(String clientId) {
        Window w = windows.computeIfAbsent(clientId, k -> new Window());
        return w.allow();
    }

    private class Window {
        private long windowStart = System.currentTimeMillis();
        private long count = 0;

        synchronized boolean allow() {
            long now = System.currentTimeMillis();
            if (now - windowStart >= windowMs) {   // new window -> reset
                windowStart = now;
                count = 0;
            }
            if (count < limit) {
                count++;
                return true;
            }
            return false;
        }
    }
}

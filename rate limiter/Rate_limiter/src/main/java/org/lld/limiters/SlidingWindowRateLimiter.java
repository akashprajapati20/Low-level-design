package org.lld.limiters;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Sliding Window Log: keep timestamps of recent requests, drop those older
 * than the window, and allow only if the remaining count is below the limit.
 * Smooths out the burst-at-window-edge problem of Fixed Window.
 * capacity = max requests per window, rate = window length in seconds.
 */
public class SlidingWindowRateLimiter implements RateLimiter {
    private final long limit;
    private final long windowMs;
    private final ConcurrentHashMap<String, Deque<Long>> logs = new ConcurrentHashMap<>();

    public SlidingWindowRateLimiter(long capacity, double rate) {
        this.limit = capacity;
        this.windowMs = (long) (rate * 1000);
    }

    @Override
    public boolean allowRequest(String clientId) {
        Deque<Long> log = logs.computeIfAbsent(clientId, k -> new ArrayDeque<>());
        long now = System.currentTimeMillis();
        synchronized (log) {
            while (!log.isEmpty() && now - log.peekFirst() >= windowMs) {
                log.pollFirst();   // evict timestamps outside the window
            }
            if (log.size() < limit) {
                log.addLast(now);
                return true;
            }
            return false;
        }
    }
}

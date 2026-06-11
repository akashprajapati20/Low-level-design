package org.lld.limiters;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Leaky Bucket: requests fill a bucket that leaks at a constant rate.
 * If the bucket would overflow, the request is rejected. Smooths bursts
 * into a steady outflow.
 * capacity = bucket size, rate = leak rate per second.
 */
public class LeakyBucketRateLimiter implements RateLimiter {
    private final long capacity;
    private final double leakRatePerMs;
    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    public LeakyBucketRateLimiter(long capacity, double leakRatePerSecond) {
        this.capacity = capacity;
        this.leakRatePerMs = leakRatePerSecond / 1000.0;
    }

    @Override
    public boolean allowRequest(String clientId) {
        Bucket bucket = buckets.computeIfAbsent(clientId, k -> new Bucket());
        return bucket.allow();
    }

    private class Bucket {
        private double water = 0;
        private long lastLeakTimestamp = System.currentTimeMillis();

        synchronized boolean allow() {
            leak();
            if (water + 1 <= capacity) {
                water += 1;
                return true;
            }
            return false;
        }

        private void leak() {
            long now = System.currentTimeMillis();
            double leaked = (now - lastLeakTimestamp) * leakRatePerMs;
            if (leaked > 0) {
                water = Math.max(0, water - leaked);
                lastLeakTimestamp = now;
            }
        }
    }
}

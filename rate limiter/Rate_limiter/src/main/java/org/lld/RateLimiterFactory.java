package org.lld;

import org.lld.enums.Algorithm;
import org.lld.limiters.FixedWindowRateLimiter;
import org.lld.limiters.LeakyBucketRateLimiter;
import org.lld.limiters.RateLimiter;
import org.lld.limiters.SlidingWindowRateLimiter;
import org.lld.limiters.TokenBucketRateLimiter;

public class RateLimiterFactory {
    public static RateLimiter create(Algorithm algo, long capacity, double rate) {
        switch (algo) {
            case TOKEN_BUCKET:   return new TokenBucketRateLimiter(capacity, rate);
            case FIXED_WINDOW:   return new FixedWindowRateLimiter(capacity, rate);
            case SLIDING_WINDOW: return new SlidingWindowRateLimiter(capacity, rate);
            case LEAKY_BUCKET:   return new LeakyBucketRateLimiter(capacity, rate);
            default: throw new IllegalArgumentException("Unknown algorithm");
        }
    }
}
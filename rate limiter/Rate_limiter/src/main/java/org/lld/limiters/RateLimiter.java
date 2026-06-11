package org.lld.limiters;

public interface RateLimiter {
    boolean allowRequest(String clientId);
}
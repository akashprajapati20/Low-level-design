package org.lld;

import org.lld.enums.Algorithm;
import org.lld.limiters.RateLimiter;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Each demo allows ~5 requests, then should start rejecting.
        demo(Algorithm.TOKEN_BUCKET,   5, 1);   // capacity 5, refill 1 token/sec
        demo(Algorithm.FIXED_WINDOW,   5, 1);   // 5 requests per 1-sec window
        demo(Algorithm.SLIDING_WINDOW, 5, 1);   // 5 requests per rolling 1 sec
        demo(Algorithm.LEAKY_BUCKET,   5, 1);   // bucket size 5, leak 1/sec
    }

    private static void demo(Algorithm algo, long capacity, double rate)
            throws InterruptedException {
        RateLimiter limiter = RateLimiterFactory.create(algo, capacity, rate);
        String client = "user-1";

        System.out.println("=== " + algo + " ===");
        // Burst of 7 requests against a limit of 5.
        for (int i = 1; i <= 7; i++) {
            boolean allowed = limiter.allowRequest(client);
            System.out.println("  req " + i + " -> " + (allowed ? "ALLOW" : "REJECT"));
        }

        // After waiting, capacity should partially recover.
        Thread.sleep(1100);
        System.out.println("  after 1.1s wait -> "
                + (limiter.allowRequest(client) ? "ALLOW" : "REJECT"));
        System.out.println();
    }
}

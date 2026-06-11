package com.roombooking.concurrency;

import java.util.function.Supplier;

/**
 * Abstraction over keyed mutual exclusion.
 *
 * <p>Callback style is deliberate: the lock is always released in a finally
 * block inside the implementation, so a caller can never leak a lock by
 * forgetting to unlock. Swapping {@code InMemoryLockManager} for a
 * {@code RedisLockManager} (single instance -> horizontally scaled) requires no
 * change in any service, because services depend only on this interface.
 */
public interface LockManager {

    <T> T executeWithLock(String key, Supplier<T> action);

    /** Convenience overload for actions that produce no value. */
    default void executeWithLock(String key, Runnable action) {
        executeWithLock(key, () -> {
            action.run();
            return null;
        });
    }
}

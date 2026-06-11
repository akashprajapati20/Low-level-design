package com.roombooking.concurrency;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * Single-JVM lock manager using striped per-key {@link ReentrantLock}s.
 *
 * <p>Locking on the room id (not a global lock) is what lets different rooms be
 * booked in parallel while two attempts on the same room serialize.
 * {@code computeIfAbsent} atomically gets-or-creates the lock so racing threads
 * on a brand-new key still share a single lock.
 *
 * <p>Caveat: per-JVM locks do not coordinate across multiple app instances. For
 * a horizontally scaled deployment, back this with a distributed lock (Redis)
 * and/or enforce the no-overlap invariant with a database constraint.
 */
public class InMemoryLockManager implements LockManager {

    private final ConcurrentMap<String, ReentrantLock> locks = new ConcurrentHashMap<>();

    @Override
    public <T> T executeWithLock(String key, Supplier<T> action) {
        ReentrantLock lock = locks.computeIfAbsent(key, k -> new ReentrantLock());
        lock.lock();
        try {
            return action.get();
        } finally {
            lock.unlock();
        }
    }
}

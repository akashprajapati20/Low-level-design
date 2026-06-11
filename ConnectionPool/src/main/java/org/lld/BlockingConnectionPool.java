package org.lld;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class BlockingConnectionPool {
    private final Deque<Integer> free = new ArrayDeque<>();
    private final ReentrantLock lock = new ReentrantLock(true); // fair => FIFO
    private final Condition available = lock.newCondition();

    public BlockingConnectionPool(int capacity) {
        for (int i = 0; i < capacity; i++) free.offerLast(i);
    }

    public int acquire() throws InterruptedException {
        lock.lock();
        try {
            while (free.isEmpty()) available.await();   // while, not if — guard spurious wakeups
            return free.pollFirst();
        } finally { lock.unlock(); }
    }

    public int acquire(long timeout, TimeUnit unit) throws InterruptedException {
        long nanos = unit.toNanos(timeout);
        lock.lock();
        try {
            while (free.isEmpty()) {
                if (nanos <= 0) return -1;              // timed out
                nanos = available.awaitNanos(nanos);
            }
            return free.pollFirst();
        } finally { lock.unlock(); }
    }

    public void release(int connectionId) {
        lock.lock();
        try {
            free.offerLast(connectionId);
            available.signal();                         // wake the longest-waiting thread
        } finally { lock.unlock(); }
    }
}
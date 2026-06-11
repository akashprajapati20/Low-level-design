package com.roombooking.concurrency;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class LockType2 {
    private final Map<String, ReentrantLock> locktMap=new ConcurrentHashMap<>();

    public ReentrantLock getLock(String roomId){
        return locktMap.computeIfAbsent(roomId,l->new ReentrantLock());
    }
}

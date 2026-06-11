package com.roombooking.id;

/** Abstraction over id creation; injectable so ids can be made deterministic in tests. */
public interface IdGenerator {
    String nextId();
}

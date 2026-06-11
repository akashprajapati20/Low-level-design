package org.lld.request;

/**
 * Abstraction for "someone wants the elevator".
 * Splits into HallRequest (external) and CarRequest (internal).
 */
public interface Request {
    int getFloor();
}

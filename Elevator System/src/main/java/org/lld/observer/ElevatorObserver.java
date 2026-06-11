package org.lld.observer;

import org.lld.models.Elevator;

/**
 * Observer pattern: anything that wants to react to an elevator's
 * floor/direction changes (displays, loggers, metrics sinks).
 */
public interface ElevatorObserver {
    void update(Elevator elevator);
}

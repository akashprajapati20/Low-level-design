package org.lld.strategy.scheduling;

import org.lld.models.Elevator;

/**
 * Strategy: given a single elevator's pending stops and current position,
 * decide which stop it should head to next. Lives in each elevator.
 */
public interface SchedulingStrategy {
    /**
     * @return the next floor to target, or {@code null} if there is nothing to serve.
     */
    Integer nextStop(Elevator elevator);
}

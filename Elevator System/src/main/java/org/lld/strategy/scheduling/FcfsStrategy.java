package org.lld.strategy.scheduling;

import org.lld.models.Elevator;

import java.util.Iterator;

/**
 * Naive first-come-first-served: serve stops in the order they were requested.
 * Relies on the elevator storing stops in insertion order.
 */
public class FcfsStrategy implements SchedulingStrategy {

    @Override
    public Integer nextStop(Elevator elevator) {
        Iterator<Integer> it = elevator.getStops().iterator();
        return it.hasNext() ? it.next() : null;
    }
}

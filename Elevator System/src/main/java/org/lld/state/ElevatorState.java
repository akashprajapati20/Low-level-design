package org.lld.state;

import org.lld.common.Direction;
import org.lld.models.Elevator;

/**
 * State pattern: each state knows how the elevator behaves on the next tick
 * and which state to transition into, replacing a sprawling switch(state).
 */
public interface ElevatorState {

    /** Advance the elevator by one tick, mutating it and/or transitioning state. */
    void step(Elevator elevator);

    /** The travel direction this state represents. */
    Direction getDirection();

    String name();
}

package org.lld.state;

import org.lld.common.Direction;
import org.lld.models.Elevator;

public class MovingDownState implements ElevatorState {

    @Override
    public void step(Elevator elevator) {
        elevator.setCurrentFloor(elevator.getCurrentFloor() - 1);
        if (elevator.getStops().contains(elevator.getCurrentFloor())) {
            elevator.setState(new DoorOpenState());
        }
        // otherwise keep descending on the next tick
    }

    @Override
    public Direction getDirection() {
        return Direction.DOWN;
    }

    @Override
    public String name() {
        return "MOVING_DOWN";
    }
}

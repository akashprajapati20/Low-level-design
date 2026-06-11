package org.lld.state;

import org.lld.common.Direction;
import org.lld.models.Elevator;

public class MovingUpState implements ElevatorState {

    @Override
    public void step(Elevator elevator) {
        elevator.setCurrentFloor(elevator.getCurrentFloor() + 1);
        if (elevator.getStops().contains(elevator.getCurrentFloor())) {
            elevator.setState(new DoorOpenState());
        }
        // otherwise keep climbing on the next tick
    }

    @Override
    public Direction getDirection() {
        return Direction.UP;
    }

    @Override
    public String name() {
        return "MOVING_UP";
    }
}

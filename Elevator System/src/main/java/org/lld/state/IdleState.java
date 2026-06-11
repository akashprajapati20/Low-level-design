package org.lld.state;

import org.lld.common.Direction;
import org.lld.models.Elevator;

public class IdleState implements ElevatorState {

    @Override
    public void step(Elevator elevator) {
        Integer next = elevator.getSchedulingStrategy().nextStop(elevator);
        if (next == null) {
            return; // nothing to do, stay idle
        }
        if (next > elevator.getCurrentFloor()) {
            elevator.setDirection(Direction.UP);
            elevator.setState(new MovingUpState());
        } else if (next < elevator.getCurrentFloor()) {
            elevator.setDirection(Direction.DOWN);
            elevator.setState(new MovingDownState());
        } else {
            // a stop on the current floor: just open up
            elevator.setState(new DoorOpenState());
        }
    }

    @Override
    public Direction getDirection() {
        return Direction.IDLE;
    }

    @Override
    public String name() {
        return "IDLE";
    }
}

package org.lld.state;

import org.lld.common.Direction;
import org.lld.models.Elevator;

public class DoorOpenState implements ElevatorState {

    @Override
    public void step(Elevator elevator) {
        // Service the stop at the current floor: open, let people in/out, close.
        elevator.getDoor().open();
        elevator.removeStop(elevator.getCurrentFloor());
        elevator.getDoor().close();

        Integer next = elevator.getSchedulingStrategy().nextStop(elevator);
        if (next == null) {
            elevator.setDirection(Direction.IDLE);
            elevator.setState(new IdleState());
        } else if (next > elevator.getCurrentFloor()) {
            elevator.setDirection(Direction.UP);
            elevator.setState(new MovingUpState());
        } else if (next < elevator.getCurrentFloor()) {
            elevator.setDirection(Direction.DOWN);
            elevator.setState(new MovingDownState());
        } else {
            // another request for this same floor arrived; serve it next tick
            elevator.setState(new DoorOpenState());
        }
    }

    @Override
    public Direction getDirection() {
        return Direction.IDLE;
    }

    @Override
    public String name() {
        return "DOOR_OPEN";
    }
}

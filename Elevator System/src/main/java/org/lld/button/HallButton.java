package org.lld.button;

import org.lld.common.Direction;
import org.lld.request.HallRequest;
import org.lld.system.ElevatorSystem;

/**
 * Landing button. Pressing it raises a HallRequest and submits it to the
 * dispatcher, which selects an elevator to serve it.
 */
public class HallButton implements Button {

    private final int floor;
    private final Direction direction;
    private final ElevatorSystem system;

    public HallButton(int floor, Direction direction, ElevatorSystem system) {
        this.floor = floor;
        this.direction = direction;
        this.system = system;
    }

    @Override
    public HallRequest press() {
        HallRequest request = new HallRequest(floor, direction);
        system.handleHallRequest(request);
        return request;
    }
}

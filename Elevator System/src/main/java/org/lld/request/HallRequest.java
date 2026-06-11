package org.lld.request;

import org.lld.common.Direction;

/**
 * External request raised from a landing. Carries a direction because
 * pressing "up" on floor 3 is a different intent than pressing "down".
 * Routed through the dispatcher's selection strategy.
 */
public class HallRequest implements Request {
    private final int floor;
    private final Direction direction;

    public HallRequest(int floor, Direction direction) {
        this.floor = floor;
        this.direction = direction;
    }

    @Override
    public int getFloor() {
        return floor;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "HallRequest{floor=" + floor + ", direction=" + direction + "}";
    }
}

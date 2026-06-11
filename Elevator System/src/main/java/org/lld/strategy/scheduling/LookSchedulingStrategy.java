package org.lld.strategy.scheduling;

import org.lld.common.Direction;
import org.lld.models.Elevator;

/**
 * The classic LOOK / SCAN "elevator algorithm": keep sweeping in the current
 * direction, servicing every stop along the way, and only reverse once there
 * is nothing left ahead.
 */
public class LookSchedulingStrategy implements SchedulingStrategy {

    @Override
    public Integer nextStop(Elevator elevator) {
        if (elevator.getStops().isEmpty()) {
            return null;
        }

        int floor = elevator.getCurrentFloor();
        Direction direction = elevator.getDirection();

        Integer nearestAbove = null; // smallest stop >= floor
        Integer nearestBelow = null; // largest stop  <= floor
        for (int stop : elevator.getStops()) {
            if (stop >= floor && (nearestAbove == null || stop < nearestAbove)) {
                nearestAbove = stop;
            }
            if (stop <= floor && (nearestBelow == null || stop > nearestBelow)) {
                nearestBelow = stop;
            }
        }

        return switch (direction) {
            case UP -> nearestAbove != null ? nearestAbove : nearestBelow;
            case DOWN -> nearestBelow != null ? nearestBelow : nearestAbove;
            case IDLE -> chooseNearest(floor, nearestAbove, nearestBelow);
        };
    }

    private Integer chooseNearest(int floor, Integer above, Integer below) {
        if (above == null) {
            return below;
        }
        if (below == null) {
            return above;
        }
        return (above - floor) <= (floor - below) ? above : below;
    }
}

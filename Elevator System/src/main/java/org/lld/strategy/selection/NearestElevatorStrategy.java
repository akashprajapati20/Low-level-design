package org.lld.strategy.selection;

import org.lld.models.Elevator;
import org.lld.request.HallRequest;

import java.util.Comparator;
import java.util.List;

/**
 * Picks the elevator whose current floor is closest to the request's floor.
 */
public class NearestElevatorStrategy implements ElevatorSelectionStrategy {

    @Override
    public Elevator selectElevator(List<Elevator> elevators, HallRequest request) {
        return elevators.stream()
                .min(Comparator.comparingInt(e -> Math.abs(e.getCurrentFloor() - request.getFloor())))
                .orElseThrow(() -> new IllegalStateException("No elevators available"));
    }
}

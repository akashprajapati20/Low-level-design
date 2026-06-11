package org.lld.strategy.selection;

import org.lld.models.Elevator;
import org.lld.request.HallRequest;

import java.util.Comparator;
import java.util.List;

/**
 * Picks the elevator with the fewest pending stops.
 */
public class LeastBusyStrategy implements ElevatorSelectionStrategy {

    @Override
    public Elevator selectElevator(List<Elevator> elevators, HallRequest request) {
        return elevators.stream()
                .min(Comparator.comparingInt(e -> e.getStops().size()))
                .orElseThrow(() -> new IllegalStateException("No elevators available"));
    }
}

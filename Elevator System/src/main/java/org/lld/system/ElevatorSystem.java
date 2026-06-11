package org.lld.system;

import org.lld.models.Elevator;
import org.lld.request.HallRequest;
import org.lld.strategy.selection.ElevatorSelectionStrategy;

import java.util.List;

/**
 * Dispatcher / controller. The single place that knows about all elevators and
 * the entry point for hall requests. It delegates the "which car?" decision to
 * an injected selection strategy.
 *
 * Often modeled as a singleton (one control system per building), but we use
 * plain dependency injection here — it keeps the dispatcher testable and the
 * strategies swappable.
 */
public class ElevatorSystem {

    private final List<Elevator> elevators;
    private final ElevatorSelectionStrategy selectionStrategy;

    public ElevatorSystem(List<Elevator> elevators, ElevatorSelectionStrategy selectionStrategy) {
        this.elevators = elevators;
        this.selectionStrategy = selectionStrategy;
    }

    /** Route an external request: pick an elevator, then hand the stop off to it. */
    public void handleHallRequest(HallRequest request) {
        Elevator chosen = selectionStrategy.selectElevator(elevators, request);
        chosen.addStop(request.getFloor());
        System.out.printf("Dispatcher assigned %s to elevator %d%n", request, chosen.getId());
    }

    /** Advance every elevator by one tick. */
    public void step() {
        for (Elevator elevator : elevators) {
            elevator.step();
        }
    }

    public boolean hasPendingWork() {
        return elevators.stream().anyMatch(Elevator::hasPendingStops);
    }

    public List<Elevator> getElevators() {
        return elevators;
    }
}

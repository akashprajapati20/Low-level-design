package org.lld.strategy.selection;

import org.lld.models.Elevator;
import org.lld.request.HallRequest;

import java.util.List;

/**
 * Strategy: decide which elevator should serve a given hall request.
 * Lives in the dispatcher.
 */
public interface ElevatorSelectionStrategy {
    Elevator selectElevator(List<Elevator> elevators, HallRequest request);
}

package org.lld;

import org.lld.button.CarButton;
import org.lld.button.HallButton;
import org.lld.common.Direction;
import org.lld.models.Elevator;
import org.lld.observer.Display;
import org.lld.strategy.scheduling.LookSchedulingStrategy;
import org.lld.strategy.scheduling.SchedulingStrategy;
import org.lld.strategy.selection.ElevatorSelectionStrategy;
import org.lld.strategy.selection.NearestElevatorStrategy;
import org.lld.system.ElevatorSystem;

import java.util.List;

/**
 * Demo wiring: constructs the system and injects the strategies.
 */
public class Main {

    public static void main(String[] args) {
        // Each car sequences its own stops with the LOOK algorithm.
        SchedulingStrategy scheduling = new LookSchedulingStrategy();

        Elevator e1 = new Elevator(1, 0, scheduling);
        Elevator e2 = new Elevator(2, 5, scheduling);

        // Observers watch each car.
        e1.addObserver(new Display("Lobby"));
        e2.addObserver(new Display("Lobby"));

        // The dispatcher decides which car serves a hall request.
        ElevatorSelectionStrategy selection = new NearestElevatorStrategy();
        ElevatorSystem system = new ElevatorSystem(List.of(e1, e2), selection);

        // --- Hall requests (external) flow through the dispatcher ---
        new HallButton(1, Direction.UP, system).press();    // e1 (floor 0) is nearest -> e1
        new HallButton(6, Direction.DOWN, system).press();  // e2 (floor 5) is nearest -> e2

        // --- Car requests (internal) belong to one specific elevator ---
        new CarButton(4, e1).press(); // rider in e1 wants floor 4
        new CarButton(8, e2).press(); // rider in e2 wants floor 8

        // Run the simulation until every car is idle.
        int tick = 0;
        while (system.hasPendingWork() && tick < 30) {
            System.out.println("--- tick " + (++tick) + " ---");
            system.step();
        }

        System.out.println("All requests served in " + tick + " ticks.");
    }
}

package org.lld.models;

import org.lld.common.Direction;
import org.lld.observer.ElevatorObserver;
import org.lld.request.CarRequest;
import org.lld.state.ElevatorState;
import org.lld.state.IdleState;
import org.lld.strategy.scheduling.SchedulingStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Owns its own physical state (floor, direction, pending stops, door) and
 * decides only its own next stop. It never knows about its sibling elevators
 * or which car a hall request should go to — that is the dispatcher's job.
 */
public class Elevator {

    private final int id;
    private int currentFloor;
    private Direction direction = Direction.IDLE;

    // Insertion order is preserved so FCFS works; LOOK sorts as needed.
    private final Set<Integer> stops = new LinkedHashSet<>();
    private final Door door = new Door();

    private ElevatorState state = new IdleState();
    private final SchedulingStrategy schedulingStrategy;

    private final List<ElevatorObserver> observers = new ArrayList<>();

    public Elevator(int id, int startFloor, SchedulingStrategy schedulingStrategy) {
        this.id = id;
        this.currentFloor = startFloor;
        this.schedulingStrategy = schedulingStrategy;
    }

    /** Advance the simulation by one tick; behavior is delegated to the current state. */
    public void step() {
        state.step(this);
        notifyObservers();
    }

    /** Internal request from inside the car — added directly, never via the dispatcher. */
    public void handleCarRequest(CarRequest request) {
        addStop(request.getFloor());
    }

    public void addStop(int floor) {
        stops.add(floor);
    }

    public void removeStop(int floor) {
        stops.remove(floor);
    }

    // --- Observer wiring ---

    public void addObserver(ElevatorObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (ElevatorObserver observer : observers) {
            observer.update(this);
        }
    }

    // --- Accessors ---

    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Set<Integer> getStops() {
        return Collections.unmodifiableSet(stops);
    }

    public boolean hasPendingStops() {
        return !stops.isEmpty();
    }

    public Door getDoor() {
        return door;
    }

    public ElevatorState getState() {
        return state;
    }

    public void setState(ElevatorState state) {
        this.state = state;
    }

    public SchedulingStrategy getSchedulingStrategy() {
        return schedulingStrategy;
    }
}

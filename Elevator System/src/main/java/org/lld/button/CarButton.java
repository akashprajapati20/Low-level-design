package org.lld.button;

import org.lld.models.Elevator;
import org.lld.request.CarRequest;

/**
 * In-car destination button. Pressing it raises a CarRequest that belongs to
 * this one elevator and bypasses the dispatcher.
 */
public class CarButton implements Button {

    private final int destinationFloor;
    private final Elevator elevator;

    public CarButton(int destinationFloor, Elevator elevator) {
        this.destinationFloor = destinationFloor;
        this.elevator = elevator;
    }

    @Override
    public CarRequest press() {
        CarRequest request = new CarRequest(destinationFloor);
        elevator.handleCarRequest(request);
        return request;
    }
}

package org.lld.request;

/**
 * Internal request raised from inside the car. Belongs to one specific
 * elevator and bypasses the dispatcher entirely.
 */
public class CarRequest implements Request {
    private final int destinationFloor;

    public CarRequest(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    @Override
    public int getFloor() {
        return destinationFloor;
    }

    @Override
    public String toString() {
        return "CarRequest{destination=" + destinationFloor + "}";
    }
}

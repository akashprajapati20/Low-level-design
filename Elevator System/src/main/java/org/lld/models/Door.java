package org.lld.models;

import org.lld.common.DoorState;

public class Door {
    private DoorState state = DoorState.CLOSED;

    public void open() {
        state = DoorState.OPENING;
        // ... physical actuation would happen here ...
        state = DoorState.OPEN;
    }

    public void close() {
        state = DoorState.CLOSING;
        // ... physical actuation would happen here ...
        state = DoorState.CLOSED;
    }

    public DoorState getState() {
        return state;
    }

    public boolean isOpen() {
        return state == DoorState.OPEN;
    }
}

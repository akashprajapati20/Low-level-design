package com.roombooking.exception;

/** Thrown when a referenced room does not exist. */
public class RoomNotFoundException extends RuntimeException {

    public RoomNotFoundException(String roomId) {
        super("No room found with id: " + roomId);
    }
}

package com.roombooking.exception;

/** Thrown when a referenced booking does not exist. */
public class BookingNotFoundException extends RuntimeException {

    public BookingNotFoundException(String bookingId) {
        super("No booking found with id: " + bookingId);
    }
}

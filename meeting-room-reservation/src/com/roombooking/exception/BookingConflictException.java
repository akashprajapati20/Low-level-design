package com.roombooking.exception;

import com.roombooking.model.TimeSlot;

/** Thrown when a requested slot overlaps an existing confirmed booking. */
public class BookingConflictException extends RuntimeException {

    public BookingConflictException(String roomId, TimeSlot slot) {
        super("Room " + roomId + " is already booked during " + slot);
    }
}

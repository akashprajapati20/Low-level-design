package com.roombooking.model;

/**
 * Lifecycle state of a booking. Cancellation is a soft state change rather than
 * a hard delete, so history remains queryable and a cancelled slot frees up
 * naturally because conflict checks only consider CONFIRMED bookings.
 */
public enum BookingStatus {
    CONFIRMED,
    CANCELLED
}

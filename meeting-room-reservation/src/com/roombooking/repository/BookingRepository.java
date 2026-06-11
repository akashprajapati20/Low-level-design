package com.roombooking.repository;

import com.roombooking.model.Booking;

import java.util.List;
import java.util.Optional;

/**
 * Storage abstraction for bookings. Services depend on this interface, never on
 * a concrete implementation (Dependency Inversion), so swapping the in-memory
 * store for a JPA/JDBC-backed one needs zero changes above this line.
 */
public interface BookingRepository {

    Booking save(Booking booking);

    Optional<Booking> findById(String bookingId);

    List<Booking> findByRoomId(String roomId);

    List<Booking> findByEmployeeId(String employeeId);

    /** Only CONFIRMED bookings for the room; the input to conflict detection. */
    List<Booking> findConfirmedByRoomId(String roomId);
}

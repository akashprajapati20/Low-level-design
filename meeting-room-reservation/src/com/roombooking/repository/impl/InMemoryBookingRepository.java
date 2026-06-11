package com.roombooking.repository.impl;

import com.roombooking.model.Booking;
import com.roombooking.repository.BookingRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Thread-safe in-memory booking store backed by a {@link ConcurrentHashMap}.
 * Mutual exclusion for the check-and-save invariant is provided one layer up by
 * the LockManager; this class only needs each individual operation to be safe.
 */
public class InMemoryBookingRepository implements BookingRepository {

    private final Map<String, Booking> store = new ConcurrentHashMap<>();

    @Override
    public Booking save(Booking booking) {
        store.put(booking.getId(), booking);
        return booking;
    }

    @Override
    public Optional<Booking> findById(String bookingId) {
        return Optional.ofNullable(store.get(bookingId));
    }

    @Override
    public List<Booking> findByRoomId(String roomId) {
        return store.values().stream()
                .filter(b -> b.getRoomId().equals(roomId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> findByEmployeeId(String employeeId) {
        return store.values().stream()
                .filter(b -> b.getEmployeeId().equals(employeeId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> findConfirmedByRoomId(String roomId) {
        return store.values().stream()
                .filter(b -> b.getRoomId().equals(roomId) && b.isConfirmed())
                .collect(Collectors.toList());
    }
}

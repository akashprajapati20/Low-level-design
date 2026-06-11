package com.roombooking.service;

import com.roombooking.conflict.ConflictDetector;
import com.roombooking.model.Room;
import com.roombooking.model.TimeSlot;
import com.roombooking.repository.RoomRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Read side: which rooms are free for a window.
 *
 * <p>Deliberately does NOT take locks. Availability is an advisory snapshot;
 * the authoritative guarantee comes from the atomic check inside
 * {@link BookingService#bookRoom}. Locking all rooms for a read would serialize
 * the whole system for no correctness benefit.
 */
public class RoomAvailabilityService {

    private final RoomRepository roomRepository;
    private final ConflictDetector conflictDetector;

    public RoomAvailabilityService(RoomRepository roomRepository,
                                   ConflictDetector conflictDetector) {
        this.roomRepository = roomRepository;
        this.conflictDetector = conflictDetector;
    }

    public List<Room> getAvailableRooms(LocalDateTime start, LocalDateTime end) {
        TimeSlot slot = new TimeSlot(start, end);
        return roomRepository.findAll().stream()
                .filter(room -> !conflictDetector.hasConflict(room.getId(), slot))
                .collect(Collectors.toList());
    }
}

package com.roombooking.conflict;

import com.roombooking.model.TimeSlot;

/**
 * Decides whether a candidate slot conflicts with existing bookings for a room.
 * Kept behind an interface so the conflict rule is open for extension (e.g. a
 * buffer-aware rule that forbids back-to-back bookings) without modifying any
 * service.
 */
public interface ConflictDetector {

    boolean hasConflict(String roomId, TimeSlot slot);
}

package com.roombooking.conflict;

import com.roombooking.model.TimeSlot;
import com.roombooking.repository.BookingRepository;

/** Default rule: a conflict exists if any confirmed booking overlaps the slot. */
public class OverlapConflictDetector implements ConflictDetector {

    private final BookingRepository bookingRepository;

    public OverlapConflictDetector(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public boolean hasConflict(String roomId, TimeSlot slot) {
        return bookingRepository.findConfirmedByRoomId(roomId).stream()
                .anyMatch(booking -> booking.getSlot().overlaps(slot));
    }
}

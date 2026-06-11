package com.roombooking.model;

import com.roombooking.exception.InvalidTimeRangeException;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Immutable value object representing a half-open time interval [start, end).
 *
 * <p>All time-range logic lives here (Single Responsibility):
 * <ul>
 *   <li>Validity is enforced in the constructor, so an invalid range can never
 *       exist anywhere else in the system.</li>
 *   <li>{@link #overlaps(TimeSlot)} is the one and only definition of overlap.</li>
 * </ul>
 *
 * <p>Intervals are half-open, so back-to-back bookings (10:00-11:00 and
 * 11:00-12:00) do <em>not</em> conflict.
 */
public final class TimeSlot {

    private final LocalDateTime start;
    private final LocalDateTime end;

    public TimeSlot(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            throw new InvalidTimeRangeException("Start and end must not be null");
        }
        if (!start.isBefore(end)) {
            throw new InvalidTimeRangeException(start, end);
        }
        this.start = start;
        this.end = end;
    }

    /** Two half-open intervals overlap iff each starts before the other ends. */
    public boolean overlaps(TimeSlot other) {
        return start.isBefore(other.end) && other.start.isBefore(end);
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeSlot)) return false;
        TimeSlot other = (TimeSlot) o;
        return start.equals(other.start) && end.equals(other.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "[" + start + " -> " + end + ")";
    }
}

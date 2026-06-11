package com.roombooking.exception;

import java.time.LocalDateTime;

/** Thrown when a time range is malformed (start is not strictly before end). */
public class InvalidTimeRangeException extends RuntimeException {

    public InvalidTimeRangeException(String message) {
        super(message);
    }

    public InvalidTimeRangeException(LocalDateTime start, LocalDateTime end) {
        super("Invalid time range: start (" + start + ") must be before end (" + end + ")");
    }
}

package com.roombooking.model;

import java.util.Objects;

/**
 * A reservation of a room by an employee for a given time slot.
 *
 * <p>References the room and employee by id (loose coupling) and owns its
 * {@link TimeSlot}. The only mutable field is {@code status}; it is marked
 * {@code volatile} so a cancellation made under one thread is visible to a
 * booker checking conflicts on another. All cancellations go through the
 * service under the room lock, so this is belt-and-suspenders for visibility.
 */
public final class Booking {

    private final String id;
    private final String employeeId;
    private final String roomId;
    private final TimeSlot slot;
    private volatile BookingStatus status;

    public Booking(String id, String employeeId, String roomId, TimeSlot slot) {
        this.id = Objects.requireNonNull(id, "id");
        this.employeeId = Objects.requireNonNull(employeeId, "employeeId");
        this.roomId = Objects.requireNonNull(roomId, "roomId");
        this.slot = Objects.requireNonNull(slot, "slot");
        this.status = BookingStatus.CONFIRMED;
    }

    public void cancel() {
        this.status = BookingStatus.CANCELLED;
    }

    public boolean isConfirmed() {
        return status == BookingStatus.CONFIRMED;
    }

    public String getId() {
        return id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getRoomId() {
        return roomId;
    }

    public TimeSlot getSlot() {
        return slot;
    }

    public BookingStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Booking{" + id + ", room=" + roomId + ", emp=" + employeeId
                + ", " + slot + ", " + status + "}";
    }
}

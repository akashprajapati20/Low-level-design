package com.roombooking;

import com.roombooking.model.Booking;
import com.roombooking.model.Room;
import com.roombooking.service.BookingService;
import com.roombooking.service.RoomAvailabilityService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Facade exposing exactly the five public operations, delegating to the two
 * services. Callers depend on this one type and never see the wiring behind it.
 */
public class MeetingRoomReservationSystem {

    private final BookingService bookingService;
    private final RoomAvailabilityService availabilityService;

    public MeetingRoomReservationSystem(BookingService bookingService,
                                        RoomAvailabilityService availabilityService) {
        this.bookingService = bookingService;
        this.availabilityService = availabilityService;
    }

    public Booking bookRoom(String employeeId, String roomId,
                            LocalDateTime startTime, LocalDateTime endTime) {
        return bookingService.bookRoom(employeeId, roomId, startTime, endTime);
    }

    public List<Room> getAvailableRooms(LocalDateTime startTime, LocalDateTime endTime) {
        return availabilityService.getAvailableRooms(startTime, endTime);
    }

    public void cancelBooking(String bookingId) {
        bookingService.cancelBooking(bookingId);
    }

    public List<Booking> listBookingsForRoom(String roomId) {
        return bookingService.listBookingsForRoom(roomId);
    }

    public List<Booking> listBookingsForEmployee(String employeeId) {
        return bookingService.listBookingsForEmployee(employeeId);
    }
}

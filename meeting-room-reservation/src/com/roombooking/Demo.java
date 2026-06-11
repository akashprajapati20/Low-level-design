package com.roombooking;

import com.roombooking.concurrency.InMemoryLockManager;
import com.roombooking.concurrency.LockManager;
import com.roombooking.concurrency.LockType2;
import com.roombooking.conflict.ConflictDetector;
import com.roombooking.conflict.OverlapConflictDetector;
import com.roombooking.exception.BookingConflictException;
import com.roombooking.exception.InvalidTimeRangeException;
import com.roombooking.id.IdGenerator;
import com.roombooking.id.UuidGenerator;
import com.roombooking.model.Booking;
import com.roombooking.model.Employee;
import com.roombooking.model.Room;
import com.roombooking.repository.BookingRepository;
import com.roombooking.repository.EmployeeRepository;
import com.roombooking.repository.RoomRepository;
import com.roombooking.repository.impl.InMemoryBookingRepository;
import com.roombooking.repository.impl.InMemoryEmployeeRepository;
import com.roombooking.repository.impl.InMemoryRoomRepository;
import com.roombooking.service.BookingService;
import com.roombooking.service.RoomAvailabilityService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Wires everything together by hand (no DI framework) and runs:
 *   1. the example scenario from the spec, and
 *   2. a concurrency stress test proving exactly one booking wins a race.
 */
public class Demo {

    private static final LocalDate DAY = LocalDate.of(2026, 6, 11);

    private static LocalDateTime at(int hour, int minute) {
        return LocalDateTime.of(DAY, LocalTime.of(hour, minute));
    }

    public static void main(String[] args) throws InterruptedException {
        // ---- Composition root: build the object graph ----
        List<Room> rooms = List.of(
                new Room("A", "Room A", 6, "Floor 1"),
                new Room("B", "Room B", 4, "Floor 1"),
                new Room("C", "Room C", 10, "Floor 2"));
        List<Employee> employees = List.of(
                new Employee("X", "User X", "x@corp.com"),
                new Employee("Y", "User Y", "y@corp.com"));

        BookingRepository bookingRepo = new InMemoryBookingRepository();
        RoomRepository roomRepo = new InMemoryRoomRepository(rooms);
        EmployeeRepository employeeRepo = new InMemoryEmployeeRepository(employees);
        ConflictDetector conflictDetector = new OverlapConflictDetector(bookingRepo);
        IdGenerator idGenerator = new UuidGenerator();
        LockManager lockManager = new InMemoryLockManager();
        LockType2 lockType2=new LockType2();
        BookingService bookingService = new BookingService(
                bookingRepo, roomRepo, employeeRepo, conflictDetector, idGenerator, lockManager,lockType2);
        RoomAvailabilityService availabilityService =
                new RoomAvailabilityService(roomRepo, conflictDetector);

        MeetingRoomReservationSystem system =
                new MeetingRoomReservationSystem(bookingService, availabilityService);

        // ---- Scenario from the spec ----
        System.out.println("=== Example scenario ===");

        Booking b1 = system.bookRoom("X", "A", at(10, 0), at(11, 0));
        System.out.println("X books Room A 10:00-11:00 -> OK   " + b1.getId());

        try {
            system.bookRoom("X", "A", at(10, 30), at(11, 30));
            System.out.println("X books Room A 10:30-11:30 -> OK (UNEXPECTED)");
        } catch (BookingConflictException e) {
            System.out.println("X books Room A 10:30-11:30 -> REJECTED: " + e.getMessage());
        }

        Booking b2 = system.bookRoom("Y", "B", at(10, 30), at(11, 30));
        System.out.println("Y books Room B 10:30-11:30 -> OK   " + b2.getId());

        // ---- Back-to-back booking is allowed (half-open intervals) ----
        Booking b3 = system.bookRoom("Y", "A", at(11, 0), at(12, 0));
        System.out.println("Y books Room A 11:00-12:00 -> OK   (back-to-back, no overlap)");

        // ---- Availability ----
        List<Room> free = system.getAvailableRooms(at(10, 30), at(11, 30));
        System.out.println("Available rooms 10:30-11:30 -> "
                + free.stream().map(Room::getName).toList());

        // ---- Invalid range ----
        try {
            system.bookRoom("X", "C", at(12, 0), at(12, 0));
        } catch (InvalidTimeRangeException e) {
            System.out.println("Book with start == end -> REJECTED: " + e.getMessage());
        }

        // ---- Cancellation frees the slot ----
        system.cancelBooking(b1.getId());
        List<Room> freeAfterCancel = system.getAvailableRooms(at(10, 0), at(10, 30));
        System.out.println("Cancelled X's Room A booking; Room A now free 10:00-10:30 -> "
                + freeAfterCancel.stream().anyMatch(r -> r.getId().equals("A")));

        // ---- Listings ----
        System.out.println("Bookings for Room A: " + system.listBookingsForRoom("A").size());
        System.out.println("Bookings for User Y: " + system.listBookingsForEmployee("Y").size());

        // ---- Concurrency stress test ----
        System.out.println("\n=== Concurrency: 50 threads race for the same slot ===");
        runRace(system);
    }

    /** 50 threads simultaneously attempt the identical booking; exactly one must win. */
    private static void runRace(MeetingRoomReservationSystem system) throws InterruptedException {
        int threads = 50;
        CountDownLatch ready = new CountDownLatch(threads);
        CountDownLatch go = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(threads);
        AtomicInteger success = new AtomicInteger();
        AtomicInteger rejected = new AtomicInteger();

        for (int i = 0; i < threads; i++) {
            new Thread(() -> {
                ready.countDown();
                try {
                    go.await();
                    system.bookRoom("X", "C", at(14, 0), at(15, 0));
                    success.incrementAndGet();
                } catch (BookingConflictException e) {
                    rejected.incrementAndGet();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown();
                }
            }).start();
        }

        ready.await();   // all threads parked at the start line
        go.countDown();  // release them at once
        done.await();    // wait for all to finish

        System.out.println("Successful bookings: " + success.get() + " (expected 1)");
        System.out.println("Rejected (conflict): " + rejected.get() + " (expected 49)");
        System.out.println("Room C bookings stored: " + system.listBookingsForRoom("C").size());
    }
}

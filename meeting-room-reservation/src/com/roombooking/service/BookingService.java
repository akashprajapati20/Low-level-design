package com.roombooking.service;

import com.roombooking.concurrency.LockManager;
import com.roombooking.concurrency.LockType2;
import com.roombooking.conflict.ConflictDetector;
import com.roombooking.exception.BookingConflictException;
import com.roombooking.exception.BookingNotFoundException;
import com.roombooking.exception.EmployeeNotFoundException;
import com.roombooking.exception.RoomNotFoundException;
import com.roombooking.id.IdGenerator;
import com.roombooking.model.Booking;
import com.roombooking.model.TimeSlot;
import com.roombooking.repository.BookingRepository;
import com.roombooking.repository.EmployeeRepository;
import com.roombooking.repository.RoomRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Orchestrates booking creation, cancellation, and listing.
 *
 * <p>Depends only on abstractions, all constructor-injected (Dependency
 * Inversion). The concurrency-critical part is {@link #bookRoom}: the conflict
 * check and the save happen together inside the room lock, which is what makes
 * "no double-booking" hold under simultaneous attempts.
 */
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final EmployeeRepository employeeRepository;
    private final ConflictDetector conflictDetector;
    private final IdGenerator idGenerator;
    private final LockManager lockManager;
    private final LockType2 roomLockProvider;
    public BookingService(BookingRepository bookingRepository,
                          RoomRepository roomRepository,
                          EmployeeRepository employeeRepository,
                          ConflictDetector conflictDetector,
                          IdGenerator idGenerator,
                          LockManager lockManager, LockType2 roomLockProvider) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.employeeRepository = employeeRepository;
        this.conflictDetector = conflictDetector;
        this.idGenerator = idGenerator;
        this.lockManager = lockManager;
        this.roomLockProvider = roomLockProvider;
    }

    public Booking bookRoom(String employeeId, String roomId,
                            LocalDateTime start, LocalDateTime end) {
        // Constructing the slot validates the range (throws InvalidTimeRangeException).
        TimeSlot slot = new TimeSlot(start, end);

        // Fail fast on unknown entities, before taking any lock.
        employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));

        // Atomic check-and-save, scoped to this room only.
//        return lockManager.executeWithLock(roomId, () -> {
//            if (conflictDetector.hasConflict(roomId, slot)) {
//                throw new BookingConflictException(roomId, slot);
//            }
//            Booking booking = new Booking(idGenerator.nextId(), employeeId, roomId, slot);
//            return bookingRepository.save(booking);
//        });

        ReentrantLock lock = roomLockProvider.getLock(roomId);
        lock.lock();                       // <-- acquire BEFORE the try
        try {
            if (conflictDetector.hasConflict(roomId, slot)) {
                throw new BookingConflictException(roomId, slot);
            }
            Booking booking = new Booking(idGenerator.nextId(), employeeId, roomId, slot);
            return bookingRepository.save(booking);
        } finally {
            lock.unlock();
        }
    }

    public void cancelBooking(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(bookingId));

        // Cancel under the room lock so a concurrent booker reads consistent state.
        lockManager.executeWithLock(booking.getRoomId(), () -> {
            booking.cancel();
            bookingRepository.save(booking);
        });
    }

    public List<Booking> listBookingsForRoom(String roomId) {
        return bookingRepository.findByRoomId(roomId);
    }

    public List<Booking> listBookingsForEmployee(String employeeId) {
        return bookingRepository.findByEmployeeId(employeeId);
    }
}

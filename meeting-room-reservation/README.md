# Meeting Room Reservation System (LLD)

A low-level design in Java demonstrating SOLID principles, graceful failure,
and concurrency-safe booking.

## Public API (facade: `MeetingRoomReservationSystem`)
- `bookRoom(employeeId, roomId, startTime, endTime)`
- `getAvailableRooms(startTime, endTime)`
- `cancelBooking(bookingId)`
- `listBookingsForRoom(roomId)`
- `listBookingsForEmployee(employeeId)`

## Package layout
```
com.roombooking
├── MeetingRoomReservationSystem   facade over the two services
├── Demo                           runnable: spec scenario + 50-thread race
├── model/                         Employee, Room, Booking, TimeSlot, BookingStatus
├── exception/                     typed runtime exceptions (graceful failure)
├── repository/                    BookingRepository, RoomRepository, EmployeeRepository
│   └── impl/                      in-memory (ConcurrentHashMap) implementations
├── concurrency/                   LockManager + InMemoryLockManager (striped locks)
├── conflict/                      ConflictDetector + OverlapConflictDetector
├── id/                            IdGenerator + UuidGenerator
└── service/                       BookingService, RoomAvailabilityService
```

## How the requirements are met
- No double-booking: the conflict check and the save run together inside a
  per-room lock (`BookingService.bookRoom`), so simultaneous attempts serialize.
- Graceful failure: overlaps, missing rooms/employees/bookings, and bad time
  ranges all throw typed exceptions instead of returning nulls or booleans.
- Invalid range (start >= end): rejected in the `TimeSlot` constructor, so an
  invalid range can never exist in the system.
- Listing by room / employee: dedicated repository finders.
- Concurrency safety: `InMemoryLockManager` uses striped per-room
  `ReentrantLock`s — same room serializes, different rooms run in parallel.

## SOLID mapping
- S: TimeSlot owns time logic; repos own storage; services own orchestration;
     ConflictDetector owns the conflict rule.
- O: new conflict rules, id strategies, lock backends, or storage = new classes.
- L: any interface implementation substitutes cleanly.
- I: three small repository interfaces, not one fat DAO.
- D: services depend only on interfaces, wired by constructor injection.

## Build & run (needs JDK 17+)
```
cd meeting-room-reservation
javac -d out $(find src -name '*.java')
java -cp out com.roombooking.Demo
```

## Production notes (intentionally out of scope here)
- In-memory locks work on ONE app instance. For horizontal scaling, swap in a
  Redis-backed `LockManager` AND/OR enforce no-overlap with a database
  constraint (e.g. Postgres `EXCLUDE USING gist` over a tstzrange) so the
  invariant holds even if a lock fails.
- `LocalDateTime` assumes a single timezone; store UTC (`Instant`) for
  multi-office deployments.
- Not modeled: reschedule/modify, authorization on cancel, recurring bookings,
  working-hours/duration rules, capacity-vs-attendees checks.

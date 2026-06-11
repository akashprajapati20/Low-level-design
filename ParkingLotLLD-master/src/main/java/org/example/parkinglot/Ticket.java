package org.example.parkinglot;

import org.example.parkinglot.ParkingSpot;
import org.example.vehicles.Vehicle;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Ticket {
    private ParkingSpot parkingSpot;
    private Vehicle vehicle;
    private LocalDateTime startTime;


    // Constructor to initialize ticket with parking spot and vehicle
    public Ticket(ParkingSpot parkingSpot, Vehicle vehicle) {
        this.parkingSpot = parkingSpot;
        this.vehicle = vehicle;
        this.startTime = LocalDateTime.now();  // Set current time as the start time
    }

}

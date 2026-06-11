package org.example.gates;

import org.example.parkinglot.ParkingLot;
import org.example.parkinglot.ParkingSpot;
import org.example.vehicles.Vehicle;
import org.example.vehicles.VehicleFactory;

import java.util.Scanner;

public class EntranceGate {
    private ParkingLot parkingLot;

    // Constructor to initialize EntranceGate with the parking lot
    public EntranceGate(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    // Method to process the vehicle entrance
    public void processEntrance() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the vehicle license plate: ");
        String licensePlate = scanner.next();
        System.out.println("Enter the vehicle type (Car or Bike): ");
        String vehicleType = scanner.next();

        Vehicle vehicle = VehicleFactory.createVehicle(vehicleType, licensePlate);
        if (vehicle == null) {
            System.out.println("Invalid vehicle type! Only Car and Bike are allowed.");
            return;
        }

        // Try to park the vehicle
        ParkingSpot spot = parkingLot.parkVehicle(vehicle);
        if (spot != null) {
            System.out.println("Vehicle parked successfully in spot: " + spot.getSpotNumber());
        } else {
            System.out.println("No available spots for the vehicle type.");
        }
    }
}

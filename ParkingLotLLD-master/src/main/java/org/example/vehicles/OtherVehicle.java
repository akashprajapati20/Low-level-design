package org.example.vehicles;

// OtherVehicle class for any other vehicle type
class OtherVehicle extends Vehicle {
    private static final double RATE = 15.0; // $15 per hour for other vehicles

    // Constructor to initialize OtherVehicle
    public OtherVehicle(String licensePlate, int hoursStayed) {
        super(licensePlate, "Other");

    }

    // Implement calculateFee for Car
    @Override
    public double calculateFee(int hoursStayed) {
        return hoursStayed * RATE;
    }
}
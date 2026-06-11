# Parking Lot System (LLD)

## Overview
This project is a Low-Level Design (LLD) implementation of a Parking Lot Management System. The solution is built using Java and adheres to the **SOLID principles** and utilizes **design patterns** to ensure modularity and scalability. It supports multiple vehicle types, parking spot types, and payment methods.

## Features
- Multi-floor parking lot with entry and exit gates.
- Support for different parking spot types (e.g. Cars, Bikes).
- Dynamic parking spot availability display.
- Ticket generation for parked vehicles.
- Payment processing via cash, credit card, or UPI.
- Hourly Charges 

## Project Structure
The project follows a modular structure for better maintainability:

### Key Components

#### `parkinglot` package
- **ParkingLot.java**: Manages floors, entry gates, exit gates, and parking spot booking.
- **ParkingFloor.java**: Manages parking spots and updates the display board.
- **ParkingSpot.java**: Abstract representation of a parking spot (Compact, Large, etc.).
- **Ticket.java**: Represents a parking ticket issued to a vehicle.

#### `vehicle` package
- **Vehicle.java**: Abstract class for vehicles.
- **CarVehicle.java**, **BikeVehicle.java**, **OtherVehicle.java**: Specific implementations for vehicle types.

#### `payment` package
- **Payment.java**: Manages payment logic.
- **PaymentStrategy.java**: Strategy pattern for payment processing.
- **CashPayment.java**, **CreditCardPayment.java**, **UPIPayment.java**: Specific implementations for payment methods.

#### `Main.java`
- Entry point of the application. Contains the `main` method to initialize and run the parking lot system.

## How to Run
1. **Prerequisites**:
   - Java 8 or later.
   - IntelliJ IDEA (recommended) or any other IDE of your choice.

2. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd ParkingLotLLD
   ```

3. **Import the Project**:
   - Open IntelliJ IDEA.
   - Select `File > Open` and choose the project's root directory.

4. **Build the Project**:
   - Ensure Maven/Gradle (if configured) dependencies are resolved.
   - Compile the project by selecting `Build > Build Project`.

5. **Run the Application**:
   - Right-click on `Main.java`.
   - Select `Run 'Main.main()'`.

6. **Interact with the System**:
   - The system simulates parking operations.
   - Check console logs for actions like ticket generation, parking spot allocation, and payments.

## Extending the System
The design ensures the system is easily extensible. Examples:
- **Add New Vehicle Types**:
  - Create a new class extending `Vehicle.java`.
- **Add New Payment Methods**:
  - Implement the `PaymentStrategy` interface.
- **Enhance Display Boards**:
  - Modify `ParkingFloor.java` to update the display logic.

## License
This project is open-source and available under the MIT License.

## Author
Developed by Mrunmai Dahare. For inquiries, contact mrunmaidahare1@gmail.com.
Subscribe to my Youtube Channel @mrunmaidaharecodes 


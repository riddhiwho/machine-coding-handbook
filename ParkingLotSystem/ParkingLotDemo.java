import java.util.List;

import vehicle.Bike;
import vehicle.Car;
import vehicle.Vehicle;
import vehicle.VehicleType;

public class ParkingLotDemo {
    public static void main(String[] args) {
        ParkingLot parkingLot = ParkingLot.getInstance();

        List<ParkingSpot> parkingSpotsFloor1 = List.of(
                new ParkingSpot(101, VehicleType.CAR),
                new ParkingSpot(102, VehicleType.CAR),
                new ParkingSpot(103, VehicleType.BIKE)
        );

        List<ParkingSpot> parkingSpotsFloor2 = List.of(
                new ParkingSpot(201, VehicleType.BIKE),
                new ParkingSpot(202, VehicleType.TRUCK)
        );

        ParkingFloor floor1 = new ParkingFloor(1, parkingSpotsFloor1);
        ParkingFloor floor2 = new ParkingFloor(2, parkingSpotsFloor2);

        parkingLot.addFloor(floor1);
        parkingLot.addFloor(floor2);

        Vehicle car1 = new Car("ABC123");
        Vehicle car2 = new Car("XYZ789");
        Vehicle bike1 = new Bike("M1234");

        System.out.println("Available spots for Car:");
        for (ParkingFloor floor: parkingLot.getParkingFloors()) {
            System.out.println("Floor: " + floor.getFloorNumber() + " " + floor.getAvailableSpots(VehicleType.CAR));
        }

        try{
            parkingLot.parkVehicle(car1);
            parkingLot.parkVehicle(car2);
            parkingLot.parkVehicle(bike1);

            Vehicle car3 = new Car("DL0432");
            parkingLot.parkVehicle(car3);
        }catch(Exception e){
            System.out.println("Exception: " + e);
        }

        try{
            parkingLot.unparkVehicle(car1);
            parkingLot.unparkVehicle(car2);
            parkingLot.unparkVehicle(car1);
        } catch(Exception e){
            System.out.println("Exception: " + e);
        }
    }
}

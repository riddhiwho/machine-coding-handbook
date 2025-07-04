import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import vehicle.Vehicle;

public class ParkingLot {
    private static ParkingLot instance;
    private List<ParkingFloor> floors;

    public ParkingLot(){
        floors = new ArrayList<>();
    }

    public static synchronized ParkingLot getInstance(){
        if(instance == null) instance = new ParkingLot();
        return instance;
    }

    public void addFloor(ParkingFloor floor){
        floors.add(floor);
    }

    public List<ParkingFloor> getParkingFloors(){
        return floors;
    }

    public synchronized void parkVehicle(Vehicle vehicle) throws Exception{
        for(ParkingFloor floor : floors){
            Optional<ParkingSpot> spot = floor.getAvailableSpot(vehicle.getVehicleType());
            if(spot.isPresent()){
                ParkingSpot parkingSpot = spot.get();
                if(parkingSpot.park(vehicle)){
                    System.out.println("Vehicle parked: " + vehicle.toString());
                    return;
                }
            }
        }
        throw new Exception("No available spot for: " + vehicle.getVehicleType());
    }

    public synchronized Vehicle unparkVehicle(Vehicle vehicle) throws Exception{
        for(ParkingFloor floor : floors){
            for(ParkingSpot spot : floor.getParkingSpots()){
                if(!spot.isAvailable() && spot.getVehicle() == vehicle) {
                    spot.unpark();
                    System.out.println("Vehicle unparked: " + vehicle.toString());
                    return vehicle;
                }
            }
            throw new Exception("Exception occurred while unparking vehicle: "+ vehicle.toString());
        }
        return null;
    }

}

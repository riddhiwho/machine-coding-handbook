import java.util.List;
import java.util.Optional;

import vehicle.VehicleType;

public class ParkingFloor {
    private int floorNumber;
    private List<ParkingSpot> parkingSpots;

    public ParkingFloor(int floorNumber, List<ParkingSpot> parkingSpots){
        this.floorNumber = floorNumber;
        this.parkingSpots = parkingSpots;
    }

    public synchronized Optional<ParkingSpot> getAvailableSpot(VehicleType type){
        return parkingSpots.stream()
                .filter(spot -> spot.isAvailable() && spot.getVehicleType()==type)
                .findFirst();
    }

    public int getFloorNumber(){
        return floorNumber;
    }

    public List<ParkingSpot> getParkingSpots(){
        return parkingSpots;
    }

    public List<Integer> getAvailableSpots(VehicleType type){
        return parkingSpots.stream()
                .filter(spot -> spot.isAvailable() && spot.getVehicleType()==type)
                .map(ParkingSpot:: getSpotNumber)
                .toList();
    }
}

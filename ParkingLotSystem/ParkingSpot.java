import vehicle.Vehicle;
import vehicle.VehicleType;

public class ParkingSpot {
    private int spotNumber;
    private Vehicle vehicle;
    private VehicleType vehicleType;
    private boolean isAvailable;

    public ParkingSpot(int spotNumber, VehicleType type){
        this.spotNumber = spotNumber;
        this.vehicleType = type;
        this.isAvailable = true;
    }

    public void setVehicleType(VehicleType type){
        this.vehicleType = type;
    }

    public VehicleType getVehicleType(){
        return vehicleType;
    }

    public Vehicle getVehicle(){
        return vehicle;
    }

    public int getSpotNumber(){
        return spotNumber;
    }

    public void setAvailable(boolean val){
        this.isAvailable = val;
    }

    public boolean isAvailable(){
        return isAvailable;
    }

    public synchronized boolean park(Vehicle vehicle){
        if(!isAvailable || vehicle.getVehicleType()!=vehicleType) return false;
        this.vehicle = vehicle;
        this.isAvailable = false;
        return true;
    }

    public synchronized void unpark(){
        vehicle = null;
        isAvailable = true;
    }

}

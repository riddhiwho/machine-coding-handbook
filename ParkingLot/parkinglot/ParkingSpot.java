package parkinglot;

import parkinglot.vehicletype.Vehicle;
import parkinglot.vehicletype.VehicleType;

public class ParkingSpot {
    private int spotNumber;
    private VehicleType vehicleType;
    private Vehicle vehicle;
    private boolean isAvailable;

    public ParkingSpot(int spotNumber, VehicleType type){
        this.spotNumber=spotNumber;
        this.vehicleType=type;
        this.isAvailable=true;
    }

    public void setVehicleType(VehicleType vehicleType){
        this.vehicleType=vehicleType;
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
        return this.isAvailable;
    }

    // TODO: UNDERSTAND
    public synchronized boolean park(Vehicle vehicle){
        if(!isAvailable || vehicle.getType()!=vehicleType) return false;
        this.vehicle=vehicle;
        isAvailable=false;
        return true;
    }

    public synchronized void unpark(){
        vehicle=null;
        isAvailable=true;
    }
}

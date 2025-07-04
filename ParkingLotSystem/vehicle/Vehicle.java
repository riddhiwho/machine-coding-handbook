package vehicle;

public class Vehicle {
    private VehicleType vehicleType;
    private String licensePlate;

    public Vehicle(String licensePlate, VehicleType type){
        this.licensePlate = licensePlate;
        this.vehicleType = type;
    }

    public String getLicensePlate(){
        return licensePlate;
    }

    public VehicleType getVehicleType(){
        return vehicleType;
    }

    @Override
    public String toString(){
        return vehicleType + " (" + licensePlate + ")";
    }
    
}

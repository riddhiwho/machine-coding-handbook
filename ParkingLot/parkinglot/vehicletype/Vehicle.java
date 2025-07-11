package parkinglot.vehicletype;

public abstract class Vehicle {
    protected String licensePlate;
    protected VehicleType type;

    public Vehicle(String licensePlate, VehicleType type){
        this.licensePlate = licensePlate;
        this.type = type;
    }

    public String getLicensePlate(){
        return licensePlate;
    }

    public VehicleType getType(){
        return type;
    }

    @Override
    public String toString(){
        return type + " (" + licensePlate + ")";
    }
}

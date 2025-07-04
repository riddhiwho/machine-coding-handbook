package parkinglot;

import parkinglot.vehicletype.Vehicle;
import java.util.Date;

public class Ticket {
    private String ticketId;
    private Vehicle vehicle;
    private ParkingSpot parkingSpot;
    private long entryTime;
    private long exitTime;

    public Ticket(String ticketId, Vehicle vehicle, ParkingSpot parkingSpot){
        this.ticketId=ticketId;
        this.vehicle=vehicle;
        this.parkingSpot=parkingSpot;
        this.entryTime= new Date().getTime();
    }

    public String getTicketId(){
        return ticketId;
    }

    public Vehicle getVehicle(){
        return vehicle;
    }

    public ParkingSpot getParkingSpot(){
        return parkingSpot;
    }

    public long getEntryTimestamp(){
        return entryTime;
    }

    public long getExitTimestamp(){
        return exitTime;
    }

    public void setExitTimestamp(){
        this.exitTime = new Date().getTime();
    }
}

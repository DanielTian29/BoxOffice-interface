package API;

import java.util.ArrayList;
import java.util.List;

public class Venue {
    private int venueID;
    private boolean booked;
    private int capacity;
    private String name;
    private int eventID;
    public Venue(int venueID, String name, int capacity, Boolean booked, int eventID){
        this.venueID = venueID;
        this.name = name;
        this.capacity = capacity;
        this.booked = booked;
        this.eventID = eventID;
    }

    public int getVenueID() { return venueID; }
    public String getName() { return name; }
    public boolean getBooked() { return booked; }
    public int getCapacity() { return  capacity; }
}

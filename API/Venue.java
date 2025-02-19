package API;

import java.util.ArrayList;
import java.util.List;

public class Venue {
    private int venueID;
    private boolean booked;
    private int capacity;

    public Venue(int venueID, boolean booked, int capacity){
        this.venueID = venueID;
        this.booked = booked;
        this.capacity = capacity;
    }

    public int getVenueID() { return venueID; }
    public boolean getBooked() { return booked; }
    public int getCapacity() { return  capacity; }

}

package API;

public class Seat {
    private int seatId;
    private int venueId;
    private int guestID;
    private String seatNumber;
    private String row;
    private Boolean booked;
    private Guest guest;

    public Seat(int seatId, int venueId, int guestID, String seatNumber, String row, Boolean booked){
        this.seatId = seatId;
        this.venueId = venueId;
        this.guestID = guestID;
        this.seatNumber = seatNumber;
        this.row = row;
        this.booked = booked;
    }

    public int getSeatId() { return seatId; }
    public int getVenueId() { return venueId; }
    public int getGuestID() { return guestID; }
    public String getSeatNumber() { return seatNumber; }
    public String  getRow() { return row; }
    public Boolean getBooked() { return booked; }
    public Guest getGuest() { return guest; }
}

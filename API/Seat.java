package API;

public class Seat {
    private int seatId;
    private int venueId;
    private int guestID;
    private String seatNumber;
    private String row;
    private Boolean booked;
    private Guest guest;
    private boolean disabledSeating;
    private int price;

    public Seat(int seatId, int venueId, String seatNumber, String row, Boolean booked, boolean disabledSeating, int price){
        this.seatId = seatId;
        this.venueId = venueId;
        this.seatNumber = seatNumber;
        this.row = row;
        this.booked = booked;
        this.disabledSeating = disabledSeating;
        this.price = price;
    }

    public int getSeatId() { return seatId; }
    public int getVenueId() { return venueId; }
    public int getGuestID() { return guestID; }
    public String getSeatNumber() { return seatNumber; }
    public String  getRow() { return row; }
    public Boolean getBooked() { return booked; }
    public Guest getGuest() { return guest; }
    public boolean getIfDisabled() { return disabledSeating; }
    public int getPrice() { return price; }
}

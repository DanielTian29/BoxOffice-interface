package API;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select what you would like from the following" +
                "options:\n 1.Get venue \n 2.Get seating info for an event");

        String response = (scanner.nextLine()).toLowerCase();
        if (response == "1") {
            option1();
        }
        if (response == "2"){
            System.out.println("Please pick which venue you'd like to look at (typed as one word):\n Main Hall \n Small Hall");
            String reply = (scanner.nextLine()).toLowerCase();
            if (reply == "mainhall"){
                option2(getMainHallID());
            } else if (reply == "smallhall") {
                option2(getSmallHallID());
            }
        }
    }
    public static int getMainHallID() throws SQLException{
        DatabaseConnection connection = new DatabaseConnection();
        return connection.getSmallHallID();
    }
    public static int getSmallHallID() throws SQLException{
        DatabaseConnection connection = new DatabaseConnection();
        return connection.getMainHallID();
    }
    public static void option1() throws SQLException {
        DatabaseConnection connection = new DatabaseConnection();
        List<Venue> venues = connection.getListOfVenues();
        List<Event> events = connection.getListOfEvents();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM, D, yyyy, h:mm a");
        for (Venue venue : venues) {
            if (venue.getBooked() == true){
                System.out.println("Venue " + venue.getName() + "has been booked \n" +
                        "The following events take place: \n");
                for (Event event : events) {
                    if (event.getVenueId() == venue.getVenueID()) {
                        String startTimeFormatted = event.getStartTime().format(formatter);
                        String endTimeFormatted = event.getEndTime().format(formatter);
                        System.out.println("Event: " + event.getName() + " With the ID " +
                                event.getEventId() + " starts at: " + startTimeFormatted +
                                "and ends at: " + endTimeFormatted + "\n");
                    }
                }
            }else {
            System.out.println("Venue " + venue.getName() + "has not been booked \n");
            }
        }
        venues = null;
        events = null;
    }
    public static void option2(int eventID) throws SQLException{
        DatabaseConnection connection = new DatabaseConnection();
        List<Seat> seats = connection.getAvailableSeats(eventID);
        System.out.println("The following seats are free: ");
        for (Seat seat: seats) {
            System.out.println("Seat number: " + seat.getSeatNumber() + "\n Seat row: " + seat.getRow());
        }
    }
}

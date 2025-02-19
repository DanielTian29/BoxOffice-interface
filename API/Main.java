package API;

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
                        System.out.println("Event: " + event.getName() + " starts at: " + startTimeFormatted +
                                "and ends at: " + endTimeFormatted);
                    }
                }
            }else {
            System.out.println("Venue " + venue.getName() + "has not been booked");
            }
        }
    }
    public static void option2() throws SQLException{
        DatabaseConnection connection = new DatabaseConnection();
        List<Venue> venues = connection.getListOfVenues();
        List<Event> events = connection.getListOfEvents();
    }
}

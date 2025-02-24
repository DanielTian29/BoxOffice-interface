package API;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.List;

public class Main implements MainInterface {
    //main function, allows you to interact with the interface
    @Override
    public void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select what you would like from the following " +
                "options:\n 1.Daily Report \n 2.Get venue information\n" +
                " 3.Get seating info for an event \n 4.Adjust the price of a seat\n" +
                " 5.Get seat arrangement.");
        String response = scanner.nextLine();
        while (!response.equals("stop")) {

            if (response.equals("1")) {
                option1();
            }

            if (response.equals("2")) {
                option2();
            }
            if (response.equals("3")) {
                System.out.println("Please pick which venue you'd like to look at (typed as one word):\n Main Hall \n Small Hall");
                String reply = (scanner.nextLine()).toLowerCase();
                if (reply == "mainhall") {
                    option3(getMainHallID());
                } else if (reply == "smallhall") {
                    option3(getSmallHallID());
                }
            }
            if (response.equals("4")) {
                System.out.println("Enter the price you'd like to sell the seat for \n");
                double d = scanner.nextDouble();
                System.out.println("Enter the seat number you'd like to change the price for \n");
                int i = scanner.nextInt();
                setPriceOfSeat(d,i);
                }
            if (response.equals("5")) {
                System.out.println("Please pick which event you'd like to look at (type the ID of the event");
                int reply = scanner.nextInt();
                System.out.println("Here is the seat arrangement for your event: \n");
                option4(reply);
                }
            System.out.println("Please select what you would like from the following " +
                    "options:\n 1.Daily Report \n 2.Get venue information \n 3.Get seating info for an event \n 4.Get seat pricing");
            response = scanner.nextLine();
            }
        }
    //gets the ID for the main hall
    @Override
    public int getMainHallID() throws SQLException{
        DatabaseConnection connection = new DatabaseConnection();
        return connection.getSmallHallID();
    }
    //gets the ID for the small hall
    @Override
    public int getSmallHallID() throws SQLException{
        DatabaseConnection connection = new DatabaseConnection();
        return connection.getMainHallID();
    }
    //gets the daily report
    @Override
    public void option1() throws SQLException{
        System.out.println("Here's the Daily report: ");
        DatabaseConnection connection = new DatabaseConnection();
        connection.getTodayEventsWithAvailableSeating();
    }
    //gets all the information on venues, what's booked for when
    @Override
    public void option2() throws SQLException {
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
    //gets all the available seats $ price for the event you're looking for
    @Override
    public void option3(int eventID) throws SQLException{
        DatabaseConnection connection = new DatabaseConnection();
        List<Seat> seats = connection.getAvailableSeats(eventID);
        System.out.println("The following seats are free: ");
        for (Seat seat: seats) {
            if (seat.getBooked() == false) {
                if (seat.getRestricted() == true) {
                    System.out.println("This seat has a restricted view, the price has been automatically adjusted, please notify the customer \n");
                }
                System.out.println("Seat number: " + seat.getSeatNumber() + "\n Seat row: " + seat.getRow() + " \n Seat price: " + seat.getPrice());
            }
        }
        seats = null;
    }
    //get the entire seating arrangement for an event
    @Override
    public void option4(int eventID) throws SQLException {
        DatabaseConnection connection = new DatabaseConnection();
        List<Seat> seats = connection.getAvailableSeats(eventID);
        System.out.println("This is the following seating arrangement: ");
        for (Seat seat: seats) {
            if (seat.getRestricted() == true) {
                System.out.println("This seat has a restricted view, the price has been automatically adjusted, please notify the customer. \n");
            }
            if (seat.getIfDisabled() == true) {
                System.out.println("This seat is disabled seating, please only allow the customers who need this support to book the seat. \n");
            }
            System.out.println("Seat number: " + seat.getSeatNumber() + "\n Seat row: " + seat.getRow() +" \n Seat price: " + seat.getPrice());
        }
        seats = null;
    }
    //adjust the price of a seat
    @Override
    public void setPriceOfSeat(double price, int seat_number) throws SQLException{
        DatabaseConnection connection = new DatabaseConnection();
        connection.setSeatPrice(price, seat_number);
    }

}
package API;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DatabaseConnectionInterface {
    //way to connect to the database
    Connection connectToDatabase() throws SQLException;

    //access the database to get all info on venues
    List<Venue> getListOfVenues() throws SQLException;

    // get all data on events from the database
    List<Event> getListOfEvents() throws SQLException;

    //get all available seats for an event
    List<Seat> getAvailableSeats(int eventId) throws SQLException;

    //get all the available seats and price
    void getTodayEventsWithAvailableSeating() throws SQLException;

    //get the main hall ID
    int getMainHallID() throws SQLException;

    //get the small hall ID
    int getSmallHallID() throws SQLException;

    //update the price of a seat
    void setSeatPrice(double newPrice, int venueId) throws SQLException;
}

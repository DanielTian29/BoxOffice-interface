package API;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    //way to connect to the database
    public Connection connectToDatabase() throws SQLException {
        String url = "jdbc:mysql://sst-stuproj.city.ac.uk:3306/in2033t08";
        String user = "";
        String password = "";
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
            return null;
        }
    }
    //access the database to get all info on venues
    public List<Venue> getListOfVenues() throws SQLException {
        List<Venue> venues = new ArrayList<>();
        String sql = "SELECT * FROM Venues";
        try (Connection conn = connectToDatabase();
             PreparedStatement p = conn.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {

            while (rs.next()) {
                Venue venue = new Venue(
                        rs.getInt("venue_id"),
                        rs.getString("name"),
                        rs.getBoolean("booked"),
                        rs.getInt("capacity")
                );
                venues.add(venue);
            }
        } catch (SQLException e) {
            System.err.println("Error in fetching venues: " + e.getMessage());
            e.printStackTrace();
        }
        return venues;
    }
    // get all data on events from the database
    public List<Event> getListOfEvents() throws SQLException {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM Events WHERE start_time >= NOW() ORDER BY Start_time ASC";
        try (Connection conn = connectToDatabase();
             PreparedStatement p = conn.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {

            while (rs.next()) {
                Event event = new Event(
                        rs.getInt("event_id"),
                        rs.getInt("venue_id"),
                        rs.getInt("client_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getTimestamp("start_time").toLocalDateTime(),
                        rs.getTimestamp("end_time").toLocalDateTime(),
                        rs.getInt("length")
                );
                events.add(event);
            }
        } catch (SQLException e) {
            System.err.println("Error in fetching events: " + e.getMessage());
            e.printStackTrace();
            throw e;  // Rethrowing exception to handle it further up if necessary
        }
        return events;
    }
    //get all available seats for an event
    public List<Seat> getAvailableSeats(int eventId) throws SQLException {
        List<Seat> availableSeats = new ArrayList<>();
        String sql = "SELECT s.* FROM Seats s JOIN Events e ON s.venue_id = e.venue_id " +
                "WHERE e.event_id = ? ORDER BY s.row, s.seat_number ASC";

        try (Connection conn = connectToDatabase();
             PreparedStatement p = conn.prepareStatement(sql)) {

            p.setInt(1, eventId);
            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    Seat seat = new Seat(
                            rs.getInt("seat_id"),
                            rs.getInt("venue_id"),
                            rs.getString("seat_number"),
                            rs.getString("row"),
                            rs.getBoolean("booked"),
                            rs.getBoolean("disabledSeating"),
                            rs.getInt("price"),
                            rs.getBoolean("restricted")
                    );
                    availableSeats.add(seat);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in fetching available seats: " + e.getMessage());
            e.printStackTrace();
            throw e;  // Rethrowing exception to handle it further up if necessary
        }
        return availableSeats;
    }
    //get all the available seats and price
    public void getTodayEventsWithAvailableSeating() throws SQLException {
        String eventsSql = "SELECT id, name, start_time, end_time FROM Venues WHERE start_time >= CURRENT_DATE AND start_time < CURRENT_DATE + INTERVAL '1' DAY";

        try (Connection conn = connectToDatabase();
             PreparedStatement eventsStmt = conn.prepareStatement(eventsSql);
             ResultSet eventsRs = eventsStmt.executeQuery()) {

            if (!eventsRs.isBeforeFirst()) {
                System.out.println("No events found for today.");
            } else {
                while (eventsRs.next()) {
                    // Print event information
                    int eventId = eventsRs.getInt("id");
                    String name = eventsRs.getString("name");
                    Time startTime = eventsRs.getTime("start_time");
                    Time endTime = eventsRs.getTime("end_time");

                    System.out.printf("\nEvent:" + name + "\nStart Time:" + startTime + "\nEnd Time: " + endTime);

                    // Query to get available seats and their prices for the current event
                    String seatsSql = "SELECT seat_number, row, price FROM Seats WHERE venue_id = ? AND booked = false";
                    try (PreparedStatement seatsStmt = conn.prepareStatement(seatsSql)) {
                        seatsStmt.setInt(1, eventId);
                        try (ResultSet seatsRs = seatsStmt.executeQuery()) {
                            if (!seatsRs.isBeforeFirst()) {
                                System.out.println("No available seats for this event.");
                            } else {
                                System.out.println("Available Seats:");
                                while (seatsRs.next()) {
                                    String seatNumber = seatsRs.getString("seat_number");
                                    String row = seatsRs.getString("row");
                                    double price = seatsRs.getDouble("price");
                                    System.out.printf("Seat Number: " + seatNumber + ", Row: " + row + ", Price: £" + price);
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Database access error:");
            e.printStackTrace();
        }
    }
    //get the main hall ID
    public int getMainHallID() throws SQLException {
        int mainHallID = -1;
        String sql = "SELECT venue_ID FROM Venues WHERE name = 'mainhall'";

        try (Connection conn = connectToDatabase();
             PreparedStatement p = conn.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {
            if (rs.next()) {
                mainHallID = rs.getInt("venue_ID");
            } else {
                System.out.println("Main Hall not found in database");
            }
        } catch (SQLException e) {
            System.err.println("Error in fetching the main hall ID: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return mainHallID;
    }
    //get the small hall ID
    public int getSmallHallID() throws SQLException {
        int smallHallID = -1;
        String sql = "SELECT venue_ID FROM Venues WHERE name = 'smallhall'";

        try (Connection conn = connectToDatabase();
             PreparedStatement p = conn.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {
            if (rs.next()) {
                smallHallID = rs.getInt("venue_ID");
            } else {
                System.out.println("Small Hall not found in database");
            }
        } catch (SQLException e) {
            System.err.println("Error in fetching the small hall ID: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return smallHallID;
    }
    //update the price of a seat
    public void setSeatPrice(double newPrice, int venueId) throws SQLException {
        String sql = "UPDATE Seats SET price = ? WHERE venue_id = ?";
        try (Connection conn = connectToDatabase();
             PreparedStatement p = conn.prepareStatement(sql)) {
            p.setDouble(1, newPrice); // Set the new price
            p.setInt(2, venueId); // Set the venue ID
            int affectedRows = p.executeUpdate(); // Execute the update

            if (affectedRows > 0) {
                System.out.println("Price updated successfully!");
            } else {
                System.out.println("Price update failed: no rows affected.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating price: " + e.getMessage());
            throw e;
        }
    }

}
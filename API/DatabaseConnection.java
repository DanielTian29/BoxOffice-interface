package API;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    public Connection connectToDatabase() throws SQLException {
        String url = "jdbc:mysql://sst-stuproj.city.ac.uk:3306/in2033t08"; // Update with your actual database name
        String user = "in2033t08_a"; // Update with your MySQL username
        String password = "1rHVxHi7gR8"; // Update with your MySQL password
        return DriverManager.getConnection(url, user, password);
        }
    public List<Venue> getListOfVenues() throws SQLException{
        List<Venue> venues = new ArrayList<>();
        String sql = "SELECT * FROM Venues";
        try(Connection conn = connectToDatabase();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()){
                Venue venue = new Venue(
                        rs.getInt("venue_id"),
                        rs.getString("name"),
                        rs.getBoolean("booked"),
                        rs.getInt("capacity")
                );
                venues.add(venue);
            }
        }catch (SQLException e) {
            System.err.println("Error in fetching venues: " + e.getMessage());
            e.printStackTrace();
        }
        return venues;
    }


    public List<Event> getListOfEvents() throws SQLException {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM Events WHERE start_time >= NOW() ORDER BY Start_time ASC";
        try (Connection conn = connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Event event = new Event(
                        rs.getInt("event_id"),
                        rs.getInt("venue_id"),
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
    public List<Seat> getAvailableSeats(int eventId) throws SQLException {
        List<Seat> availableSeats = new ArrayList<>();
        String sql = "SELECT s.* FROM Seats s JOIN Events e ON s.venue_id = e.venue_id " +
                "WHERE e.event_id = ? AND s.booked = FALSE ORDER BY s.row, s.seat_number ASC";

        try (Connection conn = connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, eventId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Seat seat = new Seat(
                            rs.getInt("seat_id"),
                            rs.getInt("venue_id"),
                            rs.getString("seat_number"),
                            rs.getString("row"),
                            rs.getBoolean("booked")
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
    public int getMainHallID() throws SQLException{
        int mainHallID = -1;
        String sql = "SELECT venue_ID FROM Venues WHERE name = 'mainhall'";

        try(Connection conn = connectToDatabase();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()){
                mainHallID = rs.getInt("venue_ID");
            }else {
                System.out.println("Main Hall not found in database");
            }
        }catch (SQLException e){
            System.err.println("Error in fetching the main hall ID: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return mainHallID;
    }
    public int getSmallHallID() throws SQLException{
        int smallHallID = -1;
        String sql = "SELECT venue_ID FROM Venues WHERE name = 'smallhall'";

        try(Connection conn = connectToDatabase();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()){
                smallHallID = rs.getInt("venue_ID");
            }else {
                System.out.println("Small Hall not found in database");
            }
        }catch (SQLException e){
            System.err.println("Error in fetching the small hall ID: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return smallHallID;
    }
}



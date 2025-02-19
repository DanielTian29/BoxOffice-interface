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
}

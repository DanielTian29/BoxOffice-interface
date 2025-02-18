package API;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public void connectToDatabase() {
        String url = "jdbc:mysql://sst-stuproj.city.ac.uk:3306/in2033t08"; // Update with your actual database name
        String user = "in2033t08_a"; // Update with your MySQL username
        String password = "1rHVxHi7gR8"; // Update with your MySQL password

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the database successfully");
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }
}

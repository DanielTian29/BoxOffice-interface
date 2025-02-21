package API;

import java.sql.SQLException;

public interface MainInterface {
    //main function, allows you to interact with the interface
    void main(String[] args) throws SQLException;

    //gets the ID for the main hall
    int getMainHallID() throws SQLException;

    //gets the ID for the small hall
    int getSmallHallID() throws SQLException;

    //gets the daily report
    void option1() throws SQLException;

    //gets all the information on venues, what's booked for when
    void option2() throws SQLException;

    //gets all the available seats $ price for the event you're looking for
    void option3(int eventID) throws SQLException;

    //get the entire seating arrangement for an event
    void option4(int eventID) throws SQLException;

    //adjust the price of a seat
    void setPriceOfSeat(double price, int seat_number) throws SQLException;
}

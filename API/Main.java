package API;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        DatabaseConnection dbconnect = new DatabaseConnection();
        //dbconnect.connectToDatabase();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select what you would like from the following" +
                "options:\n 1.Blow up the world \n 2.Eat Sushi");

        String response = (scanner.nextLine()).toLowerCase();
        System.out.println(response);
    }
}

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class SQLInjectionExample {

    public static void main(String[] args) {
        try {
            // Verbindungsdetails zur Datenbank
            String url;
            String username;
            String password;
            
            // Verbindung zur Datenbank herstellen
            Connection connection = DriverManager.getConnection(url, username, password);

            // Benutzereingaben lesen
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your username: ");
            String userInput = scanner.nextLine();
            System.out.print("Enter your password: ");
            String userPassword = scanner.nextLine();
            
            // **Unsichere SQL-Abfrage ohne Prepared Statement**
            String query = "SELECT * FROM users WHERE username = '" + userInput + "' AND password = '" + userPassword + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {
                System.out.println("User: " + resultSet.getString("username"));
            }

            scanner.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

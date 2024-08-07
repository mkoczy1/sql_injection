import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class SQLInjectionPrevention {

    public static void main(String[] args) {
        try {
            // Verbindungsdetails zur Datenbank
            String url = "jdbc:mysql://localhost:3306/demo";
            String username = "root";
            String password = "password";
            
            // Verbindung zur Datenbank herstellen
            Connection connection = DriverManager.getConnection(url, username, password);

            // Benutzereingaben lesen
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your username: ");
            String userInput = scanner.nextLine();
            System.out.print("Enter your password: ");
            String userPassword = scanner.nextLine();
            
            // **Sichere SQL-Abfrage mit Prepared Statement**
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userInput);
            preparedStatement.setString(2, userPassword);
            ResultSet resultSet = preparedStatement.executeQuery();
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

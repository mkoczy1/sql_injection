import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class SQLInjectionDemo {

    public static void main(String[] args) {
        try {
            // Verbindungsdetails zur Datenbank
            String url = "jdbc:mysql://localhost:3306/demo";
            String username = "root";
            String password = "password";
            
            // Verbindung zur Datenbank herstellen
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

            // Benutzereingaben lesen
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your username: ");
            String userInput = scanner.nextLine();
            System.out.print("Enter your password: ");
            String userPassword = scanner.nextLine();

            
            // **Anfällige SQL-Injektion (Einfache Injektion)**
            String query1 = "SELECT * FROM users WHERE username = '" + userInput + "' AND password = '" + userPassword + "'";
            ResultSet resultSet1 = statement.executeQuery(query1);
            while (resultSet1.next()) {
                System.out.println("User: " + resultSet1.getString("username"));
            }

            // **Anfällige SQL-Injektion (Union-basierte Injektion)**
            System.out.print("Enter your search term: ");
            String searchTerm = scanner.nextLine();
            String query2 = "SELECT * FROM products WHERE name = '" + searchTerm + "'";
            ResultSet resultSet2 = statement.executeQuery(query2);
            while (resultSet2.next()) {
                System.out.println("Product: " + resultSet2.getString("name"));
            }

            // **Anfällige SQL-Injektion (Blind Injektion)**
            System.out.print("Enter your email: ");
            String userEmail = scanner.nextLine();
            String query3 = "SELECT * FROM users WHERE email = '" + userEmail + "'";
            ResultSet resultSet3 = statement.executeQuery(query3);
            if (resultSet3.next()) {
                System.out.println("Email found");
            } else {
                System.out.println("Email not found");
            }

            // **Anfällige SQL-Injektion (Stored Procedure Injektion)**
            System.out.print("Enter the user ID: ");
            String userId = scanner.nextLine();
            String query4 = "{ call getUserInfo('" + userId + "') }";
            ResultSet resultSet4 = statement.executeQuery(query4);
            while (resultSet4.next()) {
                System.out.println("User Info: " + resultSet4.getString("info"));
            }

            // **Anfällige SQL-Injektion (Order By Injektion)**
            System.out.print("Enter the column to sort by: ");
            String sortBy = scanner.nextLine();
            String query5 = "SELECT * FROM users ORDER BY " + sortBy;
            ResultSet resultSet5 = statement.executeQuery(query5);
            while (resultSet5.next()) {
                System.out.println("User: " + resultSet5.getString("username"));
            }

            // **Anfällige SQL-Injektion (Kein static query)**
            System.out.print("Enter the user ID for a dynamic query: ");
            String dynamicUserId = scanner.nextLine();
            String query6 = "SELECT * FROM users WHERE id = " + dynamicUserId;
            ResultSet resultSet6 = statement.executeQuery(query6);
            while (resultSet6.next()) {
                System.out.println("User: " + resultSet6.getString("username"));
            }

            scanner.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

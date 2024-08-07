import javax.sql.DataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class SQLInjectionPrevention {

    private static DataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/demo");
        config.setUsername(System.getenv("DB_USERNAME")); // Laden aus Umgebungsvariablen
        config.setPassword(System.getenv("DB_PASSWORD")); // Laden aus Umgebungsvariablen
        dataSource = new HikariDataSource(config);
    }

    public static void main(String[] args) {
        try (Connection connection = dataSource.getConnection()) {
            // Benutzereingaben lesen
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your username: ");
            String userInput = scanner.nextLine();
            System.out.print("Enter your password: ");
            String userPassword = scanner.nextLine();
            
            // Sichere SQL-Abfrage mit Prepared Statement
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, userInput);
                preparedStatement.setString(2, userPassword);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        System.out.println("User: " + resultSet.getString("username"));
                    }
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

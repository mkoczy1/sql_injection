#include <iostream>
#include <mysql/mysql.h>
#include <string>

int main() {
    // Benutzername und Passwort direkt aus Benutzereingaben (z.B. aus der Konsole)
    std::string username;
    std::string password;

    std::cout << "Username: ";
    std::getline(std::cin, username);
    std::cout << "Password: ";
    std::getline(std::cin, password);

    // Verbindung zur Datenbank herstellen
    MYSQL *conn;
    MYSQL_RES *res;
    MYSQL_ROW row;

    conn = mysql_init(NULL);
    if (conn == NULL) {
        std::cerr << "mysql_init() fehlgeschlagen\n";
        return EXIT_FAILURE;
    }

    if (mysql_real_connect(conn, "localhost", "root", "", "example_database", 0, NULL, 0) == NULL) {
        std::cerr << "Fehler bei mysql_real_connect() " << mysql_error(conn) << "\n";
        mysql_close(conn);
        return EXIT_FAILURE;
    }

    // Unsichere SQL-Abfrage, die anfällig für SQL-Injection ist
    std::string query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
    
    if (mysql_query(conn, query.c_str())) {
        std::cerr << "Fehler bei mysql_query() " << mysql_error(conn) << "\n";
        mysql_close(conn);
        return EXIT_FAILURE;
    }

    res = mysql_store_result(conn);
    if (res == NULL) {
        std::cerr << "Fehler bei mysql_store_result() " << mysql_error(conn) << "\n";
        mysql_close(conn);
        return EXIT_FAILURE;
    }

    if (mysql_num_rows(res) > 0) {
        // Benutzer gefunden
        std::cout << "Login erfolgreich!\n";
    } else {
        // Benutzer nicht gefunden
        std::cout << "Ungültiger Benutzername oder Passwort.\n";
    }

    // Ergebnisse freigeben und Verbindung schließen
    mysql_free_result(res);
    mysql_close(conn);

    return EXIT_SUCCESS;
}

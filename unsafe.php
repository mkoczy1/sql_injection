<?php
// Unsicherer Code ohne parametrisierte Abfragen, Prepared Statements oder Eingabevalidierung

// Benutzername und Passwort direkt aus Benutzereingaben (z.B. aus einem Formular)
$username = $_POST['username'];
$password = $_POST['password'];

// Verbindung zur Datenbank herstellen
$conn = new mysqli("localhost", "root", "", "example_database");

// Überprüfen, ob die Verbindung erfolgreich war
if ($conn->connect_error) {
    die("Verbindung fehlgeschlagen: " . $conn->connect_error);
}

// Unsichere SQL-Abfrage, die anfällig für SQL-Injection ist
$sql = "SELECT * FROM users WHERE username = '$username' AND password = '$password'";

// Abfrage ausführen
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // Benutzer gefunden
    echo "Login erfolgreich!";
} else {
    // Benutzer nicht gefunden
    echo "Ungültiger Benutzername oder Passwort.";
}

// Verbindung schließen
$conn->close();
?>

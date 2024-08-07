<?php
// Sichere Programmierung mit parametrierten Abfragen, Prepared Statements und Eingabevalidierung

// Verbindung zur Datenbank herstellen
$conn = new mysqli("localhost", "root", "", "example_database");

// Überprüfen, ob die Verbindung erfolgreich war
if ($conn->connect_error) {
    die("Verbindung fehlgeschlagen: " . $conn->connect_error);
}

// Benutzername und Passwort aus Benutzereingaben (z.B. aus einem Formular)
$username = $_POST['username'];
$password = $_POST['password'];

// Eingabevalidierung: Überprüfen und bereinigen der Benutzereingaben
$username = $conn->real_escape_string($username);
$password = $conn->real_escape_string($password);

// Prepared Statement erstellen
$stmt = $conn->prepare("SELECT * FROM users WHERE username = ? AND password = ?");

// Parameter binden
$stmt->bind_param("ss", $username, $password);

// Abfrage ausführen
$stmt->execute();

// Ergebnis erhalten
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    // Benutzer gefunden
    echo "Login erfolgreich!";
} else {
    // Benutzer nicht gefunden
    echo "Ungültiger Benutzername oder Passwort.";
}

// Verbindung schließen
$stmt->close();
$conn->close();
?>

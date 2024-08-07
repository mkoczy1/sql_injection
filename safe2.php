<?php
session_start();
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    if (!isset($_POST['csrf_token']) || $_POST['csrf_token'] !== $_SESSION['csrf_token']) {
        exit("Ungültiges Token.");
    }
    if (isset($_POST['username']) && isset($_POST['password'])) {
        $conn = new mysqli("localhost", "root", "", "example_database");
        if ($conn->connect_error) {
            error_log("Verbindung fehlgeschlagen: " . $conn->connect_error);
            exit("Es gab ein Problem mit der Datenbankverbindung.");
        }
        $username = filter_var($conn->real_escape_string($_POST['username']), FILTER_SANITIZE_STRING);
        $password = filter_var($conn->real_escape_string($_POST['password']), FILTER_SANITIZE_STRING);
        $stmt = $conn->prepare("SELECT password FROM users WHERE username = ?");
        $stmt->bind_param("s", $username);
        $stmt->execute();
        $result = $stmt->get_result();
        if ($result->num_rows > 0) {
            $row = $result->fetch_assoc();
            $hashed_password = $row['password'];
            if (password_verify($password, $hashed_password)) {
                echo htmlspecialchars("Login erfolgreich!");
            } else {
                echo htmlspecialchars("Ungültiger Benutzername oder Passwort.");
            }
        } else {
            echo htmlspecialchars("Ungültiger Benutzername oder Passwort.");
        }
        $stmt->close();
        $conn->close();
    } else {
        exit("Benutzereingaben fehlen.");
    }
}
// Token generieren
$_SESSION['csrf_token'] = bin2hex(random_bytes(32));
?>

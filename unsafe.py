import mysql.connector

# Benutzername und Passwort direkt aus Benutzereingaben (z.B. aus einem Formular)
username = input('Username: ')
password = input('Password: ')

# Verbindung zur Datenbank herstellen
conn = mysql.connector.connect(
    host="localhost",
    user="root",
    password="",
    database="example_database"
)

# Verbindung prüfen
if conn.is_connected() == False:
    print("Verbindung fehlgeschlagen!")
    exit()

# Unsichere SQL-Abfrage, die anfällig für SQL-Injection ist
cursor = conn.cursor()
sql = f"SELECT * FROM users WHERE username = '{username}' AND password = '{password}'"

# Abfrage ausführen
cursor.execute(sql)
result = cursor.fetchall()

if len(result) > 0:
    # Benutzer gefunden
    print("Login erfolgreich!")
else:
    # Benutzer nicht gefunden
    print("Ungültiger Benutzername oder Passwort.")

# Verbindung schließen
conn.close()

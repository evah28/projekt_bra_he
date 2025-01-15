import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Patient {

    private int id;
    private String vorname;
    private String nachname;
    private String geburtsdatum;
    private String diagnose;

    private static final String URL = "jdbc:mysql://localhost:3306/project_db";
    private static final String USER1 = "root";
    private static final String PASSWORD1 = "FHmagdalena0504?";

    private static final String USER2 = "root";
    private static final String PASSWORD2 = "Tm20!Ka89#MaJO";


    //Konstruktor
    public Patient(int id, String vorname, String nachname, String geburtsdatum, String diagnose) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.geburtsdatum = geburtsdatum;
        this.diagnose = diagnose;
    }


    //Methode DB Verbindung
    public static Connection dbVerbindung() {

        Connection connection = null;

        try {
            // Zuerst versuchen, mit dem ersten Benutzer eine Verbindung aufzubauen
            connection = DriverManager.getConnection(URL, USER1, PASSWORD1);
         //   System.out.println("Verbindung mit Benutzer 1 erfolgreich!");
        } catch (SQLException e1) {
           // System.out.println("Verbindung mit Benutzer 1 fehlgeschlagen, versuche Benutzer 2...");

            try {
                // Wenn Benutzer1 fehlschlägt, versuche es mit Benutzer2
                connection = DriverManager.getConnection(URL, USER2, PASSWORD2);
              //  System.out.println("Verbindung mit Benutzer 2 erfolgreich!");
            } catch (SQLException e2) {
             //   System.out.println("Verbindung mit beiden Benutzern fehlgeschlagen.");
                e2.printStackTrace();
            }
        }
        return connection;


                /*DB Verbindung schließen
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(String geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", geburtsdatum='" + geburtsdatum + '\'' +
                ", diagnose='" + diagnose + '\'' +
                '}';
    }
}

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Patient {

    private int SVNR;
    private String Vorname;
    private String Nachname;
    private String Geburtsdatum;
    private String Straße;
    private String Hausnummer;
    private String PLZ;
    private String Ort;
    private String Diagnose;

    private static final String URL = "jdbc:mysql://localhost:3306/project_db";
    private static final String USER1 = "root";
    private static final String PASSWORD1 = "FHmagdalena0504?";

    private static final String USER2 = "root";
    private static final String PASSWORD2 = "Tm20!Ka89#MaJO";


    //Konstruktor
    public Patient(int SVNR, String Vorname, String Nachname, String Geburtsdatum, String Straße, String Hausnummer, String PLZ, String Ort, String Diagnose) {
        this.SVNR = SVNR;
        this.Vorname = Vorname;
        this.Nachname = Nachname;
        this.Geburtsdatum = Geburtsdatum;
        this.Straße = Straße;
        this.Hausnummer = Hausnummer;
        this.PLZ = PLZ;
        this.Ort = Ort;
        this.Diagnose = Diagnose;
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

    public static boolean patientEinfuegen(int SVNR, String Vorname, String Nachname, String Geburtsdatum, String Straße, String Hausnummer, String PLZ, String Ort, String Diagnose) {



            String query = "INSERT INTO patients (SVNR, Vorname, Nachname, Geburtsdatum, Straße, Hausnummer, PLZ, Ort, Diagnose) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";

            Connection connection = dbVerbindung(); //Verbindung zur Datenbank herstellen
        if (connection == null) {
            System.out.println("Keine Verbindung zur Datenbank!");
            return false;
        }

            try (PreparedStatement prstmt = connection.prepareStatement(query)) {
                prstmt.setInt(1, SVNR);
                prstmt.setString(2, Vorname);
                prstmt.setString(3, Nachname);
                prstmt.setString(4, Geburtsdatum);
                prstmt.setString(5, Straße);
                prstmt.setString(6, Hausnummer);
                prstmt.setString(7, PLZ);
                prstmt.setString(8, Ort);
                prstmt.setString(9, Diagnose);

                int rowsInserted;
                rowsInserted = prstmt.executeUpdate();
                return rowsInserted > 0; //gibt true zurück, wenn ein Patient eingefügt wurde
            } catch (SQLException e) {
                System.out.println("Fehler beim Einfügen des Patienten: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

    public int getSVNR() {
        return SVNR;
    }
    public void setSVNR(int SVNR) {
        this.SVNR = SVNR;
    }

    public String getVorname() {
        return Vorname;
    }
    public void setVorname(String Vorname) {
        this.Vorname = Vorname;
    }

    public String getNachname() {
        return Nachname;
    }
    public void setNachname(String Nachname) {
        this.Nachname = Nachname;
    }

    public String getGeburtsdatum() {
        return Geburtsdatum;
    }
    public void setGeburtsdatum(String Geburtsdatum) {
        this.Geburtsdatum = Geburtsdatum;
    }

    public String getDiagnose() {
        return Diagnose;
    }
    public void setDiagnose(String Diagnose) {
        this.Diagnose = Diagnose;
    }

    public String getStraße() {return Straße;}
    public void setStraße(String Straße) {this.Straße = Straße;}

    public String getHausnummer() {
        return Hausnummer;
    }

    public void setHausnummer(String hausnummer) {
        Hausnummer = hausnummer;
    }

    public String getPLZ() {
        return PLZ;
    }

    public void setPLZ(String PLZ) {
        this.PLZ = PLZ;
    }

    public String getOrt() {
        return Ort;
    }

    public void setOrt(String ort) {
        Ort = ort;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + SVNR +
                ", vorname='" + Vorname + '\'' +
                ", nachname='" + Nachname + '\'' +
                ", geburtsdatum='" + Geburtsdatum + '\'' +
                ", diagnose='" + Diagnose + '\'' +
                '}';
    }
}

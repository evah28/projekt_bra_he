import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Patient {

    private long SVNR;
    private String Vorname;
    private String Nachname;
    private String Geburtsdatum;
    private String Straße;
    private String Hausnummer;
    private String PLZ;
    private String Ort;
    private String Diagnose;

    private static final String URL = "jdbc:mysql://10.25.2.145:3306/23brabec";
    private static final String USER1 = "23brabec";
    private static final String PASSWORD1 = "geb23";

    /**
     * Konstruktor für die Erstellung eines Patientenobjekts.
     * @param SVNR Die Sozialversicherungsnummer des Patienten.
     * @param Vorname Der Vorname des Patienten.
     * @param Nachname Der Nachname des Patienten.
     * @param Geburtsdatum Das Geburtsdatum des Patienten.
     * @param Straße Die Straße, in der der Patient lebt.
     * @param Hausnummer Die Hausnummer des Patienten.
     * @param PLZ Die Postleitzahl des Wohnorts des Patienten.
     * @param Ort Der Ort des Patienten.
     * @param Diagnose Die Diagnose des Patienten.
     */

    //Konstruktor
    public Patient(long SVNR, String Vorname, String Nachname, String Geburtsdatum, String Straße, String Hausnummer, String PLZ, String Ort, String Diagnose) {
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

    /**
     * Stellt eine Verbindung zur Datenbank her.
     * @return Eine Connection zur Datenbank, falls die Verbindung erfolgreich ist, ansonsten null.
     */

    //Methode DB Verbindung
    public static Connection dbVerbindung() {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER1, PASSWORD1);
        } catch (SQLException e1) {
        }
        return connection;
    }

    /**
     * Fügt einen neuen Patienten in der Datenbank hinzu.
     * @param SVNR Die Sozialversicherungsnummer des Patienten.
     * @param Vorname Der Vorname des Patienten.
     * @param Nachname Der Nachname des Patienten.
     * @param Geburtsdatum Das Geburtsdatum des Patienten.
     * @param Straße Die Straße des Patienten.
     * @param Hausnummer Die Hausnummer des Patienten.
     * @param PLZ Die Postleitzahl des Patienten.
     * @param Ort Der Ort des Patienten.
     * @param Diagnose Die Diagnose des Patienten.
     * @param eingabeGender Das Geschlecht des Patienten.
     * @param eingabeNationality Die Nationalität des Patienten.
     * @param eingabeInsurance Die Versicherung des Patienten.
     * @return true, wenn der Patient erfolgreich hinzugefügt wurde, andernfalls false.
     */

    public static boolean patientEinfuegen(long SVNR, String Vorname, String Nachname, String Geburtsdatum, String Straße, String Hausnummer, String PLZ, String Ort, String Diagnose, String eingabeGender, String eingabeNationality, String eingabeInsurance) {

        String query = "INSERT INTO patients (SVNR, Vorname, Nachname, Geburtsdatum, Straße, Hausnummer, PLZ, Ort, Diagnose) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";

            Connection connection = dbVerbindung(); //Verbindung zur Datenbank herstellen
        if (connection == null) {
            System.out.println("Keine Verbindung zur Datenbank!");
            return false;
        }

        //Parameter für das Prepared Statement setzen
            try (PreparedStatement prstmt = connection.prepareStatement(query)) {
                prstmt.setLong(1, SVNR);
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


    // Getter- und Setter-Methoden für die Attribute des Patienten

    public long getSVNR() {
        return SVNR;
    }
    public void setSVNR(long SVNR) {
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


    /**
     * Gibt eine stringbasierte Darstellung des Patienten zurück.
     * @return String Darstellung des Patienten.
     */

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

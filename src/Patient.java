public class Patient {

    private int id;
    private String vorname;
    private String nachname;
    private String geburtsdatum;
    private String diagnose;

    //Konstruktor
    public Patient(int id, String vorname, String nachname, String geburtsdatum, String diagnose) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.geburtsdatum = geburtsdatum;
        this.diagnose = diagnose;
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

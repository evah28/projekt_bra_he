import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Diese Klasse stellt die GUI zum Einfügen eines neuen Patienten in die Datenbank bereit.
 */

public class GUIPatientEinfuegen extends JFrame {

    private GUIMenu GUIMenu; //Referenz zur MenüGUI

    private JPanel contentPaneEinfuegen;
    private JTextField textFieldSVNR;
    private JTextField textFieldVorname;
    private JTextField textFieldNachname;
    private JTextField textFieldGeburtsdatum;
    private JTextField textFieldStraße;
    private JTextField textFieldHausnummer;
    private JTextField textFieldPLZ;
    private JTextField textFieldOrt;
    private JTextField textFieldDiagnose;
    private JButton buttonSpeichern;
    private JButton buttonAbbrechen;
    private JComboBox comboBoxGender;
    private JComboBox comboBoxNationality;
    private JComboBox comboBoxInsurance;

    /**
     * Konstruktor für die GUI zum Einfügen eines neuen Patienten.
     *
     * @param guiMenu Das Hauptfenster, von dem aus dieses Dialogfeld geöffnet wurde.
     */

    public GUIPatientEinfuegen(GUIMenu guiMenu){
       //Fenster Einstellungen
        this.GUIMenu = guiMenu;
        setTitle("Neuen Patient hinzufügen");
        setSize(400,550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //zentrieren: Fenster in der Mitte

        //Layout
        JPanel contentPaneEinfuegen = new JPanel();
        contentPaneEinfuegen.setLayout(new GridBagLayout());
        setContentPane(contentPaneEinfuegen);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        //Eingabefelder für Patientendaten hinzufügen
        //SVNR
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        contentPaneEinfuegen.add(new JLabel("SVNR"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        textFieldSVNR = new JTextField(10);
        contentPaneEinfuegen.add(textFieldSVNR, gbc);

        //Vorname
        gbc.gridx = 0; gbc.gridy=1;
        gbc.anchor = GridBagConstraints.WEST;
        contentPaneEinfuegen.add(new JLabel("Vorname"),gbc);

        gbc.gridx = 1; gbc.gridy=1;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        textFieldVorname = new JTextField(20);
        contentPaneEinfuegen.add(textFieldVorname,gbc);

        //Nachname
        gbc.gridx=0; gbc.gridy=2;
        gbc.anchor=GridBagConstraints.WEST;
        contentPaneEinfuegen.add(new JLabel("Nachname"),gbc);

        gbc.gridx=1; gbc.gridy=2;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        textFieldNachname = new JTextField(20);
        contentPaneEinfuegen.add(textFieldNachname,gbc);

        //Geburtsdatum
        gbc.gridx=0; gbc.gridy=3;
        gbc.anchor=GridBagConstraints.WEST;
        contentPaneEinfuegen.add(new JLabel("Geburtsdatum"),gbc);

        gbc.gridx=1; gbc.gridy=3;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        textFieldGeburtsdatum = new JTextField(15);
        contentPaneEinfuegen.add(textFieldGeburtsdatum,gbc);

        //Straße
        gbc.gridx=0; gbc.gridy=4;
        gbc.anchor=GridBagConstraints.WEST;
        contentPaneEinfuegen.add(new JLabel("Straße"),gbc);

        gbc.gridx=1; gbc.gridy=4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        textFieldStraße = new JTextField(20);
        contentPaneEinfuegen.add(textFieldStraße,gbc);

        //Hausnummer
        gbc.gridx=0; gbc.gridy=5;
        gbc.anchor=GridBagConstraints.WEST;
        contentPaneEinfuegen.add(new JLabel("Hausnummer"),gbc);

        gbc.gridx=1; gbc.gridy=5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        textFieldHausnummer = new JTextField(15);
        contentPaneEinfuegen.add(textFieldHausnummer,gbc);

        //PLZ
        gbc.gridx=0; gbc.gridy=6;
        gbc.anchor = GridBagConstraints.WEST;
        contentPaneEinfuegen.add(new JLabel("PLZ"),gbc);

        gbc.gridx=1; gbc.gridy=6;
        gbc.fill= GridBagConstraints.HORIZONTAL;
        textFieldPLZ = new JTextField(15);
        contentPaneEinfuegen.add(textFieldPLZ,gbc);

        //Ort
        gbc.gridx=0; gbc.gridy=7;
        gbc.anchor=GridBagConstraints.WEST;
        contentPaneEinfuegen.add(new JLabel("Ort"),gbc);

        gbc.gridx=1; gbc.gridy=7;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        textFieldOrt = new JTextField(15);
        contentPaneEinfuegen.add(textFieldOrt,gbc);

        //Diagnose
        gbc.gridx=0; gbc.gridy=8;
        gbc.anchor=GridBagConstraints.WEST;
        contentPaneEinfuegen.add(new JLabel("Diagnose"),gbc);

        gbc.gridx = 1; gbc.gridy=8;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        textFieldDiagnose = new JTextField(20);
        contentPaneEinfuegen.add(textFieldDiagnose,gbc);

        //ComboBoxen
        // Geschlecht ComboBox
        gbc.gridx = 0; gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.WEST;
        contentPaneEinfuegen.add(new JLabel("Geschlecht"), gbc);

        gbc.gridx = 1; gbc.gridy = 9;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        comboBoxGender = new JComboBox<>();
        comboBoxGender.setModel(getComboBoxModel("SELECT genderPatients FROM gender", "genderPatients"));
        comboBoxGender.setPreferredSize(new Dimension(200, 30));
        contentPaneEinfuegen.add(comboBoxGender, gbc);

        // Nationalität ComboBox
        gbc.gridx = 0; gbc.gridy = 10;
        gbc.anchor = GridBagConstraints.WEST;
        contentPaneEinfuegen.add(new JLabel("Nationalität"), gbc);

        gbc.gridx = 1; gbc.gridy = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        comboBoxNationality = new JComboBox<>();
        comboBoxNationality.setModel(getComboBoxModel("SELECT nationalityPatients FROM nationality","nationalityPatients"));
        comboBoxNationality.setPreferredSize(new Dimension(200, 30));
        contentPaneEinfuegen.add(comboBoxNationality, gbc);

        // Versicherung ComboBox
        gbc.gridx = 0; gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.WEST;
        contentPaneEinfuegen.add(new JLabel("Versicherung"), gbc);

        gbc.gridx = 1; gbc.gridy = 11;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        comboBoxInsurance = new JComboBox<>();
        comboBoxInsurance.setModel(getComboBoxModel("SELECT insurancePatients FROM insurance", "insurancePatients"));
        comboBoxInsurance.setPreferredSize(new Dimension(200, 30));
        contentPaneEinfuegen.add(comboBoxInsurance, gbc);

        //Speichern Button
        gbc.gridx=0; gbc.gridy=12;
        gbc.gridwidth = 2; //Button geht über zwei Spalten
        gbc.fill=GridBagConstraints.HORIZONTAL;
        buttonSpeichern = new JButton("Speichern");
        contentPaneEinfuegen.add(buttonSpeichern,gbc);

        //Abbrechen Button
        gbc.gridx=0; gbc.gridy=13;
        gbc.gridwidth = 2; //Button geht über zwei Spalten
        gbc.fill=GridBagConstraints.HORIZONTAL;
        buttonAbbrechen = new JButton("Abbrechen");
        contentPaneEinfuegen.add(buttonAbbrechen,gbc);


        /**
         * Speichert die eingegebenen Patientendaten in der Datenbank.
         * Die Eingaben werden validiert und dann ein SQL-Insert ausgeführt, um die Daten zu speichern.
         */
        //Action Listener für Button Speichern
        buttonSpeichern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            try {
                                long SVNR = Long.parseLong(textFieldSVNR.getText());
                                String eingabeVorname = textFieldVorname.getText();
                                String eingabeNachname = textFieldNachname.getText();
                                String eingabeGeburtsdatum = textFieldGeburtsdatum.getText();
                                String eingabeStraße = textFieldStraße.getText();
                                String eingabeHausnummer = textFieldHausnummer.getText();
                                String eingabePlz = textFieldPLZ.getText();
                                String eingabeOrt = textFieldOrt.getText();
                                String eingabeDiagnose = textFieldDiagnose.getText();
                                String eingabeGender = (String) comboBoxGender.getSelectedItem();
                                String eingabeNationality = (String) comboBoxNationality.getSelectedItem();
                                String eingabeInsurance = (String) comboBoxInsurance.getSelectedItem();

                                // Überprüfen, ob die SVNR einzigartig ist
                                if (!SVNReinzigartig(SVNR)) {
                                    SwingUtilities.invokeLater(() ->
                                            JOptionPane.showMessageDialog(GUIPatientEinfuegen.this, "Die SVNR ist bereits vergeben!", "Fehler", JOptionPane.ERROR_MESSAGE));
                                    return null;
                                }

                                // Hole die IDs aus der Datenbank
                                int idGender = getGenderid(eingabeGender);
                                int idNationality = getNationalityId(eingabeNationality);
                                int idInsurance = getInsuranceId(eingabeInsurance);

                                // Wenn eine ID nicht gefunden wurde, abbrechen
                                if (idGender == -1 || idNationality == -1 || idInsurance == -1) {
                                    SwingUtilities.invokeLater(() ->
                                            JOptionPane.showMessageDialog(contentPaneEinfuegen, "Fehler bei der Auswahl der IDs!", "Fehler", JOptionPane.ERROR_MESSAGE));
                                    return null;
                                }

                                // Speichere die Patientendaten
                                boolean erfolgreich = Patient.patientEinfuegen(
                                        SVNR, eingabeVorname, eingabeNachname, eingabeGeburtsdatum, eingabeStraße,
                                        eingabeHausnummer, eingabePlz, eingabeOrt, eingabeDiagnose,
                                        String.valueOf(idGender), String.valueOf(idNationality), String.valueOf(idInsurance));
                                if (erfolgreich) {
                                    SwingUtilities.invokeLater(() -> {
                                        JOptionPane.showMessageDialog(null, "Patient erfolgreich hinzugefügt.");
                                        dispose();
                                        GUIMenu menu = new GUIMenu();
                                        menu.setVisible(true);
                                    });
                                } else {
                                    SwingUtilities.invokeLater(() ->
                                            JOptionPane.showMessageDialog(null, "Fehler beim Hinzufügen des Patienten!"));
                                }
                            } catch (NumberFormatException ex) {
                                SwingUtilities.invokeLater(() ->
                                        JOptionPane.showMessageDialog(null, "Bitte geben Sie eine gültige SVNR ein!"));
                            }
                            return null;
                        }
                    }.execute();
                }
            }
        });

        /*
        SwingWorker wird verwendet, um die Datenbankoperationen im Hintergrund auszuführen.
        Der eigentliche Code für das Speichern der Patientendaten wurde in die Methode doInBackground() verlagert. Diese wird im Hintergrund ausgeführt.
        Um die GUI nicht zu blockieren, werden alle UI-Änderungen (z. B. das Anzeigen von Fehlern oder Bestätigungen) mit SwingUtilities.invokeLater() ausgeführt,
        um sicherzustellen, dass sie im Event-Dispatch-Thread (EDT) erfolgen.

        SwingWorker stellt sicher, dass die Benutzeroberfläche immer reaktionsfähig bleibt, auch wenn der Code im Hintergrund läuft.
        Das verhindert das Einfrieren der GUI und sorgt für ein besseres Benutzererlebnis.
         */

        //Zurück zum Hauptmenü
        //Action Listener Abbrechen-Button
        buttonAbbrechen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GUIMenu menu = new GUIMenu();
                menu.setVisible(true);
            }
        });

}

    /**
     * Überprüft, ob die eingegebene Sozialversicherungsnummer (SVNR) bereits in der Datenbank vorhanden ist.
     * Diese Methode führt eine SQL-Abfrage aus, um die Anzahl der Datensätze mit der angegebenen SVNR zu zählen.
     * Wenn die Anzahl 0 ist, ist die SVNR einzigartig und die Methode gibt true zurück.
     * Andernfalls gibt sie false zurück.
     *
     * @param SVNR Die Sozialversicherungsnummer des Patienten, die überprüft werden soll.
     * @return true, wenn die SVNR einzigartig ist (noch nicht in der Datenbank vorhanden); false, wenn die SVNR bereits existiert.
     */

    public boolean SVNReinzigartig(long SVNR){
       String sql = "SELECT COUNT(*) FROM patients WHERE SVNR = ?";
        try(Connection connection = Patient.dbVerbindung();
            PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setLong(1, SVNR);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                int count = rs.getInt(1);
                return count == 0;
            }
        } catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Fehler bei der Überprüfung der SVNR: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    /**
     * Validiert die Benutzereingaben für die SVNR, das Geburtsdatum und die Postleitzahl.
     * Diese Methode prüft, ob:
     * - Die SVNR nur aus Zahlen besteht,
     * - Das Geburtsdatum dem Format YYYY-MM-DD entspricht,
     * - Die Postleitzahl nur aus Zahlen besteht.
     * Falls eine der Prüfungen fehlschlägt, wird eine entsprechende Fehlermeldung angezeigt und die Methode gibt false zurück.
     *
     * @return true, wenn alle Eingaben gültig sind; false, wenn eine Eingabe ungültig ist.
     */

    public boolean validateInput(){
        String svnrText = textFieldSVNR.getText();
        String geburtsdatum = textFieldGeburtsdatum.getText();
        String plz = textFieldPLZ.getText();

        if (!svnrText.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Die SVNR muss eine Zahl sein!", "Fehler", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!geburtsdatum.matches("\\d{4}-\\d{2}-\\d{2}")) {
            JOptionPane.showMessageDialog(this, "Das Geburtsdatum muss im Format YYYY-MM-DD sein!", "Fehler", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!plz.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Die PLZ darf nur Zahlen enthalten!", "Fehler", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Methode um GenderId aus der MySQL Datenbank zu holen und auch zu aktualisieren.
     * @param gender
     * @return
     */
     public int getGenderid (String gender){
        String sql = "SELECT idGender FROM gender where genderPatients = ?";
        try (Connection connection = Patient.dbVerbindung();
        PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, gender);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getInt("idGender");
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Fehler bei der Abfrage der Gender-ID", "Fehler",JOptionPane.ERROR_MESSAGE);
        }
        return -1; //Falls keine ID gefunden wird
     }

    /**
     * Methode um NationalityID aus der MySQL Datenbank zu holen und auch zu aktualisieren.
     * @param nationality
     * @return
     */
    // Methode um die ID für Nationalität zu holen
    public int getNationalityId(String nationality) {
        String sql = "SELECT idNationality FROM nationality WHERE nationalityPatients = ?";
        try (Connection connection = Patient.dbVerbindung();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nationality);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("idNationality");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Fehler bei der Abfrage der Nationalität-ID", "Fehler", JOptionPane.ERROR_MESSAGE);
        }
        return -1;  // Falls keine ID gefunden wird
    }

    /**
     * Methode um InsuranceID aus der MySQL Datenbank zu holen und auch zu aktualisieren.
     * @param insurance
     * @return
     */
     public int getInsuranceId (String insurance){
        String sql = "SELECT idInsurance FROM insurance where insurancePatients = ?";
        try(Connection connection = Patient.dbVerbindung();
        PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1,insurance);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("idInsurance");
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Fehler bei der Abfrage der Versicherungs-ID", "Fehler", JOptionPane.ERROR_MESSAGE );
        }
        return -1; //Falls keine ID vorhanden
     }


    /**
     * Methode ruft Daten aus einer Datenbank ab und füllt das Modell der ComboBox mit den entsprechenden Werten
     * @param sql
     * @param columnName
     * @return
     */

    public DefaultComboBoxModel<String> getComboBoxModel(String sql, String columnName) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        try (Connection connection = Patient.dbVerbindung();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                model.addElement(rs.getString(columnName));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Fehler beim Laden der Daten für die ComboBox: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
        return model;
    }

}





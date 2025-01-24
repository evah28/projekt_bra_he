import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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



    public GUIPatientEinfuegen(GUIMenu guiMenu){
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

        //Eingabefelder hinzufügen
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
        comboBoxGender = new JComboBox<>(new String[] {"männlich", "weiblich", "divers"});
        comboBoxGender.setPreferredSize(new Dimension(200, 30));
        contentPaneEinfuegen.add(comboBoxGender, gbc);

        // Nationalität ComboBox
        gbc.gridx = 0; gbc.gridy = 10;
        gbc.anchor = GridBagConstraints.WEST;
        contentPaneEinfuegen.add(new JLabel("Nationalität"), gbc);

        gbc.gridx = 1; gbc.gridy = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        comboBoxNationality = new JComboBox<>(new String[] {"Österreich", "Deutschland", "Schweiz", "andere"});
        comboBoxNationality.setPreferredSize(new Dimension(200, 30));
        contentPaneEinfuegen.add(comboBoxNationality, gbc);

        // Versicherung ComboBox
        gbc.gridx = 0; gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.WEST;
        contentPaneEinfuegen.add(new JLabel("Versicherung"), gbc);

        gbc.gridx = 1; gbc.gridy = 11;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        comboBoxInsurance = new JComboBox<>(new String[] {"ÖKG", "SVS", "BVAEB", "andere"});
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

        //Action Listener für Button Speichern
        buttonSpeichern.addActionListener(new ActionListener() {
           @Override
            public void actionPerformed(ActionEvent e) {
              if (validateInput()) {
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

                    //Überprüfen ob SVNR nur 1mal vergeben
                     if (!SVNReinzigartig(SVNR)) {
                         JOptionPane.showMessageDialog(GUIPatientEinfuegen.this, "Die SVNR ist bereits vergeben!", "Fehler", JOptionPane.ERROR_MESSAGE);
                         return;
                     }

                     //Alle Eingabefelder müssen ausgefüllt sein
                     if (eingabeVorname.isEmpty() || eingabeNachname.isEmpty() || eingabeGeburtsdatum.isEmpty() || eingabeStraße.isEmpty() || eingabeHausnummer.isEmpty() || eingabePlz.isEmpty() || eingabeOrt.isEmpty() || eingabeDiagnose.isEmpty() || eingabeGender == null || eingabeNationality == null || eingabeInsurance == null) {
                         JOptionPane.showMessageDialog(contentPaneEinfuegen, "Bitte alle Felder ausfüllen!", "Eingabefehler", JOptionPane.ERROR_MESSAGE);
                         return;
                     }

                     boolean erfolgreich = Patient.patientEinfuegen(
                             SVNR, eingabeVorname, eingabeNachname, eingabeGeburtsdatum, eingabeStraße,
                             eingabeHausnummer, eingabePlz, eingabeOrt, eingabeDiagnose,
                             eingabeGender, eingabeNationality, eingabeInsurance);
                     if (erfolgreich) {
                         JOptionPane.showMessageDialog(null, "Patient erfolgreich hinzugefügt.");
                         dispose();//Fenster schließen
                         GUIMenu menu = new GUIMenu();
                         menu.setVisible(true);
                     } else {
                         JOptionPane.showMessageDialog(null, "Fehler beim Hinzufügen des Patienten!");
                     }
                 } catch (NumberFormatException ex) {
                     JOptionPane.showMessageDialog(null, "Bitte geben Sie eine gültige SVNR ein!");
                 }
              }
           }
        });

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

}





import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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


    public GUIPatientEinfuegen(GUIMenu guiMenu){
        this.GUIMenu = guiMenu;
        setTitle("Neuen Patient hinzufügen");
        setSize(400,400);
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

        //Speichern Button
        gbc.gridx=0; gbc.gridy=9;
        gbc.gridwidth = 2; //Button geht über zwei Spalten
        gbc.fill=GridBagConstraints.HORIZONTAL;
        buttonSpeichern = new JButton("Speichern");
        contentPaneEinfuegen.add(buttonSpeichern,gbc);
        //contentPaneEinfuegen.add(new JLabel(""));

        //Abbrechen Button
        gbc.gridx=0; gbc.gridy=10;
        gbc.gridwidth = 2; //Button geht über zwei Spalten
        gbc.fill=GridBagConstraints.HORIZONTAL;
        buttonAbbrechen = new JButton("Abbrechen");
        contentPaneEinfuegen.add(buttonAbbrechen,gbc);
        //contentPaneEinfuegen.add(new JLabel(""));

        //Action Listener für Button Speichern
        buttonSpeichern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                int SVNR = Integer.parseInt(textFieldSVNR.getText());
                boolean erfolgreich = Patient.patientEinfuegen(
                        SVNR, textFieldVorname.getText(), textFieldNachname.getText(),
                        textFieldGeburtsdatum.getText(), textFieldStraße.getText(), textFieldHausnummer.getText(),
                        textFieldPLZ.getText(),textFieldOrt.getText(),textFieldDiagnose.getText());

                //boolean erfolgreichHinzugefuegt = Patient.patientEinfuegen(SVNR, Vorname, Nachname, Geburtsdatum, Strasse, Hausnummer, PLZ, Ort, Diagnose);
                if(erfolgreich){
                    JOptionPane.showMessageDialog(null, "Patient erfolgreich hinzugefügt.");
                    dispose();//Fenster schließen
                    GUIMenu menu = new GUIMenu();
                    menu.setVisible(true);
                } else{
                    JOptionPane.showMessageDialog(null, "Fehler beim Hinzufügen des Patienten!");
                }
            }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Bitte geben Sie eine gültige SVNR ein.");
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
}



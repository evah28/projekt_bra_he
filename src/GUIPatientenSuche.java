import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * Die Klasse `GUIPatientenSuche` stellt eine Benutzeroberfläche zur Verfügung, um nach Patienten in der Datenbank zu suchen,
 * die mit der Sozialversicherungsnummer (SVNR) übereinstimmen. Sie ermöglicht die Anzeige von Patientendaten in einer Tabelle
 * und bietet Funktionen zum Bearbeiten, Löschen und Abbrechen der Suche.
 */

public class GUIPatientenSuche extends JFrame {
    private JPanel contentPane;
    private JLabel labelSuche;
    private JTextField textSuche;
    private JButton buttonSuche;
    private JButton buttonAbbrechen;
    private JButton buttonLöschen;
    private JButton buttonBearbeiten;
    private JPanel buttonPanel;
    private JPanel panelTabelle;
    private JTable patientenTabelle;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;

    /**
     * Öffnet ein Fenster zur Patientensuche basierend auf der Sozialversicherungsnummer.
     * Es ermöglicht dem Benutzer, eine SVNR einzugeben, die mit den in der Datenbank gespeicherten Patienten übereinstimmt.
     * Suchergebnisse werden in einer Tabelle angezeigt. Der Benutzer kann aus den Ergebnissen einen Patienten auswählen,
     * um dessen Daten zu bearbeiten oder den Patienten zu löschen.
     *
     * Die Methode überprüft die Eingabe des Benutzers, führt die Suche in der Datenbank durch und aktualisiert die Anzeige
     * der Suchergebnisse. Es gibt auch Optionen, die Auswahl abzubrechen oder zu bearbeiten.
     */

    public void patientenSuchen() {
       //Neues JFrame (Suchfenster) für die Suchfunktion
        JFrame suchFenster = new JFrame("Patient suchen");
        suchFenster.setSize(800, 600);
        suchFenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        suchFenster.setLocationRelativeTo(null);//Fenster zentrieren
        suchFenster.setLayout(new BorderLayout());

        JPanel suchPanel = new JPanel();
        suchPanel.setLayout(new FlowLayout(FlowLayout.LEFT,20,20)); // Abstand zwischen den Komponenten
        JLabel labelSuche = new JLabel("Sozialversicherungsnummer:");
        textSuche = new JTextField(10);
        JButton buttonSuche = new JButton("Suchen");

        //Hinzufügen der Felder im Suchfenster
        suchPanel.add(labelSuche);
        suchPanel.add(textSuche);
        suchPanel.add(buttonSuche);

        // Erstellen und hinzufügen einer Tabelle für Suchergebnisse
        String[] spalten = {"SVNR", "Nachname", "Vorname", "Geburtsdatum", "Straße", "Hausnummer", "PLZ", "Ort", "Diagnose"};
        DefaultTableModel tableModel = new DefaultTableModel(spalten, 0);
        JTable patientenTabelle = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(patientenTabelle);

        //Panel für die Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20,20));
        JButton buttonAbbrechen = new JButton("Abbrechen");
        JButton buttonLöschen = new JButton("Löschen");
        JButton buttonBearbeiten = new JButton("Bearbeiten");

        //Hinzufügen zu ButtonPanel
        buttonPanel.add(buttonAbbrechen);
        buttonPanel.add(buttonLöschen);
        buttonPanel.add(buttonBearbeiten);

        suchFenster.add(suchPanel, BorderLayout.NORTH);
        suchFenster.add(scrollPane, BorderLayout.CENTER);
        suchFenster.add(buttonPanel, BorderLayout.SOUTH);

        //Action Listener für Suchen Button
        buttonSuche.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String suchbegriff = textSuche.getText();

                //Eingabe darf nicht leer sein
                if(suchbegriff.isEmpty()) {
                    JOptionPane.showMessageDialog(suchFenster, "Bitte geben Sie eine Sozialversicherungsnummer ein!","Eingabefehler",JOptionPane.ERROR_MESSAGE);
                    return;
                }
               //Überprüfung nur Zahlen erlaubt
               if(!suchbegriff.matches("\\d+")){ //Regex für nur Zahlen
                    JOptionPane.showMessageDialog(suchFenster, "Bitte nur Zahlen eingeben!", "Eingabefehler", JOptionPane.ERROR_MESSAGE);
                    return;
                }

               //Methode zur DB-Verbindung aus der Patientenklasse aufrufen
                Connection connection = Patient.dbVerbindung();

                if( connection != null) {
                       try {
                           String sql = "SELECT * FROM patients WHERE SVNR LIKE ?";
                           PreparedStatement pst = connection.prepareStatement(sql);
                           pst.setString(1, "%" + suchbegriff + "%");
                           ResultSet rs = pst.executeQuery();

                    tableModel.setRowCount(0); //Vorherige Ergebnisse entfernen

                    boolean eintragGefunden = false; //Prüfen, ob Suchbegriff gefunden wurde

                    while (rs.next()) {
                        eintragGefunden = true; //Eintrag wurde gefunden
                        Object[] row = {
                            rs.getLong("SVNR"),
                            rs.getString("Nachname"),
                            rs.getString("Vorname"),
                            rs.getDate("Geburtsdatum"),
                            rs.getString("Straße"),
                            rs.getInt("Hausnummer"),
                            rs.getInt("PLZ"),
                            rs.getString("Ort"),
                            rs.getString("Diagnose"),
                        };
                        tableModel.addRow(row);

                    }
                    //Falls kein Eintrag gefunden
                    if(!eintragGefunden) {
                        JOptionPane.showMessageDialog(suchFenster, "Kein Patient mit der Sozialversicherungsnummer "+ suchbegriff+ " gefunden!", "Hinweis", JOptionPane.WARNING_MESSAGE);
                    }
                } catch(SQLException ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(suchFenster, "Fehler bei der Datenbankverbindung!");

                       }
            }
            }
        });

        //Action Listener für Abbrechen-Button
        buttonAbbrechen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIMenu menu = new GUIMenu();
                menu.setVisible(true);
                suchFenster.dispose();
            }
        });

        //Action Listener für Löschen Button
        buttonLöschen.addActionListener(e -> {
            int selectedRow = patientenTabelle.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(suchFenster, "Bitte wählen Sie einen Patienten aus, um ihn zu löschen.","Hinweis", JOptionPane.WARNING_MESSAGE);
                return;
            }
            long svnr = (long)tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(suchFenster, "Möchten Sie den Patienten mit der Sozialversicherungsnummer " + svnr + " löschen?","Bestätigung",JOptionPane.YES_NO_OPTION);

               if(confirm == JOptionPane.YES_OPTION) {
                   String suchbegriff = textSuche.getText();

                   //Aufrufen der dbVerbindung Methode aus der Patientenklasse
                   Connection connection = Patient.dbVerbindung();

                   if( connection != null) {
                       try {
                           String sql = "DELETE FROM patients WHERE SVNR = ?";
                           PreparedStatement pst = connection.prepareStatement(sql);
                           pst.setLong(1, svnr);
                           int rowsDeleted = pst.executeUpdate();

                           if(rowsDeleted > 0) {
                               tableModel.removeRow(selectedRow);
                               JOptionPane.showMessageDialog(this,"Patient erfolgreich aus Datenbank gelöscht.");
                           }
                       }catch(SQLException ex){
                           ex.printStackTrace();
                           JOptionPane.showMessageDialog(this, "Fehler beim Löschen des Patienten! ", "Fehler", JOptionPane.ERROR_MESSAGE);
                       }
                       }
               }
        });

        //Action Listener für Bearbeiten-Button
        buttonBearbeiten.addActionListener(e -> {
            int selectedRow = patientenTabelle.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(suchFenster, "Bitte wählen Sie einen Patienten aus, um ihn zu bearbeiten.", "Hinweis", JOptionPane.WARNING_MESSAGE);
            return;
            }

            //Patientendaten aus Tabelle holen und in ein Bearbeitungsfenster laden
            long svnr = (long) tableModel.getValueAt(selectedRow, 0);
            String nachname = (String) tableModel.getValueAt(selectedRow, 1);
            String vorname = (String) tableModel.getValueAt(selectedRow, 2);
            Date geburtsdatum = (Date) tableModel.getValueAt(selectedRow, 3);
            String strasse = (String) tableModel.getValueAt(selectedRow, 4);
            int hausnummer = (int) tableModel.getValueAt(selectedRow, 5);
            int plz = (int) tableModel.getValueAt(selectedRow, 6);
            String ort = (String) tableModel.getValueAt(selectedRow, 7);
            String diagnose = (String) tableModel.getValueAt(selectedRow, 8);

            JFrame bearbeitenFenster = new JFrame("Patient bearbeiten");
            bearbeitenFenster.setSize(400, 400);
            bearbeitenFenster.setLayout(new GridLayout(0, 2, 10, 10)); // Abstände zwischen den Feldern
            bearbeitenFenster.setLocationRelativeTo(null);

            // Geschlecht ComboBox
            JComboBox<String> comboGeschlecht = new JComboBox<>(new String[] { "Männlich", "Weiblich", "Divers" });
            comboGeschlecht.setSelectedItem(null); // Kein Wert vorab auswählgewählt

            // Nationalität ComboBox
            JComboBox<String> comboNationalitaet = new JComboBox<>(new String[] { "Österreich", "Deutschland", "Schweiz", "andere" });
            comboNationalitaet.setSelectedItem(null);

            // Versicherung ComboBox
            JComboBox<String> comboVersicherung = new JComboBox<>(new String[] { "ÖGK", "BVAEB", "SVS", "andere"});
            comboVersicherung.setSelectedItem(null);

            // Felder für die Bearbeitung
            JTextField textnachname = new JTextField(nachname);
            JTextField textvorname = new JTextField(vorname);
            JTextField textgeburtsdatum = new JTextField(geburtsdatum.toString());
            JTextField textdiagnose = new JTextField(diagnose);
            JTextField textstrasse = new JTextField(strasse);
            JTextField texthausnummer = new JTextField(String.valueOf(hausnummer));
            JTextField textplz = new JTextField(String.valueOf(plz));
            JTextField textort = new JTextField(ort);

            // Hinzufügen der Felder zum Fenster
            bearbeitenFenster.add(new JLabel("Nachname:"));
            bearbeitenFenster.add(textnachname);
            bearbeitenFenster.add(new JLabel("Vorname:"));
            bearbeitenFenster.add(textvorname);
            bearbeitenFenster.add(new JLabel("Geburtsdatum (yyyy-mm-dd):"));
            bearbeitenFenster.add(textgeburtsdatum);
            bearbeitenFenster.add(new JLabel("Diagnose:"));
            bearbeitenFenster.add(textdiagnose);
            bearbeitenFenster.add(new JLabel("Straße:"));
            bearbeitenFenster.add(textstrasse);
            bearbeitenFenster.add(new JLabel("Hausnummer:"));
            bearbeitenFenster.add(texthausnummer);
            bearbeitenFenster.add(new JLabel("PLZ:"));
            bearbeitenFenster.add(textplz);
            bearbeitenFenster.add(new JLabel("Ort:"));
            bearbeitenFenster.add(textort);
            bearbeitenFenster.add(new JLabel("Geschlecht:"));
            bearbeitenFenster.add(comboGeschlecht);
            bearbeitenFenster.add(new JLabel("Nationalität:"));
            bearbeitenFenster.add(comboNationalitaet);
            bearbeitenFenster.add(new JLabel("Versicherung:"));
            bearbeitenFenster.add(comboVersicherung);

            JButton buttonSpeichern = new JButton("Bearbeitung speichern");
            JButton buttonBearbAbbrechen = new JButton("Bearbeitung abbrechen");

            bearbeitenFenster.add(buttonSpeichern);
            bearbeitenFenster.add(buttonBearbAbbrechen);



            //Bearbeitung abbrechen Button
            buttonBearbAbbrechen.addActionListener(e1 -> bearbeitenFenster.dispose());

            //Bearbeitung speichern Button
            buttonSpeichern.addActionListener(e1 -> {
                if (textnachname.getText().isEmpty() || textvorname.getText().isEmpty() || textgeburtsdatum.getText().isEmpty() ||
                        textdiagnose.getText().isEmpty() || textstrasse.getText().isEmpty() || texthausnummer.getText().isEmpty() ||
                        textplz.getText().isEmpty() || textort.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(bearbeitenFenster, "Bitte füllen Sie alle Felder aus.", "Eingabefehler", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //Änderungen speichern
                String suchbegriff = textSuche.getText();

                //Aufrufen der Methode zur DB-Verbindung in der Patientenmethode
                Connection connection = Patient.dbVerbindung();


                if( connection != null) {
                    try {
                        // IDs für Geschlecht, Nationalität und Versicherung holen
                        String sqlGeschlecht = "SELECT idGender FROM gender WHERE genderPatients = ?";
                        PreparedStatement pstGeschlecht = connection.prepareStatement(sqlGeschlecht);
                        pstGeschlecht.setString(1, (String) comboGeschlecht.getSelectedItem());
                        ResultSet rsGeschlecht = pstGeschlecht.executeQuery();
                        int idGender = rsGeschlecht.next() ? rsGeschlecht.getInt("idGender") : -1;

                        String sqlNationalitaet = "SELECT idNationality FROM nationality WHERE nationalityPatients = ?";
                        PreparedStatement pstNationalitaet = connection.prepareStatement(sqlNationalitaet);
                        pstNationalitaet.setString(1, (String) comboNationalitaet.getSelectedItem());
                        ResultSet rsNationalitaet = pstNationalitaet.executeQuery();
                        int idNationality = rsNationalitaet.next() ? rsNationalitaet.getInt("idNationality") : -1;

                        String sqlVersicherung = "SELECT idInsurance FROM insurance WHERE insurancePatients = ?";
                        PreparedStatement pstVersicherung = connection.prepareStatement(sqlVersicherung);
                        pstVersicherung.setString(1, (String) comboVersicherung.getSelectedItem());
                        ResultSet rsVersicherung = pstVersicherung.executeQuery();
                        int idInsurance = rsVersicherung.next() ? rsVersicherung.getInt("idInsurance") : -1;

                        // UPDATE-Statement für die Bearbeitung des Patienten
                        String sql = "UPDATE patients SET Nachname = ?, Vorname = ?, Geburtsdatum = ?, Diagnose = ?, Straße = ?, Hausnummer = ?, PLZ = ?, Ort = ?, idGender = ?, idNationality = ?, idInsurance = ? WHERE SVNR = ?";
                        PreparedStatement pst = connection.prepareStatement(sql);
                        pst.setString(1, textnachname.getText());
                        pst.setString(2, textvorname.getText());
                        pst.setDate(3, geburtsdatum);
                        pst.setString(4, textdiagnose.getText());
                        pst.setString(5, textstrasse.getText());
                        pst.setInt(6, Integer.parseInt(texthausnummer.getText()));
                        pst.setInt(7, Integer.parseInt(textplz.getText()));
                        pst.setString(8, textort.getText());
                        pst.setInt(9, idGender);
                        pst.setInt(10, idNationality);
                        pst.setInt(11, idInsurance);
                        pst.setLong(12, svnr); // Die SVNR des Patienten, der bearbeitet wird
                        int rowsUpdated = pst.executeUpdate();

                        if (rowsUpdated > 0) {
                            // Tabelle aktualisieren
                            tableModel.setValueAt(textnachname.getText(), selectedRow, 1);
                            tableModel.setValueAt(textvorname.getText(), selectedRow, 2);
                            tableModel.setValueAt(Date.valueOf(textgeburtsdatum.getText()), selectedRow, 3);
                            tableModel.setValueAt(textdiagnose.getText(), selectedRow, 4);
                            tableModel.setValueAt(textstrasse.getText(), selectedRow, 5);
                            tableModel.setValueAt(Integer.parseInt(texthausnummer.getText()), selectedRow, 6);
                            tableModel.setValueAt(Integer.parseInt(textplz.getText()), selectedRow, 7);
                            tableModel.setValueAt(textort.getText(), selectedRow, 8);

                            JOptionPane.showMessageDialog(bearbeitenFenster, "Patient erfolgreich bearbeitet.");
                            bearbeitenFenster.dispose();
                        }
                    }catch(SQLException ex2) {
                    ex2.printStackTrace();
                    JOptionPane.showMessageDialog(bearbeitenFenster, "Fehler beim Bearbeiten des Patienten.","Fehler", JOptionPane.ERROR_MESSAGE);
                    } catch(IllegalArgumentException ex3) {
                        JOptionPane.showMessageDialog(bearbeitenFenster, "Bitte geben Sie ein gültiges Datum im Format yyyy-mm-dd ein.", "Eingabefehler",JOptionPane.ERROR_MESSAGE);
                    }
                }
                    });
                    bearbeitenFenster.setVisible(true);
        });

        //Suchfenster sichtbar machen
        suchFenster.setVisible(true);
    }
}

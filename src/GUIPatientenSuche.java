import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

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


    public void patientenSuchen() {
       //Neues JFrame für die Suchfunktion
        JFrame suchFenster = new JFrame("Patient suchen");
        suchFenster.setSize(800, 600);
        suchFenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        suchFenster.setLocationRelativeTo(null);//Fenster zentrieren
        suchFenster.setLayout(new BorderLayout());


        JPanel suchPanel = new JPanel();
        suchPanel.setLayout(new FlowLayout());
        JLabel labelSuche = new JLabel("Sozialversicherungsnummer:");
        textSuche = new JTextField(10);
        JButton buttonSuche = new JButton("Suchen");

        //Hinzufügen im Suchfenster
        suchPanel.add(labelSuche);
        suchPanel.add(textSuche);
        suchPanel.add(buttonSuche);


        // Tabelle für Suchergebnisse
        String[] spalten = {"ID", "Nachname", "Vorname", "Geburtsdatum", "Diagnose", "Straße", "Hausnummer", "PLZ", "Ort"};
        DefaultTableModel tableModel = new DefaultTableModel(spalten, 0);
        JTable patientenTabelle = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(patientenTabelle);

        //Panel für die Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
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

        //Action Listener für Button machen
        buttonSuche.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String suchbegriff = textSuche.getText();
                Connection con = null;

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

                //Verbindung DB
                String URL = "jdbc:mysql://localhost:3306/project_db";
                String USER1 = "root";
                String PASSWORD1 = "FHmagdalena0504?";
                String USER2 = "root";
                String PASSWORD2 = "Tm20!Ka89#MaJO";


                try {
                    // Zuerst versuchen, mit dem ersten Benutzer eine Verbindung aufzubauen
                    con = DriverManager.getConnection(URL, USER1, PASSWORD1);
                } catch (SQLException e1) {
                    try {
                        // Wenn Benutzer1 fehlschlägt, versuche es mit Benutzer2
                        con = DriverManager.getConnection(URL, USER2, PASSWORD2);
                    } catch (SQLException e2) {
                        e2.printStackTrace();
                        JOptionPane.showMessageDialog(suchFenster, "Keine Verbindung zur Datenbank möglich.");
                        return; //Abbrechen, wenn keine Verbindung möglich ist
                    }
                }

                   if( con != null) {
                       try {
                           String sql = "SELECT * FROM patients WHERE SVNR LIKE ?";
                           PreparedStatement pst = con.prepareStatement(sql);
                           pst.setString(1, "%" + suchbegriff + "%");
                           ResultSet rs = pst.executeQuery();

                    tableModel.setRowCount(0);

                    boolean eintragGefunden = false; //Prüfen, ob Suchbegriff gefunden wurde

                    while (rs.next()) {
                        eintragGefunden = true; //Eintrag wurde gefunden
                        Object[] row = {
                            rs.getLong("SVNR"),
                            rs.getString("Nachname"),
                            rs.getString("Vorname"),
                            rs.getDate("Geburtsdatum"),
                            rs.getString("Diagnose"),
                            rs.getString("Straße"),
                            rs.getInt("Hausnummer"),
                            rs.getInt("PLZ"),
                            rs.getString("Ort"),
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
                } finally{
                          try{
                              con.close();//Verbindung schließen
                          } catch (SQLException ex) {
                              ex.printStackTrace();
                          }
                       }
            }
            }
        });

        //Action Listener für Abbrechen-Button
        buttonAbbrechen.addActionListener(e -> suchFenster.dispose());

        //Action Listener für Löschen Button
        buttonLöschen.addActionListener(e -> {
            int selectedRow = patientenTabelle.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(suchFenster, "Bitte wählen Sie einen Patienten aus, um ihn zu löschen.","Hinweis", JOptionPane.WARNING_MESSAGE);
                return;
            }
            long svnr = (long)tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(suchFenster, "Möchten Sie den Patienten mit der Sozialversicherungsnummer " + svnr + " löschen?","Bestätigung",JOptionPane.YES_NO_OPTION);

            //Verbindung DB
            String URL = "jdbc:mysql://localhost:3306/project_db";
            String USER1 = "root";
            String PASSWORD1 = "FHmagdalena0504?";
            String USER2 = "root";
            String PASSWORD2 = "Tm20!Ka89#MaJO";

               if(confirm == JOptionPane.YES_OPTION) {
                   String suchbegriff = textSuche.getText();
                   Connection con = null;

                   try {
                       // Zuerst versuchen, mit dem ersten Benutzer eine Verbindung aufzubauen
                       con = DriverManager.getConnection(URL, USER1, PASSWORD1);
                   } catch (SQLException e1) {
                       try {
                           // Wenn Benutzer1 fehlschlägt, versuche es mit Benutzer2
                           con = DriverManager.getConnection(URL, USER2, PASSWORD2);
                       } catch (SQLException e2) {
                           e2.printStackTrace();
                           JOptionPane.showMessageDialog(suchFenster, "Keine Verbindung zur Datenbank möglich.");
                           return; //Abbrechen, wenn keine Verbindung möglich ist
                       }
                   }

                   if( con != null) {
                       try {
                           String sql = "DELETE FROM patients WHERE SVNR = ?";
                           PreparedStatement pst = con.prepareStatement(sql);
                           pst.setLong(1, svnr);
                           int rowsDeleted = pst.executeUpdate();

                           if(rowsDeleted > 0) {
                               tableModel.removeRow(selectedRow);
                               JOptionPane.showMessageDialog(suchFenster,"Patient erfolgreich aus Datenbank gelöscht.");
                           }
                       }catch(SQLException ex){
                           ex.printStackTrace();
                           JOptionPane.showMessageDialog(suchFenster, "Fehler beim Löschen des Patienten! ", "Fehler", JOptionPane.ERROR_MESSAGE);
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

            //Patientendaten aus Tabelle holen
            long svnr = (long)tableModel.getValueAt(selectedRow, 0);
            String nachname = (String)tableModel.getValueAt(selectedRow, 1);
            String vorname = (String)tableModel.getValueAt(selectedRow, 2);
            Date geburtsdatum = (Date)tableModel.getValueAt(selectedRow, 3);
            String diagnose = (String)tableModel.getValueAt(selectedRow, 4);
            String strasse = (String)tableModel.getValueAt(selectedRow, 5);
            int hausnummer = (int)tableModel.getValueAt(selectedRow, 6);
            int PLZ = (int)tableModel.getValueAt(selectedRow, 7);
            String ort = (String)tableModel.getValueAt(selectedRow, 8);

            //Neues Fenster für die Bearbeitung
            JFrame bearbeitenFenster = new JFrame("Patient bearbeiten");
            bearbeitenFenster.setSize(400,400);
            bearbeitenFenster.setLayout(new GridLayout(10,2));
            bearbeitenFenster.setLocationRelativeTo(null);

            //Felder für die Bearbeitung
            JTextField textnachname = new JTextField(nachname);
            JTextField textvorname = new JTextField(vorname);
            JTextField textgeburtsdatum = new JTextField(geburtsdatum.toString());
            JTextField textdiagnose = new JTextField(diagnose);
            JTextField textstrasse = new JTextField(strasse);
            JTextField texthausnummer = new JTextField(String.valueOf(hausnummer));
            JTextField textplz = new JTextField(String.valueOf(PLZ));
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
                Connection con = null;

                //Verbindung DB
                String URL = "jdbc:mysql://localhost:3306/project_db";
                String USER1 = "root";
                String PASSWORD1 = "FHmagdalena0504?";
                String USER2 = "root";
                String PASSWORD2 = "Tm20!Ka89#MaJO";

                try {
                    // Zuerst versuchen, mit dem ersten Benutzer eine Verbindung aufzubauen
                    con = DriverManager.getConnection(URL, USER1, PASSWORD1);
                } catch (SQLException e1b) {
                    try {
                        // Wenn Benutzer1 fehlschlägt, versuche es mit Benutzer2
                        con = DriverManager.getConnection(URL, USER2, PASSWORD2);
                    } catch (SQLException e2) {
                        e2.printStackTrace();
                        JOptionPane.showMessageDialog(suchFenster, "Keine Verbindung zur Datenbank möglich.");
                        return; //Abbrechen, wenn keine Verbindung möglich ist
                    }
                }

                if( con != null) {
                    try {
                        String sql = "UPDATE patients SET Nachname = ?, Vorname = ?, Geburtsdatum = ?, Diagnose = ?, Straße = ?, Hausnummer = ?, PLZ = ?, Ort = ? WHERE SVNR = ?";
                        PreparedStatement pst = con.prepareStatement(sql);
                        pst.setString(1, textnachname.getText());
                        pst.setString(2, textvorname.getText());
                        pst.setDate(3, Date.valueOf(textgeburtsdatum.getText()));
                        pst.setString(4, textdiagnose.getText());
                        pst.setString(5, textstrasse.getText());
                        pst.setInt(6, Integer.parseInt(texthausnummer.getText()));
                        pst.setInt(7, Integer.parseInt(textplz.getText()));
                        pst.setString(8, textort.getText());
                        pst.setLong(9, svnr);

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

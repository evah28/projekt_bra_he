import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

import static javax.swing.JMenuItem.*;

public class GUIMenu extends JFrame {
    private JPanel contentPaneMenu;
    private JPanel panelTabelle;
    private JTable patientenTabelle;
    private DefaultTableModel tableModel;

    public GUIMenu() {

        //Frame Einstellungen
        setTitle("Menü");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Menüleiste erstellen
        JMenuBar menuBar = new JMenuBar();

        //Menü Funktionen -> Items direkt dem MenuBar hinzugefügt, damit sie in der Menüleiste nebeneinander angezeigt werden
        JMenuItem itemSpeichern = new JMenuItem("Speichern");
        JMenuItem itemEinfügen = new JMenuItem("Einfügen");
        JMenuItem itemBearbeiten = new JMenuItem("Bearbeiten");
        JMenuItem itemLöschen = new JMenuItem("Löschen");
        JMenuItem itemExportieren = new JMenuItem("Exportieren");
        JMenuItem itemSuchen = new JMenuItem("Suchen");

        //Menü Items hinzufügen
        menuBar.add(itemSpeichern);
        menuBar.add(itemEinfügen);
        menuBar.add(itemBearbeiten);
        menuBar.add(itemLöschen);
        menuBar.add(itemExportieren);
        menuBar.add(itemSuchen);

        //Menüleiste hinzufügen
        setJMenuBar(menuBar);

        //Menü-Item Aktionen
        itemSpeichern.addActionListener(e -> {
        });
        itemEinfügen.addActionListener(e -> {
        });
        itemBearbeiten.addActionListener(e -> {
        });
        itemLöschen.addActionListener(e -> {
        });
        itemExportieren.addActionListener(e -> {
        });
        itemSuchen.addActionListener(e -> {
        });


        //Panel für die Tabelle
        JPanel panelTabelle = new JPanel();
        panelTabelle.setLayout(new BorderLayout());
        tableModel = new DefaultTableModel();
        patientenTabelle = new JTable(tableModel);
        panelTabelle.add(new JScrollPane(patientenTabelle), BorderLayout.CENTER);
        add(panelTabelle);

        zeigePatientenDaten();

    }

    public void zeigePatientenDaten(){

        String URL = "jdbc:mysql://localhost:3306/project_db";
        String USER1 = "root";
        String PASSWORD1 = "FHmagdalena0504?";

        String USER2 = "root";
        String PASSWORD2 = "Tm20!Ka89#MaJO";

        //Verbindung Datenbank
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
                return;
            }
        }

        if (con != null) {
            //SQL Abfrage ausführen
            try {
                String query = "SELECT * FROM patients";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                //TAbelle wird geleert bevor neue Daten angezeigt werden
                tableModel.setRowCount(0);

                //Spaltennamen hinzufügen
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                if(tableModel.getColumnCount() == 0){
                    for(int i = 1; i <= columnCount; i++){
                        tableModel.addColumn(metaData.getColumnName(i));
                    }
                }
                while(rs.next()){
                    Object [] row = new Object[columnCount];
                    for(int i = 1; i <= columnCount; i++){
                        row[i-1] = rs.getObject(i);
                    }
                    tableModel.addRow(row);
                }
            } catch (SQLException e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Fehler beim Laden der Patientendaten");
            } finally {
                //Schließe Verbindung
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}

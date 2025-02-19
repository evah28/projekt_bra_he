import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

/**
 * Diese Klasse stellt das GUI-Menü zur Anzeige von Patienten und zur Interaktion mit anderen Funktionen zur Verfügung.
 *  * Sie ermöglicht das Einfügen von Patientendaten, das Exportieren von Daten und das Suchen von Patienten.
 */

public class GUIMenu extends JFrame {
    private JPanel contentPaneMenu;
    private JPanel panelTabelle;
    private JTable patientenTabelle;
    private DefaultTableModel tableModel;

    /**
     * Konstruktor für das GUI-Menü. Initialisiert das Fenster, die Menüleiste und die Tabellenansicht.
     */

    public GUIMenu() {

        //Frame Einstellungen
        setTitle("Menü");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Menüleiste erstellen
        JMenuBar menuBar = new JMenuBar();

        //Layout Menü Leiste um Elemente näher beieinander anzuordnen
        menuBar.setLayout(new FlowLayout(FlowLayout.LEFT)); //links ausgerichtet

        //Menü Funktionen -> Items direkt dem MenuBar hinzugefügt, damit sie in der Menüleiste nebeneinander angezeigt werden
        JMenuItem itemEinfügen = new JMenuItem("Einfügen");
        JMenuItem itemExportieren = new JMenuItem("Exportieren");
        JMenuItem itemSuchen = new JMenuItem("Suchen");

        //Menü Items zur Menüleiste hinzufügen
        menuBar.add(itemSuchen);
        menuBar.add(itemEinfügen);
        menuBar.add(itemExportieren);

        //Menüleiste zum Frame hinzufügen
        setJMenuBar(menuBar);

        //Menü-Item Aktionen
        itemEinfügen.addActionListener(e -> {
            dispose();
            GUIPatientEinfuegen guiPatientEinfuegen = new GUIPatientEinfuegen(this);
            guiPatientEinfuegen.setVisible(true);
        });
        itemExportieren.addActionListener(e -> {

            String [] options = {"Gesamte Patientenliste exportieren", " Einzelne Patientendaten exportieren"};
            int choice = JOptionPane.showOptionDialog(
                    this,
                    "Bitte wählen Sie aus:",
                    "Export auswählen",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
        );
            if(choice == 0){
                Export.exportGesamtePatientenliste();
            }else if(choice == 1){
                Export.exportEinzelnePatienten();
            }
        });
        itemSuchen.addActionListener(e -> {
            dispose();
            GUIPatientenSuche suche = new GUIPatientenSuche();
            suche.patientenSuchen();
        });


        //Panel für die Tabelle
        JPanel panelTabelle = new JPanel();
        panelTabelle.setLayout(new BorderLayout());
        tableModel = new DefaultTableModel();
        patientenTabelle = new JTable(tableModel);
        panelTabelle.add(new JScrollPane(patientenTabelle), BorderLayout.CENTER);
        add(panelTabelle);

        //Methode aufrufen
        zeigePatientenDaten();
    }

    /**
     * Zeigt die Patientendaten in der Tabelle an.
     * Lädt die Daten aus der Datenbank und füllt die Tabelle mit den Abfrageergebnissen.
     */

    public void zeigePatientenDaten(){
        //Aufrufen der Methode zur DB-Verbindung aus der Patientenklasse
        Connection connection = Patient.dbVerbindung();

        if (connection != null) {
            //SQL Abfrage ausführen
            try {
                String query = "SELECT SVNR, Nachname, Vorname, Geburtsdatum, Straße, Hausnummer, PLZ, Ort FROM patients";
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                //Tabelle wird geleert bevor neue Daten angezeigt werden
                tableModel.setRowCount(0);

                //Spaltennamen hinzufügen
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                if(tableModel.getColumnCount() == 0){
                    for(int i = 1; i <= columnCount; i++){
                        tableModel.addColumn(metaData.getColumnName(i));
                    }
                }
                //Zeilen mit den Daten aus der ResultSet hinzufügen
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
            }
        }
    }
}

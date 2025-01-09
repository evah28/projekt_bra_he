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
        JButton buttonAbbrechen = new JButton("Abbrechen");

        //Hinzufügen im Suchfenster
        suchPanel.add(labelSuche);
        suchPanel.add(textSuche);
        suchPanel.add(buttonSuche);
        suchPanel.add(buttonAbbrechen);


        // Tabelle für Suchergebnisse
        String[] spalten = {"ID", "Nachname", "Vorname", "Geburtsdatum", "Diagnose", "Straße", "Hausnummer", "PLZ", "Ort"};
        DefaultTableModel tableModel = new DefaultTableModel(spalten, 0);
        JTable patientenTabelle = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(patientenTabelle);

        suchFenster.add(suchPanel, BorderLayout.NORTH);
        suchFenster.add(scrollPane, BorderLayout.CENTER);

        //Action Listener für Button machen
        buttonSuche.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String suchbegriff = textSuche.getText();

                //Verbindung DB
                try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_db", "root", "Tm20!Ka89#MaJO")){

                    String sql = "SELECT * FROM patients WHERE idpatients LIKE ?";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1, "%" + suchbegriff + "%");
                    ResultSet rs = pst.executeQuery();

                    tableModel.setRowCount(0);

                    while (rs.next()) {
                        Object[] row = {
                            rs.getInt("idpatients"),
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


                } catch(SQLException ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(suchFenster, "Fehler bei der Datenbankverbindung!");
                }
            }
        });

        //Action Listener für Abbrechen-Button
        buttonAbbrechen.addActionListener(e -> suchFenster.dispose());

        //Suchfenster sichtbar machen
        suchFenster.setVisible(true);


    }
}

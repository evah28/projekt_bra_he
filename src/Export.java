import javax.swing.*;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Die Klasse enthält Methoden zum Exportieren von Patientendaten in eine CSV-Datei.
 */

public class Export {

    /**
     * Exportiert die Patientendaten aus einer SQL-Abfrage in eine CSV-Datei.
     * @param query Die SQL-Abfrage. die die zu exportierenden Patientendaten liefert.
     * @param parameter Die Parameter für die SQL-Abfrage
     */

    public static void exportCSV(String query, String[] parameter){

        JFileChooser filechooser = new JFileChooser();
        filechooser.setDialogTitle("Patientendaten als CSV speichern");
        filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        //Standardname für die CSV Datei
        filechooser.setSelectedFile(new java.io.File("patientendaten.csv"));

        int userSelection = filechooser.showSaveDialog(null);

        if(userSelection == JFileChooser.APPROVE_OPTION) {
            String dateipfad = filechooser.getSelectedFile().getAbsolutePath();

            try (Connection connection = Patient.dbVerbindung();
                 PreparedStatement stmt = connection.prepareStatement(query);
                 OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(dateipfad), "UTF-8");
                 FileWriter filedWriter = new FileWriter(dateipfad)){

                //Parameter der Abfrage setzen
                for(int i = 0; i < parameter.length; i++){
                    stmt.setString(i + 1, parameter[i]);
                }

                ResultSet resultSet = stmt.executeQuery();

                //Byte Order Mark für UTF-8 und CSV Header
                fileWriter.write("\uFEFF");
                fileWriter.append("SVNR; Vorname; Nachname; Geburtsdatum; Straße; Hausnummer; PLZ; Ort; Diagnose\n");

                // Patientendaten schreiben
                while (resultSet.next()) {
                    fileWriter.append(resultSet.getString("SVNR")).append(";");
                    fileWriter.append(resultSet.getString("Vorname")).append(";");
                    fileWriter.append(resultSet.getString("Nachname")).append(";");
                    fileWriter.append(resultSet.getString("Geburtsdatum")).append(";");
                    fileWriter.append(resultSet.getString("Straße")).append(";");
                    fileWriter.append(resultSet.getString("Hausnummer")).append(";");
                    fileWriter.append(resultSet.getString("PLZ")).append(";");
                    fileWriter.append(resultSet.getString("Ort")).append(";");
                    fileWriter.append(resultSet.getString("Diagnose")).append("\n");
                }

                JOptionPane.showMessageDialog(null, "CSV-Datei erfolgreich erstellt: " + dateipfad);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Fehler beim Schreiben der Datei!"+ e.getMessage());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Fehler beim Exportieren der Datei!" + e.getMessage() );
                e.printStackTrace();
            }
        } else{
            JOptionPane.showMessageDialog(null, "Export abgebrochen!" );
        }
    }

    /**
     * Exportiert die gesamte Liste der Patienten in eine CSV-Datei.
     */

    public static void exportGesamtePatientenliste(){
        String query = "SELECT * FROM patients";
        exportCSV(query, new String[0]);
    }

    /**
     * Ermöglicht den Export von Daten eines einzelnen Patienten, nach Auswahl aus einer Liste.
     */

    public static void exportEinzelnePatienten(){
        String query = "SELECT SVNR, Vorname, Nachname FROM patients";
        try(Connection connection = Patient.dbVerbindung();
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet rs = stmt.executeQuery()){

            DefaultListModel<String> listModel = new DefaultListModel<>();
            while(rs.next()){
                listModel.addElement(rs.getString("SVNR") + " - " + rs.getString("Vorname") + " " + rs.getString("Nachname"));
            }

            //Zeige Auswahlbox
            JList<String> patientenListe = new JList<>(listModel);
            patientenListe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scrollPane = new JScrollPane(patientenListe);
            int option = JOptionPane.showConfirmDialog(null, scrollPane, "Wählen Sie einen Patienten", JOptionPane.OK_CANCEL_OPTION);

            if(option == JOptionPane.OK_OPTION && patientenListe.getSelectedValue() != null){
                //Wähle die SVNR des ausgewählten Patienten
                String selectedPatient = patientenListe.getSelectedValue();
                String SVNR = selectedPatient.split(" - ")[0];

                //Query für den ausgewählten Patienten
                String patienteQuery = "SELECT * FROM patients WHERE SVNR = ?";
                exportCSV(patienteQuery,new String[]{SVNR});
            } else{
                JOptionPane.showMessageDialog(null, "Kein Patient ausgewählt!" );
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Fehler beim Abrufen der Patientdaten!" + e.getMessage());
        }

    }
}

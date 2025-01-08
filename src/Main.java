import javax.swing.*;
import java.sql.*;

public class Main {

    private static final String URL = "jdbc:mysql://localhost:3306/project_db";
    private static final String USER1 = "root";
    private static final String PASSWORD1 = "FHmagdalena0504?";

    private static final String USER2 = "root";
    private static final String PASSWORD2 = "Tm20!Ka89#MaJO";


    public static void main(String[] args) throws SQLException {


        Connection connection = null;

        try {
            // Zuerst versuchen, mit dem ersten Benutzer eine Verbindung aufzubauen
            connection = DriverManager.getConnection(URL, USER1, PASSWORD1);
            System.out.println("Verbindung mit Benutzer 1 erfolgreich!");
        } catch (SQLException e1) {
            System.out.println("Verbindung mit Benutzer 1 fehlgeschlagen, versuche Benutzer 2...");

            try {
                // Wenn Benutzer1 fehlschlägt, versuche es mit Benutzer2
                connection = DriverManager.getConnection(URL, USER2, PASSWORD2);
                System.out.println("Verbindung mit Benutzer 2 erfolgreich!");
            } catch (SQLException e2) {
                System.out.println("Verbindung mit beiden Benutzern fehlgeschlagen.");
                e2.printStackTrace();
            }
        }


        Connection con = null;
        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM patients");

                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("idpatients"));
                    System.out.println("Name: " + rs.getString("vorname"));
                }
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //GUI
        SwingUtilities.invokeLater(() -> {
            GUILogin gridExample = new GUILogin();
            gridExample.setVisible(true);
        });

        GUILogin login = new GUILogin();
        login.setVisible(true);

        SwingUtilities.invokeLater(() -> new GUIMenu());

    }
}
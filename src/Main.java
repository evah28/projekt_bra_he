import javax.swing.*;
import java.sql.*;

public class Main {

    private static final String URL = "jdbc:mysql://localhost:3306/project_db";
    private static final String USER = "root";
    private static final String PASSWORD = "FHmagdalena0504?";

    public static void main(String[] args) throws SQLException {

        Connection con = null;
        try{
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Datenbank verbunden.");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        /*if (con != null) {
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
        }*/

        //GUI
        SwingUtilities.invokeLater(() -> {
            GUILogin gridExample = new GUILogin();
            gridExample.setVisible(true);
        });

        GUILogin login = new GUILogin();
        login.setVisible(true);

    }
}
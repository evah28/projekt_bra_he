import javax.swing.*;
import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        //GUI
        SwingUtilities.invokeLater(() -> {
            GUILogin gridExample = new GUILogin();
            gridExample.setVisible(true);
        });

        SwingUtilities.invokeLater(() -> new GUIMenu());

    }
}
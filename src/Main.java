import javax.swing.*;
import java.sql.*;

/**
 * Die Main-Klasse ist der Einstiegspunkt der Anwendung. Sie startet die GUI für das Login und das Hauptmenü.
 */

public class Main {

    /**
     * Der Einstiegspunkt der Anwendung. Startet die GUI-Elemente für den Login und das Menü.
     *
     * @param args Die Kommandozeilenargumente (werden in diesem Fall nicht verwendet).
     * @throws SQLException Wenn ein Fehler bei der Datenbankverbindung auftritt.
     */

    public static void main(String[] args) throws SQLException {

        //GUI wird im Event-Dispatch-Thread gestartet
        SwingUtilities.invokeLater(() -> {
            //Login anzeigen
            GUILogin gridExample = new GUILogin();
            gridExample.setVisible(true);
        });
        //GUI für das Hauptmenü starten
        SwingUtilities.invokeLater(() -> new GUIMenu());
    }
}



import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        //GUI
        SwingUtilities.invokeLater(() -> {
            GUILogin gridExample = new GUILogin();
            gridExample.setVisible(true);
        });

        GUILogin login = new GUILogin();
        login.setVisible(true);

    }
}
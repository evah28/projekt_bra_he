import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUILogin extends JFrame {
    private javax.swing.JPanel contentPane;
    private JLabel jLabel_login;
    private JLabel jLabel_benutzername;
    private JTextField textField_benutzername;
    private JLabel JLabel_Passwort;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton abbrechenButton;


    public GUILogin() {
        setTitle("Login");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        pack();


        // Button-ActionListener
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String validusername = "magda13";
                String validpasswort = "1234";

                String enteredUsername = textField_benutzername.getText();
                String enteredPassword = new String(passwordField.getPassword());

                // Vergleich der Eingaben mit den gültigen Werten
                if (validusername.equals(enteredUsername) && validpasswort.equals(enteredPassword)) {
                    System.out.println("Login erfolgreich");

                    // Login-Frame ausblenden
                    dispose();

                    // Neues Menü-Frame öffnen
                    GUIMenu Menu = new GUIMenu();
                    Menu.setLocationRelativeTo(null); // Zentriere Fenster
                    Menu.setVisible(true);

                }
                else {
                    JOptionPane.showMessageDialog(null, "Login fehlgeschlagen!", "Fehler", JOptionPane.ERROR_MESSAGE);
                }

            }
        });



    }




}





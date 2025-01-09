import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUILogin extends JFrame {
    private javax.swing.JPanel contentPane;
    private JLabel labelLogin;
    private JLabel labelBenutzer;
    private JTextField textFieldBenutzername;
    private JLabel labelPasswort;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton abbrechenButton;


    public GUILogin() {
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //Fenster zentrieren
        setLayout(new GridBagLayout()); //GridBagLayout verwenden

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); //Abstände zwischen Komponenten
        gbc.fill = GridBagConstraints.HORIZONTAL; //Wie der verfügbare Platz gefüllt wird (Horizontal)

        //Titel
        JLabel labelLogin = new JLabel("Login", JLabel.CENTER);
        labelLogin.setFont(new Font("Tahoma", Font.BOLD, 20));
        gbc.gridwidth = 2; //Überspanne 2 Spalten
        gbc.gridx = 0; //spalte in der der Titel platziert wird (1.Spalte)
        gbc.gridy = 0; //Zeile in der der Titel platziert wird (1. Zeile)
        add(labelLogin, gbc);

        //Benutzername Label
        gbc.gridwidth = 1; //Zurücksetzen
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Benutzername:"), gbc);

        //Benutzer Text Feld
        gbc.gridx = 1;
        JTextField textFieldBenutzername = new JTextField(15);
        add(textFieldBenutzername, gbc);

        //Passwort Label
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Passwort:"), gbc);

        //Passwordfield
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        add(passwordField, gbc);

        //Login Button
        gbc.gridx = 0;
        gbc.gridy = 3;
        JButton loginButton = new JButton("Login");
        add(loginButton, gbc);

        //Abbrechen Button
        gbc.gridx = 1;
        JButton abbrechenButton = new JButton("Abbrechen");
        add(abbrechenButton, gbc);

        // Button-ActionListener
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String validusername = "magda13";
                String validpasswort = "1234";

                String enteredUsername = textFieldBenutzername.getText();
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

        abbrechenButton.addActionListener(e -> System.exit(0));
        setVisible(true);




    }




}





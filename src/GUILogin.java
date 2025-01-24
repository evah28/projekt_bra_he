import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Diese Klasse stellt das Login-Fenster zur Verfügung, in dem der Benutzer seinen Benutzernamen und sein Passwort eingibt.
 * Wenn die Eingaben korrekt sind, wird das Hauptmenü geöffnet.
 */

public class GUILogin extends JFrame {
    private javax.swing.JPanel contentPane;
    private JLabel labelLogin;
    private JLabel labelBenutzer;
    private JTextField textFieldBenutzername;
    private JLabel labelPasswort;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton abbrechenButton;

    /**
     * Konstruktor für das Login-Fenster. Initialisiert die GUI-Komponenten und die Layout-Konfiguration.
     */

    public GUILogin() {
        //Frame Einstellungen
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

        // Button-ActionListener für Login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String validusername = "dottoreamore";
                String validpasswort = "1234";
                String enteredUsername = textFieldBenutzername.getText();
                String enteredPassword = new String(passwordField.getPassword());

                // SwingWorker für Login-Prozess im Hintergrund
                SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
                    @Override
                    protected Boolean doInBackground() throws Exception {
                        // Überprüfung des Logins im Hintergrund
                        return validusername.equals(enteredUsername) && validpasswort.equals(enteredPassword);
                    }

                    @Override
                    protected void done() {
                        try {
                            if (get()) {
                                System.out.println("Login erfolgreich");
                                dispose();
                                GUIMenu Menu = new GUIMenu();
                                Menu.setLocationRelativeTo(null);
                                Menu.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(null, "Login fehlgeschlagen!", "Fehler", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                };

                // Starte den Worker
                worker.execute();
            }
        });


        //Abbrechen Button schließt das Programm
        abbrechenButton.addActionListener(e -> System.exit(0));
        //Fenster sichtbar machen
        setVisible(true);
    }
}





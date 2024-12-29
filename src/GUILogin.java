import javax.swing.*;

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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(contentPane);
        pack();
    }


}

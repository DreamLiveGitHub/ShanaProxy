package atmatm6.proxylauncher.dialogs;

import atmatm6.proxylauncher.utils.LoginUtils;
import org.apache.http.auth.InvalidCredentialsException;

import javax.swing.*;
import java.awt.event.*;

public class LoginDialog extends JFrame {
    private JPanel contentPane;
    public JButton buttonOK;
    private JButton buttonCancel;
    public JTextField emailField;
    public JPasswordField passwordField;
    private JLabel errorField;
    private JButton launcherButton;
    private LoginUtils loginutils;
    public boolean closing;
    /* Maybe add more later, currently using boolean
    * true - Exit
    * false - Launching
    */

    public LoginDialog() {
        setTitle("Login to ProxyLauncher");
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        launcherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LauncherGUI lg = new LauncherGUI();
                lg.pack();
                lg.setVisible(true);
            }
        });
    }

    public LoginUtils getLoginutils() {
        return loginutils;
    }

    public void setLoginutils(LoginUtils loginutils) {
        this.loginutils = loginutils;
    }

    private void onOK(){
        String s = new String(passwordField.getPassword());
        loginutils.setLoginDetails(emailField.getText(),s);
        try {
            if (loginutils.authenticate()){
                closing = false;
                dispose();
            }
        } catch (InvalidCredentialsException e) {
            errorField.setText("Sorry, invalid connection credentials, please try again.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onCancel() {
        System.out.println("Canceled the login, shutting down");
        closing = true;
        dispose();
    }

    public static void main(String[] args) {
        LoginDialog dialog = new LoginDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

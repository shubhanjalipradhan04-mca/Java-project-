import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleLoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel statusLabel;

    public SimpleLoginScreen() {
        setTitle("Simple Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Login panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2, 10, 10));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginActionListener());

        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);

        add(loginPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.equals("student") && password.equals("1234")) {
                JOptionPane.showMessageDialog(SimpleLoginScreen.this, 
                    "Welcome! Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                statusLabel.setText("Login Successful! ✓");
                statusLabel.setForeground(Color.GREEN);
            } else {
                JOptionPane.showMessageDialog(SimpleLoginScreen.this, 
                    "Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                statusLabel.setText("Login Failed! ✗");
                statusLabel.setForeground(Color.RED);
                usernameField.setText("");
                passwordField.setText("");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SimpleLoginScreen().setVisible(true);
        });
    }
}
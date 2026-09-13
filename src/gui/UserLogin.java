package gui;

import database.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UserLogin extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton, backButton;

    public UserLogin() {
        setTitle("User Login");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel main = new JPanel(new GridBagLayout());
        main.setBackground(new Color(235, 242, 250));

        JPanel card = new JPanel(null);
        card.setPreferredSize(new Dimension(550, 480));
        card.setBackground(Color.WHITE);

        JLabel title = new JLabel("USER LOGIN", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(25, 55, 90));
        title.setBounds(100, 45, 350, 40);
        card.add(title);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 15));
        emailLabel.setBounds(70, 125, 100, 30);
        card.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(180, 125, 270, 35);
        card.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 15));
        passwordLabel.setBounds(70, 180, 100, 30);
        card.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(180, 180, 270, 35);
        card.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(70, 250, 130, 40);
        styleButton(loginButton, new Color(45, 85, 130));
        card.add(loginButton);

        registerButton = new JButton("Register");
        registerButton.setBounds(210, 250, 130, 40);
        styleButton(registerButton, new Color(55, 140, 100));
        card.add(registerButton);

        backButton = new JButton("Back");
        backButton.setBounds(350, 250, 100, 40);
        styleButton(backButton, new Color(100, 100, 100));
        card.add(backButton);

        loginButton.addActionListener(e -> loginUser());

        registerButton.addActionListener(e -> {
            new UserRegister().setVisible(true);
            dispose();
        });

        backButton.addActionListener(e -> {
            new MainLogin().setVisible(true);
            dispose();
        });

        main.add(card);
        add(main);
        setVisible(true);
    }

    private void styleButton(JButton b, Color c) {
        b.setFont(new Font("Arial", Font.BOLD, 14));
        b.setBackground(c);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
    }

    private void loginUser() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter email and password!");
            return;
        }

        String sql = "SELECT user_id, name FROM users " +
                     "WHERE email = ? AND password = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");

                JOptionPane.showMessageDialog(this,
                        "Login Successful!\nWelcome " + name + "!");

                new UserDashboard(
                        rs.getInt("user_id"), name
                ).setVisible(true);

                dispose();

            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid email or password!",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Database Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserLogin());
    }
}
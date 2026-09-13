package gui;

import database.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UserRegister extends JFrame {

    private JTextField nameField, emailField, phoneField;
    private JPasswordField passwordField;
    private JButton registerButton, backButton;

    public UserRegister() {
        setTitle("User Registration");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel main = new JPanel(new GridBagLayout());
        main.setBackground(new Color(235, 242, 250));

        JPanel card = new JPanel(null);
        card.setPreferredSize(new Dimension(600, 570));
        card.setBackground(Color.WHITE);

        JLabel title = new JLabel("USER REGISTRATION", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 27));
        title.setForeground(new Color(25, 55, 90));
        title.setBounds(100, 35, 400, 40);
        card.add(title);

        JLabel nameLabel = new JLabel("Name:");
        styleLabel(nameLabel);
        nameLabel.setBounds(80, 105, 100, 30);
        card.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(190, 105, 300, 35);
        card.add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        styleLabel(emailLabel);
        emailLabel.setBounds(80, 160, 100, 30);
        card.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(190, 160, 300, 35);
        card.add(emailField);

        JLabel phoneLabel = new JLabel("Phone:");
        styleLabel(phoneLabel);
        phoneLabel.setBounds(80, 215, 100, 30);
        card.add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(190, 215, 300, 35);
        card.add(phoneField);

        JLabel passwordLabel = new JLabel("Password:");
        styleLabel(passwordLabel);
        passwordLabel.setBounds(80, 270, 100, 30);
        card.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(190, 270, 300, 35);
        card.add(passwordField);

        registerButton = new JButton("Register");
        registerButton.setBounds(130, 350, 150, 45);
        styleButton(registerButton, new Color(55, 140, 100));
        card.add(registerButton);

        backButton = new JButton("Back to Login");
        backButton.setBounds(300, 350, 160, 45);
        styleButton(backButton, new Color(45, 85, 130));
        card.add(backButton);

        registerButton.addActionListener(e -> registerUser());

        backButton.addActionListener(e -> {
            new UserLogin().setVisible(true);
            dispose();
        });

        main.add(card);
        add(main);
        setVisible(true);
    }

    private void styleLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setForeground(new Color(70, 70, 70));
    }

    private void styleButton(JButton b, Color c) {
        b.setFont(new Font("Arial", Font.BOLD, 14));
        b.setBackground(c);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
    }

    private void registerUser() {

        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (name.isEmpty() || email.isEmpty() ||
            phone.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Please fill all fields!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid email address!",
                    "Invalid Email",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!phone.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this,
                    "Phone number must contain exactly 10 digits!",
                    "Invalid Phone",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this,
                    "Password must contain at least 6 characters!",
                    "Invalid Password",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection con = DBConnection.getConnection()) {

            String checkSql =
                    "SELECT user_id FROM users WHERE email = ?";

            try (PreparedStatement ps = con.prepareStatement(checkSql)) {
                ps.setString(1, email);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(this,
                            "Email already registered!",
                            "Registration Failed",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            String sql =
                    "INSERT INTO users(name, email, phone, password) " +
                    "VALUES (?, ?, ?, ?)";

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, phone);
                ps.setString(4, password);

                if (ps.executeUpdate() > 0) {

                    JOptionPane.showMessageDialog(this,
                            "Registration Successful!\nYou can now login.",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);

                    new UserLogin().setVisible(true);
                    dispose();

                } else {
                    JOptionPane.showMessageDialog(this,
                            "Registration Failed!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Database Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserRegister());
    }
}
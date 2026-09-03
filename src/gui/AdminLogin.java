package gui;

import service.AdminService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminLogin extends JFrame implements ActionListener {

    JLabel titleLabel, emailLabel, passwordLabel;
    JTextField emailField;
    JPasswordField passwordField;

    JButton loginButton, clearButton;

    AdminService service;

    public AdminLogin() {

        // Frame Settings
        setTitle("Admin Login");
        setSize(500, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        service = new AdminService();

        // Title
        titleLabel = new JLabel("ADMIN LOGIN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(160, 40, 250, 35);
        add(titleLabel);

        // Email
        emailLabel = new JLabel("Email:");
        emailLabel.setBounds(80, 120, 100, 25);

        emailField = new JTextField();
        emailField.setBounds(180, 120, 220, 30);

        add(emailLabel);
        add(emailField);

        // Password
        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(80, 170, 100, 25);

        passwordField = new JPasswordField();
        passwordField.setBounds(180, 170, 220, 30);

        add(passwordLabel);
        add(passwordField);

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setBounds(130, 240, 100, 35);

        // Clear Button
        clearButton = new JButton("Clear");
        clearButton.setBounds(250, 240, 100, 35);

        loginButton.addActionListener(this);
        clearButton.addActionListener(this);

        add(loginButton);
        add(clearButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // ================= LOGIN =================

        if (e.getSource() == loginButton) {

            String email = emailField.getText().trim();
            String password =
                    new String(passwordField.getPassword());

            // Empty field validation
            if (email.isEmpty() || password.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter email and password!",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            // Email basic validation
            if (!email.contains("@") || !email.contains(".")) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid email!",
                        "Invalid Email",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            // Check login
            boolean result = service.login(email, password);

            if (result) {

                JOptionPane.showMessageDialog(
                        this,
                        "Admin Login Successful!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                // Open Dashboard
                new Dashboard();

                // Close Login Window
                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid email or password!",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );

                passwordField.setText("");
            }
        }

        // ================= CLEAR =================

        else if (e.getSource() == clearButton) {

            emailField.setText("");
            passwordField.setText("");
        }
    }

    public static void main(String[] args) {
        new AdminLogin();
    }
}
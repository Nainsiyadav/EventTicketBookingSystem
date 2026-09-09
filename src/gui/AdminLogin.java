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

        setTitle("Admin Login");
        setSize(500, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        service = new AdminService();

        // ================= TITLE =================
        titleLabel = new JLabel("ADMIN LOGIN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(new Color(25, 55, 90));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(100, 40, 300, 40);
        add(titleLabel);

        // ================= EMAIL =================
        emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 15));
        emailLabel.setForeground(new Color(45, 55, 70));
        emailLabel.setBounds(80, 120, 100, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(180, 120, 220, 30);
        add(emailField);

        // ================= PASSWORD =================
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 15));
        passwordLabel.setForeground(new Color(45, 55, 70));
        passwordLabel.setBounds(80, 170, 100, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(180, 170, 220, 30);
        add(passwordField);

        // ================= LOGIN BUTTON =================
        loginButton = new JButton("Login");
        loginButton.setBounds(130, 240, 100, 35);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(45, 85, 130));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);

        // ================= CLEAR BUTTON =================
        clearButton = new JButton("Clear");
        clearButton.setBounds(250, 240, 100, 35);
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.setBackground(new Color(190, 70, 70));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setBorderPainted(false);

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

            if (email.isEmpty() || password.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter email and password!",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            if (!email.contains("@") || !email.contains(".")) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid email!",
                        "Invalid Email",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            boolean result = service.login(email, password);

            if (result) {

                JOptionPane.showMessageDialog(
                        this,
                        "Admin Login Successful!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                new Dashboard();

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
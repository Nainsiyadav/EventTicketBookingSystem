package gui;

import database.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserLogin extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    private JButton loginButton;
    private JButton registerButton;

    public UserLogin() {

        setTitle("User Login");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // ================= TITLE =================

        JLabel titleLabel = new JLabel(
                "USER LOGIN",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 26)
        );

        titleLabel.setForeground(
                new Color(25, 55, 90)
        );

        titleLabel.setBounds(150, 40, 200, 40);

        add(titleLabel);


        // ================= EMAIL =================

        JLabel emailLabel = new JLabel("Email:");

        emailLabel.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        emailLabel.setForeground(
                new Color(70, 70, 70)
        );

        emailLabel.setBounds(80, 120, 100, 30);

        add(emailLabel);

        emailField = new JTextField();

        emailField.setBounds(180, 120, 220, 30);

        add(emailField);


        // ================= PASSWORD =================

        JLabel passwordLabel =
                new JLabel("Password:");

        passwordLabel.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        passwordLabel.setForeground(
                new Color(70, 70, 70)
        );

        passwordLabel.setBounds(80, 170, 100, 30);

        add(passwordLabel);

        passwordField = new JPasswordField();

        passwordField.setBounds(180, 170, 220, 30);

        add(passwordField);


        // ================= LOGIN BUTTON =================

        loginButton = new JButton("Login");

        loginButton.setBounds(150, 230, 100, 35);

        styleButton(
                loginButton,
                new Color(45, 85, 130)
        );

        add(loginButton);


        // ================= REGISTER BUTTON =================

        registerButton = new JButton("Register");

        registerButton.setBounds(260, 230, 100, 35);

        styleButton(
                registerButton,
                new Color(55, 140, 100)
        );

        add(registerButton);


        // ================= LOGIN ACTION =================

        loginButton.addActionListener(e ->
                loginUser()
        );


        // ================= REGISTER ACTION =================

        registerButton.addActionListener(e -> {

            new UserRegister();

            dispose();
        });

        setVisible(true);
    }


    // ================= BUTTON STYLE =================

    private void styleButton(
            JButton button,
            Color color
    ) {

        button.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        button.setBackground(color);
        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }


    // ================= LOGIN METHOD =================

    private void loginUser() {

        String email =
                emailField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );


        if (email.isEmpty() ||
                password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter email and password!"
            );

            return;
        }


        String sql =
                "SELECT user_id, name FROM users " +
                "WHERE email = ? AND password = ?";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs =
                    ps.executeQuery();


            if (rs.next()) {

                String name =
                        rs.getString("name");


                JOptionPane.showMessageDialog(
                        this,
                        "Login Successful!\n" +
                        "Welcome " + name + "!"
                );


                new UserDashboard(
                        rs.getInt("user_id"),
                        name
                ).setVisible(true);

                dispose();


            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid email or password!",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }


        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " +
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // ================= MAIN =================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new UserLogin();

        });
    }
}
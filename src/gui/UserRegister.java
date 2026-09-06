package gui;

import database.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserRegister extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JPasswordField passwordField;

    private JButton registerButton;
    private JButton backButton;

    public UserRegister() {

        setTitle("User Registration");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // ================= TITLE =================

        JLabel titleLabel = new JLabel("USER REGISTRATION");
        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 25)
        );
        titleLabel.setBounds(130, 40, 280, 40);
        add(titleLabel);


        // ================= NAME =================

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(
                new Font("Arial", Font.BOLD, 15)
        );
        nameLabel.setBounds(70, 110, 100, 30);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(180, 110, 230, 30);
        add(nameField);


        // ================= EMAIL =================

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(
                new Font("Arial", Font.BOLD, 15)
        );
        emailLabel.setBounds(70, 160, 100, 30);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(180, 160, 230, 30);
        add(emailField);


        // ================= PHONE =================

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(
                new Font("Arial", Font.BOLD, 15)
        );
        phoneLabel.setBounds(70, 210, 100, 30);
        add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(180, 210, 230, 30);
        add(phoneField);


        // ================= PASSWORD =================

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(
                new Font("Arial", Font.BOLD, 15)
        );
        passwordLabel.setBounds(70, 260, 100, 30);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(180, 260, 230, 30);
        add(passwordField);


        // ================= REGISTER BUTTON =================

        registerButton = new JButton("Register");
        registerButton.setBounds(140, 330, 110, 35);
        add(registerButton);


        // ================= BACK BUTTON =================

        backButton = new JButton("Back to Login");
        backButton.setBounds(260, 330, 130, 35);
        add(backButton);


        // ================= REGISTER ACTION =================

        registerButton.addActionListener(
                e -> registerUser()
        );


        // ================= BACK ACTION =================

        backButton.addActionListener(e -> {

            new UserLogin();

            dispose();
        });


        setVisible(true);
    }


    // =========================
    // REGISTER USER
    // =========================

    private void registerUser() {

        String name =
                nameField.getText().trim();

        String email =
                emailField.getText().trim();

        String phone =
                phoneField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );


        // =========================
        // VALIDATION
        // =========================

        if (name.isEmpty()
                || email.isEmpty()
                || phone.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        // =========================
        // EMAIL VALIDATION
        // =========================

        if (!email.contains("@")
                || !email.contains(".")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid email address!",
                    "Invalid Email",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        // =========================
        // PHONE VALIDATION
        // =========================

        if (!phone.matches("\\d{10}")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Phone number must contain exactly 10 digits!",
                    "Invalid Phone",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        // =========================
        // PASSWORD VALIDATION
        // =========================

        if (password.length() < 6) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password must contain at least 6 characters!",
                    "Invalid Password",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        // =========================
        // CHECK EMAIL
        // =========================

        String checkSql =
                "SELECT user_id FROM users WHERE email = ?";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement checkPs =
                        con.prepareStatement(checkSql)
        ) {

            checkPs.setString(1, email);

            ResultSet rs =
                    checkPs.executeQuery();


            if (rs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Email already registered!",
                        "Registration Failed",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }


        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: "
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        // =========================
        // INSERT USER
        // =========================

        String sql =
                "INSERT INTO users "
                + "(name, email, phone, password) "
                + "VALUES (?, ?, ?, ?)";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, password);


            int result =
                    ps.executeUpdate();


            if (result > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Registration Successful!\n"
                                + "You can now login.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );


                // Go to User Login

                new UserLogin();

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Registration Failed!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }


        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: "
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================
    // MAIN METHOD
    // =========================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> new UserRegister()
        );
    }
}
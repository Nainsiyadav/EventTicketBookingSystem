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

    JButton loginButton, clearButton, backButton;

    AdminService service;

    public AdminLogin() {

        setTitle("Admin Login");

        // Full laptop screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ================= MAIN PANEL =================

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(242, 244, 247));

        setContentPane(mainPanel);

        // ================= WHITE CARD =================

        JPanel card = new RoundedPanel(30);
        card.setPreferredSize(new Dimension(550, 450));
        card.setBackground(Color.WHITE);
        card.setLayout(null);

        mainPanel.add(card);

        // ================= SERVICE =================

        service = new AdminService();

        // ================= TITLE =================

        titleLabel = new JLabel("ADMIN LOGIN");

        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(25, 55, 90));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        titleLabel.setBounds(100, 40, 350, 40);

        card.add(titleLabel);

        // ================= EMAIL =================

        emailLabel = new JLabel("Email:");

        emailLabel.setFont(new Font("Arial", Font.BOLD, 15));
        emailLabel.setForeground(new Color(45, 55, 70));

        emailLabel.setBounds(70, 125, 100, 25);

        card.add(emailLabel);

        emailField = new JTextField();

        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setBounds(170, 120, 300, 35);

        card.add(emailField);

        // ================= PASSWORD =================

        passwordLabel = new JLabel("Password:");

        passwordLabel.setFont(new Font("Arial", Font.BOLD, 15));
        passwordLabel.setForeground(new Color(45, 55, 70));

        passwordLabel.setBounds(70, 180, 100, 25);

        card.add(passwordLabel);

        passwordField = new JPasswordField();

        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBounds(170, 175, 300, 35);

        card.add(passwordField);

        // ================= LOGIN BUTTON =================

        loginButton = createButton(
                "Login",
                new Color(45, 85, 130)
        );

        loginButton.setBounds(80, 250, 120, 40);

        card.add(loginButton);

        // ================= CLEAR BUTTON =================

        clearButton = createButton(
                "Clear",
                new Color(120, 120, 120)
        );

        clearButton.setBounds(215, 250, 120, 40);

        card.add(clearButton);

        // ================= BACK BUTTON =================

        backButton = createButton(
                "← Back",
                new Color(90, 90, 90)
        );

        backButton.setBounds(350, 250, 120, 40);

        card.add(backButton);

        // ================= ACTION LISTENERS =================

        loginButton.addActionListener(this);
        clearButton.addActionListener(this);
        backButton.addActionListener(this);

        setVisible(true);
    }

    // =====================================================
    // BUTTON CREATION
    // =====================================================

    private JButton createButton(String text, Color background) {

        JButton button = new JButton(text);

        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(background);
        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        return button;
    }

    // =====================================================
    // BUTTON ACTIONS
    // =====================================================

    @Override
    public void actionPerformed(ActionEvent e) {

        // ================= LOGIN =================

        if (e.getSource() == loginButton) {

            String email = emailField.getText().trim();

            String password =
                    new String(passwordField.getPassword());

            // Empty validation

            if (email.isEmpty() || password.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter email and password!",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            // Email validation

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

                new Dashboard().setVisible(true);

                // Close Admin Login

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

        // ================= BACK =================

        else if (e.getSource() == backButton) {

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Go back to Main Login?",
                    "Back",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {

                new MainLogin().setVisible(true);

                dispose();
            }
        }
    }

    // =====================================================
    // ROUNDED CARD
    // =====================================================

    class RoundedPanel extends JPanel {

        private int radius;

        RoundedPanel(int radius) {

            this.radius = radius;

            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(getBackground());

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    radius,
                    radius
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // =====================================================
    // MAIN
    // =====================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new AdminLogin();
        });
    }
}
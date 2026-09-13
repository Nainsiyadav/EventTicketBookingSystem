package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame implements ActionListener {

    JLabel titleLabel, welcomeLabel;

    JButton adminButton, userButton, eventButton,
            bookingButton, paymentButton, reportButton,
            backButton, logoutButton;

    public Dashboard() {

        setTitle("Event Ticket Booking System - Admin Dashboard");

        // Full laptop screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // =====================================================
        // MAIN PANEL
        // =====================================================

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(242, 244, 247));

        setContentPane(mainPanel);

        // =====================================================
        // CENTER CARD
        // =====================================================

        JPanel card = new RoundedPanel(30);

        card.setPreferredSize(new Dimension(850, 600));
        card.setBackground(Color.WHITE);
        card.setLayout(null);

        // Automatically center card
        mainPanel.add(card);

        // =====================================================
        // TITLE
        // =====================================================

        titleLabel = new JLabel(
                "EVENT TICKET BOOKING SYSTEM"
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 26)
        );

        titleLabel.setForeground(
                new Color(25, 55, 90)
        );

        titleLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        titleLabel.setBounds(175, 35, 500, 40);

        card.add(titleLabel);

        // =====================================================
        // WELCOME
        // =====================================================

        welcomeLabel = new JLabel(
                "Welcome, Admin!"
        );

        welcomeLabel.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        welcomeLabel.setForeground(
                new Color(70, 70, 70)
        );

        welcomeLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        welcomeLabel.setBounds(325, 85, 200, 30);

        card.add(welcomeLabel);

        // =====================================================
        // ADMIN MANAGEMENT
        // =====================================================

        adminButton = createButton(
                "Admin Management",
                new Color(45, 85, 130)
        );

        adminButton.setBounds(80, 150, 200, 50);

        card.add(adminButton);

        // =====================================================
        // USER MANAGEMENT
        // =====================================================

        userButton = createButton(
                "User Management",
                new Color(55, 140, 100)
        );

        userButton.setBounds(320, 150, 200, 50);

        card.add(userButton);

        // =====================================================
        // EVENT MANAGEMENT
        // =====================================================

        eventButton = createButton(
                "Event Management",
                new Color(120, 80, 150)
        );

        eventButton.setBounds(560, 150, 200, 50);

        card.add(eventButton);

        // =====================================================
        // BOOKING MANAGEMENT
        // =====================================================

        bookingButton = createButton(
                "Booking Management",
                new Color(210, 130, 50)
        );

        bookingButton.setBounds(80, 240, 200, 50);

        card.add(bookingButton);

        // =====================================================
        // PAYMENT MANAGEMENT
        // =====================================================

        paymentButton = createButton(
                "Payment Management",
                new Color(40, 130, 130)
        );

        paymentButton.setBounds(320, 240, 200, 50);

        card.add(paymentButton);

        // =====================================================
        // REPORTS
        // =====================================================

        reportButton = createButton(
                "Reports",
                new Color(70, 90, 120)
        );

        reportButton.setBounds(560, 240, 200, 50);

        card.add(reportButton);

        // =====================================================
        // BACK BUTTON
        // =====================================================

        backButton = createButton(
                "← Back",
                new Color(90, 90, 90)
        );

        backButton.setBounds(190, 360, 180, 50);

        card.add(backButton);

        // =====================================================
        // LOGOUT BUTTON
        // =====================================================

        logoutButton = createButton(
                "Logout",
                new Color(190, 70, 70)
        );

        logoutButton.setBounds(470, 360, 180, 50);

        card.add(logoutButton);

        // =====================================================
        // SHOW
        // =====================================================

        setVisible(true);
    }

    // =========================================================
    // CREATE BUTTON
    // =========================================================

    private JButton createButton(
            String text,
            Color background
    ) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        button.setBackground(background);

        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.addActionListener(this);

        return button;
    }

    // =========================================================
    // BUTTON ACTIONS
    // =========================================================

    @Override
    public void actionPerformed(ActionEvent e) {

        // Admin Management
        if (e.getSource() == adminButton) {

            new AdminForm().setVisible(true);
            dispose();
        }

        // User Management
        else if (e.getSource() == userButton) {

            new UserForm().setVisible(true);
            dispose();
        }

        // Event Management
        else if (e.getSource() == eventButton) {

            new EventForm().setVisible(true);
            dispose();
        }

        // Booking Management
        else if (e.getSource() == bookingButton) {

            new BookingForm().setVisible(true);
            dispose();
        }

        // Payment Management
        else if (e.getSource() == paymentButton) {

            new PaymentForm().setVisible(true);
            dispose();
        }

        // Reports
        else if (e.getSource() == reportButton) {

            new ReportForm().setVisible(true);
            dispose();
        }

        // =====================================================
        // BACK
        // =====================================================

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

        // =====================================================
        // LOGOUT
        // =====================================================

        else if (e.getSource() == logoutButton) {

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {

                new MainLogin().setVisible(true);
                dispose();
            }
        }
    }

    // =========================================================
    // ROUNDED CARD
    // =========================================================

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

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new Dashboard();
        });
    }
}
package gui;

import javax.swing.*;
import java.awt.*;

public class UserDashboard extends JFrame {

    private int userId;
    private String userName;

    private JButton viewEventsButton;
    private JButton myBookingsButton;
    private JButton logoutButton;

    // ================= CONSTRUCTOR WITH USER ID + NAME =================

    public UserDashboard(int userId, String userName) {

        this.userId = userId;
        this.userName = userName;

        createDashboard();
    }

    // ================= CONSTRUCTOR WITH ONLY USER ID =================
    // Used when returning from MyBookingsForm

    public UserDashboard(int userId) {

        this.userId = userId;
        this.userName = "User";

        createDashboard();
    }

    // ================= CREATE DASHBOARD =================

    private void createDashboard() {

        setTitle("Event Ticket Booking System - User Dashboard");

        // Full laptop screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ================= MAIN PANEL =================

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(242, 244, 247));

        setContentPane(mainPanel);

        // ================= WHITE CARD =================

        JPanel card = new RoundedPanel(30);

        card.setPreferredSize(new Dimension(850, 600));
        card.setBackground(Color.WHITE);

        card.setLayout(null);

        mainPanel.add(card);

        // ================= TITLE =================

        JLabel titleLabel = new JLabel(
                "EVENT TICKET BOOKING SYSTEM",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        titleLabel.setForeground(
                new Color(25, 55, 90)
        );

        titleLabel.setBounds(
                100, 50, 650, 45
        );

        card.add(titleLabel);

        // ================= WELCOME =================

        JLabel welcomeLabel = new JLabel(
                "Welcome, " + userName + "!",
                SwingConstants.CENTER
        );

        welcomeLabel.setFont(
                new Font("Arial", Font.BOLD, 20)
        );

        welcomeLabel.setForeground(
                new Color(70, 70, 70)
        );

        welcomeLabel.setBounds(
                150, 105, 550, 35
        );

        card.add(welcomeLabel);

        // ================= VIEW EVENTS =================

        viewEventsButton = new JButton("View Events");

        viewEventsButton.setBounds(
                100, 190, 280, 60
        );

        styleButton(
                viewEventsButton,
                new Color(45, 85, 130)
        );

        card.add(viewEventsButton);

        // ================= MY BOOKINGS =================

        myBookingsButton = new JButton("My Bookings");

        myBookingsButton.setBounds(
                470, 190, 280, 60
        );

        styleButton(
                myBookingsButton,
                new Color(55, 140, 100)
        );

        card.add(myBookingsButton);

        // ================= LOGOUT =================

        logoutButton = new JButton("Logout");

        logoutButton.setBounds(
                285, 300, 280, 60
        );

        styleButton(
                logoutButton,
                new Color(190, 70, 70)
        );

        card.add(logoutButton);

        // ================= VIEW EVENTS ACTION =================

        viewEventsButton.addActionListener(e -> {

            new UserEventForm(userId).setVisible(true);

            dispose();
        });

        // ================= MY BOOKINGS ACTION =================

        myBookingsButton.addActionListener(e -> {

            new MyBookingsForm(userId).setVisible(true);

            dispose();
        });

        // ================= LOGOUT ACTION =================

        logoutButton.addActionListener(e -> {

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
        });

        setVisible(true);
    }

    // ================= BUTTON STYLE =================

    private void styleButton(
            JButton button,
            Color color
    ) {

        button.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        button.setBackground(color);
        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }

    // ================= ROUNDED CARD =================

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
                    getWidth() - 1,
                    getHeight() - 1,
                    radius,
                    radius
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // ================= MAIN METHOD =================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new UserDashboard(
                    1,
                    "Test User"
            );

        });
    }
}
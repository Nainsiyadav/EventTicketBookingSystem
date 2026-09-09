package gui;

import javax.swing.*;
import java.awt.*;

public class UserDashboard extends JFrame {

    private int userId;
    private String userName;

    private JButton viewEventsButton;
    private JButton myBookingsButton;
    private JButton logoutButton;

    public UserDashboard(int userId, String userName) {

        this.userId = userId;
        this.userName = userName;

        setTitle("Event Ticket Booking System - User Dashboard");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(null);

        // ================= TITLE =================

        JLabel titleLabel = new JLabel(
                "EVENT TICKET BOOKING SYSTEM",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 26)
        );

        titleLabel.setForeground(
                new Color(25, 55, 90)
        );

        titleLabel.setBounds(100, 40, 550, 40);

        add(titleLabel);


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

        welcomeLabel.setBounds(200, 90, 350, 35);

        add(welcomeLabel);


        // ================= VIEW EVENTS =================

        viewEventsButton = new JButton("View Events");

        viewEventsButton.setBounds(
                100, 170, 230, 55
        );

        styleButton(
                viewEventsButton,
                new Color(45, 85, 130)
        );

        add(viewEventsButton);


        // ================= MY BOOKINGS =================

        myBookingsButton = new JButton("My Bookings");

        myBookingsButton.setBounds(
                400, 170, 230, 55
        );

        styleButton(
                myBookingsButton,
                new Color(55, 140, 100)
        );

        add(myBookingsButton);


        // ================= LOGOUT =================

        logoutButton = new JButton("Logout");

        logoutButton.setBounds(
                250, 260, 230, 55
        );

        styleButton(
                logoutButton,
                new Color(190, 70, 70)
        );

        add(logoutButton);


        // ================= VIEW EVENTS ACTION =================

        viewEventsButton.addActionListener(e -> {

            new UserEventForm(userId).setVisible(true);

        });


        // ================= MY BOOKINGS ACTION =================

        myBookingsButton.addActionListener(e -> {

            new MyBookingsForm(userId).setVisible(true);

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

    }


    // ================= BUTTON STYLE =================

    private void styleButton(JButton button, Color color) {

        button.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        button.setBackground(color);
        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }


    // ================= MAIN METHOD =================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new UserDashboard(
                    1,
                    "Test User"
            ).setVisible(true);

        });

    }
}
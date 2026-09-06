package gui;

import javax.swing.*;
import java.awt.*;

public class UserDashboard extends JFrame {

    private int userId;
    private String userName;

    private JButton viewEventsButton;
    private JButton bookingButton;
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

        welcomeLabel.setBounds(200, 90, 350, 35);

        add(welcomeLabel);


        // ================= VIEW EVENTS =================

        viewEventsButton =
                new JButton("View Events");

        viewEventsButton.setBounds(
                100, 170, 230, 55
        );

        add(viewEventsButton);


        // ================= BOOK TICKET =================

        bookingButton =
                new JButton("Book Ticket");

        bookingButton.setBounds(
                400, 170, 230, 55
        );

        add(bookingButton);


        // ================= MY BOOKINGS =================

        myBookingsButton =
                new JButton("My Bookings");

        myBookingsButton.setBounds(
                100, 260, 230, 55
        );

        add(myBookingsButton);


        // ================= LOGOUT =================

        logoutButton =
                new JButton("Logout");

        logoutButton.setBounds(
                400, 260, 230, 55
        );

        add(logoutButton);


        // ================= VIEW EVENTS ACTION =================

            viewEventsButton.addActionListener(e -> {

           new UserEventForm(userId).setVisible(true);

         });


        // ================= BOOKING ACTION =================

        bookingButton.addActionListener(e -> {

            try {

                new BookingForm();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Unable to open Booking Management.\n"
                                + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

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

                new UserLogin();

                dispose();
            }

        });

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
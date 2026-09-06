package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame implements ActionListener {

    JLabel titleLabel;
    JLabel welcomeLabel;

    JButton adminButton;
    JButton userButton;
    JButton eventButton;
    JButton bookingButton;
    JButton paymentButton;
    JButton reportButton;
    JButton logoutButton;

    public Dashboard() {

        // ================= FRAME =================

        setTitle("Event Ticket Booking System - Dashboard");
        setSize(850, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ================= TITLE =================

        titleLabel = new JLabel("EVENT TICKET BOOKING SYSTEM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setBounds(220, 30, 500, 40);

        add(titleLabel);

        // ================= WELCOME =================

        welcomeLabel = new JLabel("Welcome, Admin!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setBounds(350, 85, 200, 30);

        add(welcomeLabel);

        // ================= ADMIN =================

        adminButton = new JButton("Admin Management");
        adminButton.setBounds(80, 150, 200, 50);
        adminButton.addActionListener(this);

        add(adminButton);

        // ================= USER =================

        userButton = new JButton("User Management");
        userButton.setBounds(320, 150, 200, 50);
        userButton.addActionListener(this);

        add(userButton);

        // ================= EVENT =================

        eventButton = new JButton("Event Management");
        eventButton.setBounds(560, 150, 200, 50);
        eventButton.addActionListener(this);

        add(eventButton);

        // ================= BOOKING =================

        bookingButton = new JButton("Booking Management");
        bookingButton.setBounds(80, 240, 200, 50);
        bookingButton.addActionListener(this);

        add(bookingButton);

        // ================= PAYMENT =================

        paymentButton = new JButton("Payment Management");
        paymentButton.setBounds(320, 240, 200, 50);
        paymentButton.addActionListener(this);

        add(paymentButton);

        // ================= REPORT =================

        reportButton = new JButton("Reports");
        reportButton.setBounds(560, 240, 200, 50);
        reportButton.addActionListener(this);

        add(reportButton);

        // ================= LOGOUT =================

        logoutButton = new JButton("Logout");
        logoutButton.setBounds(325, 360, 200, 50);
        logoutButton.addActionListener(this);

        add(logoutButton);

        // ================= FRAME VISIBLE =================

        setVisible(true);
    }

    // ================= BUTTON ACTIONS =================

    @Override
    public void actionPerformed(ActionEvent e) {

        // ADMIN MANAGEMENT
        if (e.getSource() == adminButton) {

            new AdminForm();
        }

        // USER MANAGEMENT
        else if (e.getSource() == userButton) {

            new UserForm();
        }

        // EVENT MANAGEMENT
        else if (e.getSource() == eventButton) {

            new EventForm();
        }

        // BOOKING MANAGEMENT
        else if (e.getSource() == bookingButton) {

            new BookingForm();
        }

        // PAYMENT MANAGEMENT
        else if (e.getSource() == paymentButton) {

            new PaymentForm();
        }

        // REPORTS
            else if (e.getSource() == reportButton) {

        System.out.println("Reports button clicked!");

        new ReportForm();

        System.out.println("ReportForm opened!");
            }

        // LOGOUT
        else if (e.getSource() == logoutButton) {

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {

                dispose();

                new AdminLogin();
            }
        }
    }

    // ================= MAIN =================

    public static void main(String[] args) {

        new Dashboard();
    }
}
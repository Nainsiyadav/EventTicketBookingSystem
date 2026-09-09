package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame implements ActionListener {

    JLabel titleLabel, welcomeLabel;

    JButton adminButton, userButton, eventButton,
            bookingButton, paymentButton, reportButton, logoutButton;

    public  Dashboard() {

        setTitle("Event Ticket Booking System - Dashboard");
        setSize(850, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ================= TITLE =================

        titleLabel = new JLabel("EVENT TICKET BOOKING SYSTEM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(new Color(25, 55, 90));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(175, 30, 500, 40);
        add(titleLabel);

        // ================= WELCOME =================

        welcomeLabel = new JLabel("Welcome, Admin!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setForeground(new Color(70, 70, 70));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setBounds(325, 85, 200, 30);
        add(welcomeLabel);

        // ================= ADMIN =================

        adminButton = new JButton("Admin Management");
        adminButton.setBounds(80, 150, 200, 50);
        adminButton.setBackground(new Color(45, 85, 130));
        adminButton.setForeground(Color.WHITE);
        adminButton.addActionListener(this);
        add(adminButton);

        // ================= USER =================

        userButton = new JButton("User Management");
        userButton.setBounds(320, 150, 200, 50);
        userButton.setBackground(new Color(55, 140, 100));
        userButton.setForeground(Color.WHITE);
        userButton.addActionListener(this);
        add(userButton);

        // ================= EVENT =================

        eventButton = new JButton("Event Management");
        eventButton.setBounds(560, 150, 200, 50);
        eventButton.setBackground(new Color(120, 80, 150));
        eventButton.setForeground(Color.WHITE);
        eventButton.addActionListener(this);
        add(eventButton);

        // ================= BOOKING =================

        bookingButton = new JButton("Booking Management");
        bookingButton.setBounds(80, 240, 200, 50);
        bookingButton.setBackground(new Color(210, 130, 50));
        bookingButton.setForeground(Color.WHITE);
        bookingButton.addActionListener(this);
        add(bookingButton);

        // ================= PAYMENT =================

        paymentButton = new JButton("Payment Management");
        paymentButton.setBounds(320, 240, 200, 50);
        paymentButton.setBackground(new Color(40, 130, 130));
        paymentButton.setForeground(Color.WHITE);
        paymentButton.addActionListener(this);
        add(paymentButton);

        // ================= REPORT =================

        reportButton = new JButton("Reports");
        reportButton.setBounds(560, 240, 200, 50);
        reportButton.setBackground(new Color(70, 90, 120));
        reportButton.setForeground(Color.WHITE);
        reportButton.addActionListener(this);
        add(reportButton);

        // ================= LOGOUT =================

        logoutButton = new JButton("Logout");
        logoutButton.setBounds(325, 360, 200, 50);
        logoutButton.setBackground(new Color(190, 70, 70));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.addActionListener(this);
        add(logoutButton);

        setVisible(true);
    }

    // ================= BUTTON ACTIONS =================

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == adminButton) {
            new AdminForm();
        }

        else if (e.getSource() == userButton) {
            new UserForm();
        }

        else if (e.getSource() == eventButton) {
            new EventForm();
        }

        else if (e.getSource() == bookingButton) {
            new BookingForm();
        }

        else if (e.getSource() == paymentButton) {
            new PaymentForm();
        }

        else if (e.getSource() == reportButton) {

            System.out.println("Reports button clicked!");
            new ReportForm();
            System.out.println("ReportForm opened!");
        }

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
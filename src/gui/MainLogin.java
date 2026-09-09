package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainLogin extends JFrame implements ActionListener {

    JLabel titleLabel, subtitleLabel;
    JButton adminButton, userButton, exitButton;

    public MainLogin() {

        setTitle("Event Ticket Booking & Management System");
        setSize(700, 550);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Background Color
        getContentPane().setBackground(new Color(235, 242, 250));

        // ---------------- TITLE ----------------
        titleLabel = new JLabel(
                "<html><center>EVENT TICKET BOOKING<br>& MANAGEMENT SYSTEM</center></html>"
        );

        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(25, 55, 90));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Center position
        titleLabel.setBounds(75, 60, 550, 80);

        add(titleLabel);

        // ---------------- SUBTITLE ----------------
        subtitleLabel = new JLabel("Welcome! Please select your login");

        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitleLabel.setForeground(new Color(70, 70, 70));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        subtitleLabel.setBounds(150, 155, 400, 30);

        add(subtitleLabel);

        // ---------------- ADMIN BUTTON ----------------
        adminButton = new JButton("Admin Login");

        adminButton.setBounds(225, 220, 250, 50);
        adminButton.setFont(new Font("Arial", Font.BOLD, 16));

        adminButton.setBackground(new Color(45, 85, 130));
        adminButton.setForeground(Color.WHITE);

        adminButton.setFocusPainted(false);
        adminButton.setBorderPainted(false);

        adminButton.addActionListener(this);

        add(adminButton);

        // ---------------- USER BUTTON ----------------
        userButton = new JButton("User Login");

        userButton.setBounds(225, 290, 250, 50);
        userButton.setFont(new Font("Arial", Font.BOLD, 16));

        userButton.setBackground(new Color(55, 140, 100));
        userButton.setForeground(Color.WHITE);

        userButton.setFocusPainted(false);
        userButton.setBorderPainted(false);

        userButton.addActionListener(this);

        add(userButton);

        // ---------------- EXIT BUTTON ----------------
        exitButton = new JButton("Exit");

        exitButton.setBounds(225, 360, 250, 50);
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));

        exitButton.setBackground(new Color(190, 70, 70));
        exitButton.setForeground(Color.WHITE);

        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);

        exitButton.addActionListener(this);

        add(exitButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Admin Login
        if (e.getSource() == adminButton) {

            new AdminLogin().setVisible(true);
            dispose();

        }

        // User Login
        else if (e.getSource() == userButton) {

            new UserLogin().setVisible(true);
            dispose();

        }

        // Exit
        else if (e.getSource() == exitButton) {

            System.exit(0);
        }
    }

    public static void main(String[] args) {

        new MainLogin();
    }
}
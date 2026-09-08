package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainLogin extends JFrame implements ActionListener {

    JLabel titleLabel, subtitleLabel;
    JButton adminButton, userButton, exitButton;

    public MainLogin() {

        setTitle("Event Ticket Booking System");
        setSize(500, 400);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Title
        titleLabel = new JLabel("EVENT TICKET BOOKING SYSTEM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBounds(70, 50, 370, 40);
        add(titleLabel);

        // Subtitle
        subtitleLabel = new JLabel("Welcome! Please select your login");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setBounds(130, 100, 280, 30);
        add(subtitleLabel);

        // Admin Login Button
        adminButton = new JButton("Admin Login");
        adminButton.setBounds(150, 160, 200, 45);
        adminButton.addActionListener(this);
        add(adminButton);

        // User Login Button
        userButton = new JButton("User Login");
        userButton.setBounds(150, 220, 200, 45);
        userButton.addActionListener(this);
        add(userButton);

        // Exit Button
        exitButton = new JButton("Exit");
        exitButton.setBounds(150, 280, 200, 45);
        exitButton.addActionListener(this);
        add(exitButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == adminButton) {

            new AdminLogin().setVisible(true);
            dispose();

        } else if (e.getSource() == userButton) {

            new UserLogin().setVisible(true);
            dispose();

        } else if (e.getSource() == exitButton) {

            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new MainLogin();
    }
}

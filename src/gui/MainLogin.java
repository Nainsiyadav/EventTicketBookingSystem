package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainLogin extends JFrame implements ActionListener {

    JButton adminButton, userButton, exitButton;

    public MainLogin() {

        setTitle("Event Ticket Booking & Management System");

        // Full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // =====================================================
        // MAIN CONTENT PANEL
        // =====================================================

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(242, 244, 247));

        setContentPane(mainPanel);

        // =====================================================
        // WHITE CARD
        // =====================================================

        JPanel card = new RoundedPanel(30);

        card.setPreferredSize(new Dimension(500, 520));
        card.setBackground(Color.WHITE);
        card.setLayout(null);

        // GridBag automatically puts card in exact center
        mainPanel.add(card);

        // =====================================================
        // TITLE
        // =====================================================

        JLabel titleLabel = new JLabel(
            "<html><center>EVENT TICKET BOOKING<br>"
            + "& MANAGEMENT SYSTEM</center></html>"
        );

        titleLabel.setFont(
            new Font("Arial", Font.BOLD, 27)
        );

        titleLabel.setForeground(
            new Color(25, 45, 65)
        );

        titleLabel.setHorizontalAlignment(
            SwingConstants.CENTER
        );

        titleLabel.setBounds(40, 45, 420, 75);

        card.add(titleLabel);

        // =====================================================
        // SUBTITLE
        // =====================================================

        JLabel subtitleLabel =
            new JLabel("Welcome! Please select your login");

        subtitleLabel.setFont(
            new Font("Arial", Font.PLAIN, 17)
        );

        subtitleLabel.setForeground(
            new Color(90, 90, 90)
        );

        subtitleLabel.setHorizontalAlignment(
            SwingConstants.CENTER
        );

        subtitleLabel.setBounds(40, 135, 420, 30);

        card.add(subtitleLabel);

        // =====================================================
        // ADMIN LOGIN BUTTON
        // =====================================================

        adminButton = new RoundedButton("Admin Login");

        adminButton.setBounds(100, 205, 300, 55);

        adminButton.setFont(
            new Font("Arial", Font.BOLD, 17)
        );

        adminButton.setBackground(
            new Color(30, 135, 135)
        );

        adminButton.setForeground(Color.WHITE);

        adminButton.setFocusPainted(false);

        adminButton.addActionListener(this);

        card.add(adminButton);

        // =====================================================
        // USER LOGIN BUTTON
        // =====================================================

        userButton = new RoundedButton("User Login");

        userButton.setBounds(100, 280, 300, 55);

        userButton.setFont(
            new Font("Arial", Font.BOLD, 17)
        );

        userButton.setBackground(
            new Color(55, 155, 105)
        );

        userButton.setForeground(Color.WHITE);

        userButton.setFocusPainted(false);

        userButton.addActionListener(this);

        card.add(userButton);

        // =====================================================
        // EXIT BUTTON
        // =====================================================

        exitButton = new RoundedButton("Exit");

        exitButton.setBounds(100, 355, 300, 55);

        exitButton.setFont(
            new Font("Arial", Font.BOLD, 17)
        );

        exitButton.setBackground(
            new Color(195, 70, 70)
        );

        exitButton.setForeground(Color.WHITE);

        exitButton.setFocusPainted(false);

        exitButton.addActionListener(this);

        card.add(exitButton);

        // =====================================================
        // SHOW WINDOW
        // =====================================================

        setVisible(true);
    }

    // =========================================================
    // BUTTON ACTIONS
    // =========================================================

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
    // ROUNDED BUTTON
    // =========================================================

    class RoundedButton extends JButton {

        private int radius = 20;

        RoundedButton(String text) {

            super(text);

            setContentAreaFilled(false);
            setBorderPainted(false);
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
            new MainLogin();
        });
    }
}
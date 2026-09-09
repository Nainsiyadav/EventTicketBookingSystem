package gui;

import model.Payment;
import service.PaymentService;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserPaymentForm extends JFrame {

    private int userId;
    private int bookingId;

    private PaymentService paymentService;

    private JLabel userIdLabel;
    private JLabel bookingIdLabel;
    private JLabel amountLabel;

    private JComboBox<String> paymentMethodCombo;
    private JComboBox<String> paymentStatusCombo;

    private JButton payButton;
    private JButton cancelButton;

    private double amount;

    // =========================
    // CONSTRUCTOR
    // =========================

    public UserPaymentForm(int userId, int bookingId) {

        this.userId = userId;
        this.bookingId = bookingId;

        paymentService = new PaymentService();

        // Get booking amount
        amount = paymentService.getBookingAmount(bookingId);

        setTitle("Make Payment");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(null);

        // =========================
        // TITLE
        // =========================

        JLabel titleLabel =
                new JLabel("MAKE PAYMENT", SwingConstants.CENTER);

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        titleLabel.setForeground(
                new Color(25, 55, 90)
        );

        titleLabel.setBounds(125, 30, 250, 40);

        add(titleLabel);


        // =========================
        // USER ID
        // =========================

        JLabel userLabel =
                new JLabel("User ID:");

        styleLabel(userLabel);
        userLabel.setBounds(70, 100, 120, 30);

        add(userLabel);

        userIdLabel =
                new JLabel(String.valueOf(userId));

        userIdLabel.setFont(
                new Font("Arial", Font.PLAIN, 15)
        );

        userIdLabel.setBounds(220, 100, 200, 30);

        add(userIdLabel);


        // =========================
        // BOOKING ID
        // =========================

        JLabel bookingLabel =
                new JLabel("Booking ID:");

        styleLabel(bookingLabel);
        bookingLabel.setBounds(70, 140, 120, 30);

        add(bookingLabel);

        bookingIdLabel =
                new JLabel(String.valueOf(bookingId));

        bookingIdLabel.setFont(
                new Font("Arial", Font.PLAIN, 15)
        );

        bookingIdLabel.setBounds(220, 140, 200, 30);

        add(bookingIdLabel);


        // =========================
        // AMOUNT
        // =========================

        JLabel amountTextLabel =
                new JLabel("Amount:");

        styleLabel(amountTextLabel);
        amountTextLabel.setBounds(70, 180, 120, 30);

        add(amountTextLabel);

        amountLabel =
                new JLabel(
                        String.format("₹%.2f", amount)
                );

        amountLabel.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        amountLabel.setForeground(
                new Color(55, 140, 100)
        );

        amountLabel.setBounds(220, 180, 200, 30);

        add(amountLabel);


        // =========================
        // PAYMENT METHOD
        // =========================

        JLabel methodLabel =
                new JLabel("Payment Method:");

        styleLabel(methodLabel);
        methodLabel.setBounds(70, 220, 140, 30);

        add(methodLabel);

        paymentMethodCombo =
                new JComboBox<>(
                        new String[]{
                                "UPI",
                                "Card",
                                "Cash",
                                "Net Banking"
                        }
                );

        paymentMethodCombo.setBounds(
                220, 220, 200, 30
        );

        add(paymentMethodCombo);


        // =========================
        // PAYMENT STATUS
        // =========================

        JLabel statusLabel =
                new JLabel("Payment Status:");

        styleLabel(statusLabel);
        statusLabel.setBounds(70, 260, 140, 30);

        add(statusLabel);

        paymentStatusCombo =
                new JComboBox<>(
                        new String[]{
                                "Paid",
                                "Pending"
                        }
                );

        paymentStatusCombo.setBounds(
                220, 260, 200, 30
        );

        add(paymentStatusCombo);


        // =========================
        // BUTTONS
        // =========================

        payButton =
                new JButton("Pay Now");

        payButton.setBounds(
                140, 310, 100, 35
        );

        styleButton(
                payButton,
                new Color(55, 140, 100)
        );

        add(payButton);


        cancelButton =
                new JButton("Cancel");

        cancelButton.setBounds(
                260, 310, 100, 35
        );

        styleButton(
                cancelButton,
                new Color(190, 70, 70)
        );

        add(cancelButton);


        // =========================
        // PAY BUTTON
        // =========================

        payButton.addActionListener(
                e -> makePayment()
        );


        // =========================
        // CANCEL BUTTON
        // =========================

        cancelButton.addActionListener(
                e -> dispose()
        );
    }


    // =========================
    // LABEL STYLE
    // =========================

    private void styleLabel(JLabel label) {

        label.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        label.setForeground(
                new Color(70, 70, 70)
        );
    }


    // =========================
    // BUTTON STYLE
    // =========================

    private void styleButton(
            JButton button,
            Color color
    ) {

        button.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        button.setBackground(color);
        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }


    // =========================
    // MAKE PAYMENT
    // =========================

    private void makePayment() {

        if (amount == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Booking not found.",
                    "Payment Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        String paymentMethod =
                paymentMethodCombo
                        .getSelectedItem()
                        .toString();


        String paymentStatus =
                paymentStatusCombo
                        .getSelectedItem()
                        .toString();


        // Current date and time
        String paymentDate =
                new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss"
                ).format(new Date());


        Payment payment =
                new Payment();


        payment.setBookingId(
                bookingId
        );

        payment.setAmount(
                amount
        );

        payment.setPaymentMethod(
                paymentMethod
        );

        payment.setPaymentStatus(
                paymentStatus
        );

        payment.setPaymentDate(
                paymentDate
        );


        // =========================
        // SAVE PAYMENT
        // =========================

        boolean result =
                paymentService.addPayment(
                        payment
                );


        if (result) {

            JOptionPane.showMessageDialog(
                    this,
                    "Payment successful!\n"
                    + "Booking ID: "
                    + bookingId
                    + "\nAmount: ₹"
                    + String.format(
                            "%.2f",
                            amount
                    )
                    + "\nStatus: "
                    + paymentStatus,
                    "Payment Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Payment failed.",
                    "Payment Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================
    // MAIN METHOD
    // =========================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> {

                    // Testing only
                    new UserPaymentForm(
                            1,
                            1
                    ).setVisible(true);

                }
        );
    }
}
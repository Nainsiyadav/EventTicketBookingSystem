package gui;

import model.Booking;
import service.BookingService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookingForm extends JFrame implements ActionListener {

    JLabel titleLabel, bookingIdLabel, userIdLabel,
            eventIdLabel, ticketTypeIdLabel,
            quantityLabel, totalAmountLabel;

    JTextField bookingIdField, userIdField,
            eventIdField, ticketTypeIdField,
            quantityField, totalAmountField;

    JButton addButton, updateButton, deleteButton, viewButton;

    BookingService service;

    public BookingForm() {

        setTitle("Booking Management");
        setSize(600, 550);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        service = new BookingService();

        // ================= TITLE =================

        titleLabel = new JLabel("BOOKING MANAGEMENT");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBounds(170, 30, 300, 30);
        add(titleLabel);

        // ================= BOOKING ID =================

        bookingIdLabel = new JLabel("Booking ID:");
        bookingIdLabel.setBounds(80, 90, 120, 25);

        bookingIdField = new JTextField();
        bookingIdField.setBounds(210, 90, 200, 25);

        add(bookingIdLabel);
        add(bookingIdField);

        // ================= USER ID =================

        userIdLabel = new JLabel("User ID:");
        userIdLabel.setBounds(80, 130, 120, 25);

        userIdField = new JTextField();
        userIdField.setBounds(210, 130, 200, 25);

        add(userIdLabel);
        add(userIdField);

        // ================= EVENT ID =================

        eventIdLabel = new JLabel("Event ID:");
        eventIdLabel.setBounds(80, 170, 120, 25);

        eventIdField = new JTextField();
        eventIdField.setBounds(210, 170, 200, 25);

        add(eventIdLabel);
        add(eventIdField);

        // ================= TICKET TYPE =================

        ticketTypeIdLabel = new JLabel("Ticket Type ID:");
        ticketTypeIdLabel.setBounds(80, 210, 120, 25);

        ticketTypeIdField = new JTextField();
        ticketTypeIdField.setBounds(210, 210, 200, 25);

        add(ticketTypeIdLabel);
        add(ticketTypeIdField);

        // ================= QUANTITY =================

        quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(80, 250, 120, 25);

        quantityField = new JTextField();
        quantityField.setBounds(210, 250, 200, 25);

        add(quantityLabel);
        add(quantityField);

        // ================= TOTAL AMOUNT =================

        totalAmountLabel = new JLabel("Total Amount:");
        totalAmountLabel.setBounds(80, 290, 120, 25);

        totalAmountField = new JTextField();
        totalAmountField.setBounds(210, 290, 200, 25);

        add(totalAmountLabel);
        add(totalAmountField);

        // ================= BUTTONS =================

        addButton = new JButton("Add");
        addButton.setBounds(90, 360, 100, 35);

        updateButton = new JButton("Update");
        updateButton.setBounds(200, 360, 100, 35);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(310, 360, 100, 35);

        viewButton = new JButton("View Bookings");
        viewButton.setBounds(200, 410, 130, 35);

        addButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);
        viewButton.addActionListener(this);

        add(addButton);
        add(updateButton);
        add(deleteButton);
        add(viewButton);

        setVisible(true);
    }


    // =====================================================
    // BUTTON ACTIONS
    // =====================================================

    @Override
    public void actionPerformed(ActionEvent e) {

        // ================= ADD =================

        if (e.getSource() == addButton) {

            try {

                if (userIdField.getText().trim().isEmpty()) {
                    showError("Please enter User ID.");
                    return;
                }

                if (eventIdField.getText().trim().isEmpty()) {
                    showError("Please enter Event ID.");
                    return;
                }

                if (ticketTypeIdField.getText().trim().isEmpty()) {
                    showError("Please enter Ticket Type ID.");
                    return;
                }

                if (quantityField.getText().trim().isEmpty()) {
                    showError("Please enter Quantity.");
                    return;
                }

                if (totalAmountField.getText().trim().isEmpty()) {
                    showError("Please enter Total Amount.");
                    return;
                }

                int userId =
                        Integer.parseInt(userIdField.getText().trim());

                int eventId =
                        Integer.parseInt(eventIdField.getText().trim());

                int ticketTypeId =
                        Integer.parseInt(ticketTypeIdField.getText().trim());

                int quantity =
                        Integer.parseInt(quantityField.getText().trim());

                double totalAmount =
                        Double.parseDouble(totalAmountField.getText().trim());

                Booking booking = new Booking(
                        userId,
                        eventId,
                        ticketTypeId,
                        quantity,
                        totalAmount
                );

                String result = service.addBooking(booking);

                if (result.startsWith("Booking Added Successfully")) {

                    JOptionPane.showMessageDialog(
                            this,
                            result,
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    clearFields();

                } else {

                    showError(result);
                }

            } catch (NumberFormatException ex) {

                showError(
                        "Please enter valid numbers in User ID, Event ID, " +
                        "Ticket Type ID, Quantity and Total Amount."
                );
            }
        }


        // ================= UPDATE =================

        else if (e.getSource() == updateButton) {

            try {

                if (bookingIdField.getText().trim().isEmpty()) {
                    showError("Please enter Booking ID.");
                    return;
                }

                if (userIdField.getText().trim().isEmpty()) {
                    showError("Please enter User ID.");
                    return;
                }

                if (eventIdField.getText().trim().isEmpty()) {
                    showError("Please enter Event ID.");
                    return;
                }

                if (ticketTypeIdField.getText().trim().isEmpty()) {
                    showError("Please enter Ticket Type ID.");
                    return;
                }

                if (quantityField.getText().trim().isEmpty()) {
                    showError("Please enter Quantity.");
                    return;
                }

                if (totalAmountField.getText().trim().isEmpty()) {
                    showError("Please enter Total Amount.");
                    return;
                }

                int bookingId =
                        Integer.parseInt(bookingIdField.getText().trim());

                int userId =
                        Integer.parseInt(userIdField.getText().trim());

                int eventId =
                        Integer.parseInt(eventIdField.getText().trim());

                int ticketTypeId =
                        Integer.parseInt(ticketTypeIdField.getText().trim());

                int quantity =
                        Integer.parseInt(quantityField.getText().trim());

                double totalAmount =
                        Double.parseDouble(totalAmountField.getText().trim());

                // Password dialog
                JPasswordField passwordField =
                        new JPasswordField();

                int option = JOptionPane.showConfirmDialog(
                        this,
                        passwordField,
                        "Enter Current Password",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

                if (option != JOptionPane.OK_OPTION) {
                    return;
                }

                String password =
                        new String(passwordField.getPassword());

                if (password.trim().isEmpty()) {
                    showError("Password cannot be empty.");
                    return;
                }

                Booking booking = new Booking(
                        userId,
                        eventId,
                        ticketTypeId,
                        quantity,
                        totalAmount
                );

                booking.setBookingId(bookingId);

                String result =
                        service.updateBooking(booking, password);

                if (result.startsWith("Booking Updated Successfully")) {

                    JOptionPane.showMessageDialog(
                            this,
                            result,
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    clearFields();

                } else {

                    showError(result);
                }

            } catch (NumberFormatException ex) {

                showError(
                        "Please enter valid numbers in all numeric fields."
                );
            }
        }


        // ================= DELETE =================

        else if (e.getSource() == deleteButton) {

            try {

                if (bookingIdField.getText().trim().isEmpty()) {
                    showError("Please enter Booking ID.");
                    return;
                }

                int bookingId =
                        Integer.parseInt(
                                bookingIdField.getText().trim()
                        );

                // Password dialog
                JPasswordField passwordField =
                        new JPasswordField();

                int option = JOptionPane.showConfirmDialog(
                        this,
                        passwordField,
                        "Enter Current Password",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

                if (option != JOptionPane.OK_OPTION) {
                    return;
                }

                String password =
                        new String(passwordField.getPassword());

                if (password.trim().isEmpty()) {
                    showError("Password cannot be empty.");
                    return;
                }

                String result =
                        service.deleteBooking(
                                bookingId,
                                password
                        );

                if (result.startsWith("Booking Deleted Successfully")) {

                    JOptionPane.showMessageDialog(
                            this,
                            result,
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    clearFields();

                } else {

                    showError(result);
                }

            } catch (NumberFormatException ex) {

                showError("Please enter a valid Booking ID.");
            }
        }


        // ================= VIEW =================

        else if (e.getSource() == viewButton) {

            service.viewBookings();

            JOptionPane.showMessageDialog(
                    this,
                    "Bookings displayed in Terminal.",
                    "Bookings",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }


    // =====================================================
    // ERROR MESSAGE
    // =====================================================

    private void showError(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }


    // =====================================================
    // CLEAR FIELDS
    // =====================================================

    private void clearFields() {

        bookingIdField.setText("");
        userIdField.setText("");
        eventIdField.setText("");
        ticketTypeIdField.setText("");
        quantityField.setText("");
        totalAmountField.setText("");
    }


    public static void main(String[] args) {

        new BookingForm();
    }
}
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

        // Title
        titleLabel = new JLabel("BOOKING MANAGEMENT");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBounds(170, 30, 300, 30);
        add(titleLabel);

        // Booking ID
        bookingIdLabel = new JLabel("Booking ID:");
        bookingIdLabel.setBounds(80, 90, 120, 25);

        bookingIdField = new JTextField();
        bookingIdField.setBounds(210, 90, 200, 25);

        add(bookingIdLabel);
        add(bookingIdField);

        // User ID
        userIdLabel = new JLabel("User ID:");
        userIdLabel.setBounds(80, 130, 120, 25);

        userIdField = new JTextField();
        userIdField.setBounds(210, 130, 200, 25);

        add(userIdLabel);
        add(userIdField);

        // Event ID
        eventIdLabel = new JLabel("Event ID:");
        eventIdLabel.setBounds(80, 170, 120, 25);

        eventIdField = new JTextField();
        eventIdField.setBounds(210, 170, 200, 25);

        add(eventIdLabel);
        add(eventIdField);

        // Ticket Type ID
        ticketTypeIdLabel = new JLabel("Ticket Type ID:");
        ticketTypeIdLabel.setBounds(80, 210, 120, 25);

        ticketTypeIdField = new JTextField();
        ticketTypeIdField.setBounds(210, 210, 200, 25);

        add(ticketTypeIdLabel);
        add(ticketTypeIdField);

        // Quantity
        quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(80, 250, 120, 25);

        quantityField = new JTextField();
        quantityField.setBounds(210, 250, 200, 25);

        add(quantityLabel);
        add(quantityField);

        // Total Amount
        totalAmountLabel = new JLabel("Total Amount:");
        totalAmountLabel.setBounds(80, 290, 120, 25);

        totalAmountField = new JTextField();
        totalAmountField.setBounds(210, 290, 200, 25);

        add(totalAmountLabel);
        add(totalAmountField);

        // Buttons
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

    @Override
    public void actionPerformed(ActionEvent e) {

        try {

            // ADD BOOKING
            if (e.getSource() == addButton) {

                int userId = Integer.parseInt(userIdField.getText());
                int eventId = Integer.parseInt(eventIdField.getText());
                int ticketTypeId = Integer.parseInt(ticketTypeIdField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                double totalAmount = Double.parseDouble(totalAmountField.getText());

                Booking booking = new Booking(
                        userId,
                        eventId,
                        ticketTypeId,
                        quantity,
                        totalAmount
                );

                service.addBooking(booking);

                JOptionPane.showMessageDialog(this,
                        "Booking Added Successfully!");
            }

            // UPDATE BOOKING
            else if (e.getSource() == updateButton) {

                int bookingId = Integer.parseInt(bookingIdField.getText());
                int userId = Integer.parseInt(userIdField.getText());
                int eventId = Integer.parseInt(eventIdField.getText());
                int ticketTypeId = Integer.parseInt(ticketTypeIdField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                double totalAmount = Double.parseDouble(totalAmountField.getText());

                Booking booking = new Booking(
                        userId,
                        eventId,
                        ticketTypeId,
                        quantity,
                        totalAmount
                );

                booking.setBookingId(bookingId);

                service.updateBooking(booking);

                JOptionPane.showMessageDialog(this,
                        "Booking Updated Successfully!");
            }

            // DELETE BOOKING
            else if (e.getSource() == deleteButton) {

                int bookingId = Integer.parseInt(
                        bookingIdField.getText()
                );

                service.deleteBooking(bookingId);

                JOptionPane.showMessageDialog(this,
                        "Booking Deleted Successfully!");
            }

            // VIEW BOOKINGS
            else if (e.getSource() == viewButton) {

                service.viewBookings();

                JOptionPane.showMessageDialog(this,
                        "Bookings displayed in Terminal!");
            }

            // Clear Fields
            bookingIdField.setText("");
            userIdField.setText("");
            eventIdField.setText("");
            ticketTypeIdField.setText("");
            quantityField.setText("");
            totalAmountField.setText("");

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this,
                    "Please enter valid details!");
        }
    }

    public static void main(String[] args) {
        new BookingForm();
    }
}
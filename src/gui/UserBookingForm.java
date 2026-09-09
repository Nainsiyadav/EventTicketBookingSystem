package gui;

import database.DBConnection;
import model.Booking;
import service.BookingService;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserBookingForm extends JFrame {

    private int userId, eventId;
    private BookingService bookingService;

    private JLabel eventLabel, ticketLabel, priceLabel, totalLabel;
    private JComboBox<String> ticketTypeCombo;
    private JTextField quantityField;
    private JButton bookButton, cancelButton;

    private int selectedTicketTypeId = -1;
    private double selectedTicketPrice = 0.0;

    public UserBookingForm(int userId, int eventId) {

        this.userId = userId;
        this.eventId = eventId;
        bookingService = new BookingService();

        setTitle("Book Ticket");
        setSize(550, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // TITLE
        JLabel titleLabel = new JLabel("BOOK TICKET", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setForeground(new Color(25, 55, 90));
        titleLabel.setBounds(140, 30, 270, 40);
        add(titleLabel);

        // USER ID
        JLabel userLabel = label("User ID:");
        userLabel.setBounds(70, 100, 120, 30);
        add(userLabel);

        JLabel userValueLabel =
                new JLabel(String.valueOf(userId));
        userValueLabel.setBounds(220, 100, 220, 30);
        add(userValueLabel);

        // EVENT ID
        JLabel eventIdLabel = label("Event ID:");
        eventIdLabel.setBounds(70, 140, 120, 30);
        add(eventIdLabel);

        eventLabel = new JLabel(String.valueOf(eventId));
        eventLabel.setBounds(220, 140, 220, 30);
        add(eventLabel);

        // TICKET TYPE
        ticketLabel = label("Ticket Type:");
        ticketLabel.setBounds(70, 180, 120, 30);
        add(ticketLabel);

        ticketTypeCombo = new JComboBox<>();
        ticketTypeCombo.setBounds(220, 180, 220, 30);
        add(ticketTypeCombo);

        // PRICE
        priceLabel = label("Ticket Price: ₹0.00");
        priceLabel.setBounds(70, 220, 300, 30);
        add(priceLabel);

        // QUANTITY
        JLabel quantityLabel = label("Quantity:");
        quantityLabel.setBounds(70, 260, 120, 30);
        add(quantityLabel);

        quantityField = new JTextField("1");
        quantityField.setBounds(220, 260, 220, 30);
        add(quantityField);

        // TOTAL
        totalLabel = label("Total Amount: ₹0.00");
        totalLabel.setBounds(70, 300, 300, 30);
        add(totalLabel);

        // BUTTONS
        bookButton = new JButton("Book Ticket");
        bookButton.setBounds(140, 350, 120, 35);
        styleButton(bookButton, new Color(55, 140, 100));
        add(bookButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(280, 350, 100, 35);
        styleButton(cancelButton, new Color(190, 70, 70));
        add(cancelButton);

        // LOAD TICKETS
        loadTicketTypes();

        ticketTypeCombo.addActionListener(e -> updateTicketDetails());

        quantityField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                calculateTotal();
            }
        });

        bookButton.addActionListener(e -> bookTicket());
        cancelButton.addActionListener(e -> dispose());
    }

    // LABEL STYLE
    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Arial", Font.BOLD, 15));
        l.setForeground(new Color(70, 70, 70));
        return l;
    }

    // BUTTON STYLE
    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }

    // LOAD TICKET TYPES
    private void loadTicketTypes() {

        String sql =
                "SELECT ticket_type_id, ticket_name, price, " +
                "available_quantity FROM ticket_types " +
                "WHERE event_id = ? AND available_quantity > 0";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            ticketTypeCombo.removeAllItems();

            while (rs.next()) {

                int id = rs.getInt("ticket_type_id");
                String name = rs.getString("ticket_name");
                double price = rs.getDouble("price");
                int available = rs.getInt("available_quantity");

                ticketTypeCombo.addItem(
                        id + " - " + name + " - ₹" +
                        price + " (" + available + " available)");
            }

            if (ticketTypeCombo.getItemCount() == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "No tickets available for this event.",
                        "No Tickets",
                        JOptionPane.WARNING_MESSAGE);

                bookButton.setEnabled(false);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading ticket types:\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        updateTicketDetails();
    }

    // UPDATE TICKET DETAILS
    private void updateTicketDetails() {

        if (ticketTypeCombo.getSelectedItem() == null) {

            selectedTicketTypeId = -1;
            selectedTicketPrice = 0.0;
            priceLabel.setText("Ticket Price: ₹0.00");
            totalLabel.setText("Total Amount: ₹0.00");
            return;
        }

        try {

            String[] parts =
                    ticketTypeCombo.getSelectedItem().toString()
                            .split(" - ");

            selectedTicketTypeId =
                    Integer.parseInt(parts[0].trim());

            String pricePart =
                    parts[2].replace("₹", "")
                            .split("\\(")[0].trim();

            selectedTicketPrice =
                    Double.parseDouble(pricePart);

            priceLabel.setText(
                    String.format(
                            "Ticket Price: ₹%.2f",
                            selectedTicketPrice));

            calculateTotal();

        } catch (Exception e) {

            selectedTicketTypeId = -1;
            selectedTicketPrice = 0.0;

            priceLabel.setText("Ticket Price: ₹0.00");
            totalLabel.setText("Total Amount: ₹0.00");
        }
    }

    // CALCULATE TOTAL
    private void calculateTotal() {

        try {

            int quantity =
                    Integer.parseInt(quantityField.getText().trim());

            if (quantity <= 0) {
                totalLabel.setText("Total Amount: ₹0.00");
                return;
            }

            double total = selectedTicketPrice * quantity;

            totalLabel.setText(
                    String.format(
                            "Total Amount: ₹%.2f", total));

        } catch (NumberFormatException e) {

            totalLabel.setText("Total Amount: ₹0.00");
        }
    }

    // BOOK TICKET
    private void bookTicket() {

        try {

            if (selectedTicketTypeId == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a ticket type.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String quantityText =
                    quantityField.getText().trim();

            if (quantityText.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter quantity.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int quantity = Integer.parseInt(quantityText);

            if (quantity <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Quantity must be greater than 0.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            double totalAmount =
                    selectedTicketPrice * quantity;

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    String.format(
                            "Ticket Price: ₹%.2f\n" +
                            "Quantity: %d\n" +
                            "Total Amount: ₹%.2f\n\n" +
                            "Confirm booking?",
                            selectedTicketPrice,
                            quantity,
                            totalAmount),
                    "Confirm Booking",
                    JOptionPane.YES_NO_OPTION);

            if (choice != JOptionPane.YES_OPTION)
                return;

            Booking booking = new Booking(
                    userId,
                    eventId,
                    selectedTicketTypeId,
                    quantity,
                    totalAmount);

            String result =
                    bookingService.addBooking(booking);

            if (result.startsWith(
                    "Booking Added Successfully")) {

                JOptionPane.showMessageDialog(
                        this,
                        result + "\nBooking ID: " +
                        booking.getBookingId(),
                        "Booking Successful",
                        JOptionPane.INFORMATION_MESSAGE);

                new UserPaymentForm(
                        userId,
                        booking.getBookingId()).setVisible(true);

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        result,
                        "Booking Failed",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid quantity.",
                    "Invalid Quantity",
                    JOptionPane.ERROR_MESSAGE);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error while booking:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // MAIN
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new UserBookingForm(1, 1).setVisible(true));
    }
}
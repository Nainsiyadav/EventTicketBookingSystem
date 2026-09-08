package gui;

import  database.DBConnection;
import model.Booking;
import service.BookingService;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserBookingForm extends JFrame {

    private int userId;
    private int eventId;

    private BookingService bookingService;

    private JLabel eventLabel;
    private JLabel ticketLabel;
    private JLabel priceLabel;
    private JLabel totalLabel;

    private JComboBox<String> ticketTypeCombo;
    private JTextField quantityField;

    private JButton bookButton;
    private JButton cancelButton;

    // Selected ticket information
    private int selectedTicketTypeId = -1;
    private double selectedTicketPrice = 0.0;

    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public UserBookingForm(int userId, int eventId) {

        this.userId = userId;
        this.eventId = eventId;

        bookingService = new BookingService();

        setTitle("Book Ticket");
        setSize(550, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // =========================
        // TITLE
        // =========================

        JLabel titleLabel =
                new JLabel("BOOK TICKET");

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 25)
        );

        titleLabel.setBounds(190, 30, 200, 40);

        add(titleLabel);


        // =========================
        // USER ID
        // =========================

        JLabel userLabel =
                new JLabel("User ID:");

        userLabel.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        userLabel.setBounds(70, 100, 120, 30);

        add(userLabel);


        JLabel userValueLabel =
                new JLabel(String.valueOf(userId));

        userValueLabel.setBounds(220, 100, 220, 30);

        add(userValueLabel);


        // =========================
        // EVENT ID
        // =========================

        JLabel eventIdLabel =
                new JLabel("Event ID:");

        eventIdLabel.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        eventIdLabel.setBounds(70, 140, 120, 30);

        add(eventIdLabel);


        eventLabel =
                new JLabel(String.valueOf(eventId));

        eventLabel.setBounds(220, 140, 220, 30);

        add(eventLabel);


        // =========================
        // TICKET TYPE
        // =========================

        ticketLabel =
                new JLabel("Ticket Type:");

        ticketLabel.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        ticketLabel.setBounds(70, 180, 120, 30);

        add(ticketLabel);


        ticketTypeCombo =
                new JComboBox<>();

        ticketTypeCombo.setBounds(
                220, 180, 220, 30
        );

        add(ticketTypeCombo);


        // =========================
        // PRICE
        // =========================

        priceLabel =
                new JLabel("Ticket Price: ₹0.00");

        priceLabel.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        priceLabel.setBounds(
                70, 220, 300, 30
        );

        add(priceLabel);


        // =========================
        // QUANTITY
        // =========================

        JLabel quantityLabel =
                new JLabel("Quantity:");

        quantityLabel.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        quantityLabel.setBounds(
                70, 260, 120, 30
        );

        add(quantityLabel);


        quantityField =
                new JTextField("1");

        quantityField.setBounds(
                220, 260, 220, 30
        );

        add(quantityField);


        // =========================
        // TOTAL
        // =========================

        totalLabel =
                new JLabel("Total Amount: ₹0.00");

        totalLabel.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        totalLabel.setBounds(
                70, 300, 300, 30
        );

        add(totalLabel);


        // =========================
        // BUTTONS
        // =========================

        bookButton =
                new JButton("Book Ticket");

        bookButton.setBounds(
                140, 350, 120, 35
        );

        add(bookButton);


        cancelButton =
                new JButton("Cancel");

        cancelButton.setBounds(
                280, 350, 100, 35
        );

        add(cancelButton);


        // =========================
        // LOAD TICKET TYPES
        // =========================

        loadTicketTypes();


        // =========================
        // TICKET TYPE ACTION
        // =========================

        ticketTypeCombo.addActionListener(e -> {

            updateTicketDetails();

        });


        // =========================
        // QUANTITY ACTION
        // =========================

        quantityField.addKeyListener(
                new java.awt.event.KeyAdapter() {

                    @Override
                    public void keyReleased(
                            java.awt.event.KeyEvent e) {

                        calculateTotal();

                    }
                }
        );


        // =========================
        // BOOK BUTTON
        // =========================

        bookButton.addActionListener(e -> {

            bookTicket();

        });


        // =========================
        // CANCEL BUTTON
        // =========================

        cancelButton.addActionListener(e -> {

            dispose();

        });
    }


    // =====================================================
    // LOAD TICKET TYPES
    // =====================================================

    private void loadTicketTypes() {

        String sql =
                "SELECT ticket_type_id, ticket_name, price, " +
                "available_quantity " +
                "FROM ticket_types " +
                "WHERE event_id = ? " +
                "AND available_quantity > 0";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, eventId);

            ResultSet rs =
                    ps.executeQuery();

            ticketTypeCombo.removeAllItems();

            while (rs.next()) {

                int ticketTypeId =
                        rs.getInt("ticket_type_id");

                String ticketName =
                        rs.getString("ticket_name");

                double price =
                        rs.getDouble("price");

                int available =
                        rs.getInt("available_quantity");

                String item =
                        ticketTypeId
                        + " - "
                        + ticketName
                        + " - ₹"
                        + price
                        + " ("
                        + available
                        + " available)";

                ticketTypeCombo.addItem(item);
            }

            if (ticketTypeCombo.getItemCount() == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "No tickets available for this event.",
                        "No Tickets",
                        JOptionPane.WARNING_MESSAGE
                );

                bookButton.setEnabled(false);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading ticket types:\n"
                    + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        updateTicketDetails();
    }


    // =====================================================
    // UPDATE TICKET DETAILS
    // =====================================================

    private void updateTicketDetails() {

        if (ticketTypeCombo.getSelectedItem() == null) {

            selectedTicketTypeId = -1;
            selectedTicketPrice = 0.0;

            priceLabel.setText(
                    "Ticket Price: ₹0.00"
            );

            totalLabel.setText(
                    "Total Amount: ₹0.00"
            );

            return;
        }

        String selected =
                ticketTypeCombo
                        .getSelectedItem()
                        .toString();

        try {

            // Example:
            // 8 - VIP - ₹1000.0 (50 available)

            String[] parts =
                    selected.split(" - ");

            // Ticket Type ID
            selectedTicketTypeId =
                    Integer.parseInt(
                            parts[0].trim()
                    );

            // Remove ₹ and available quantity
            String pricePart =
                    parts[2]
                            .replace("₹", "")
                            .trim();

            // Remove "(50 available)"
            pricePart =
                    pricePart.split("\\(")[0]
                            .trim();

            // Ticket price
            selectedTicketPrice =
                    Double.parseDouble(pricePart);

            priceLabel.setText(
                    String.format(
                            "Ticket Price: ₹%.2f",
                            selectedTicketPrice
                    )
            );

            calculateTotal();

        } catch (Exception e) {

            selectedTicketTypeId = -1;
            selectedTicketPrice = 0.0;

            priceLabel.setText(
                    "Ticket Price: ₹0.00"
            );

            totalLabel.setText(
                    "Total Amount: ₹0.00"
            );

            System.out.println(
                    "Ticket selection error: "
                    + e.getMessage()
            );
        }
    }


    // =====================================================
    // CALCULATE TOTAL
    // =====================================================

    private void calculateTotal() {

        try {

            int quantity =
                    Integer.parseInt(
                            quantityField
                                    .getText()
                                    .trim()
                    );

            if (quantity <= 0) {

                totalLabel.setText(
                        "Total Amount: ₹0.00"
                );

                return;
            }

            double total =
                    selectedTicketPrice * quantity;

            totalLabel.setText(
                    String.format(
                            "Total Amount: ₹%.2f",
                            total
                    )
            );

        } catch (NumberFormatException e) {

            totalLabel.setText(
                    "Total Amount: ₹0.00"
            );
        }
    }


    // =====================================================
    // BOOK TICKET
    // =====================================================

    private void bookTicket() {

        try {

            // =========================
            // CHECK TICKET TYPE
            // =========================

            if (selectedTicketTypeId == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a ticket type.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }


            // =========================
            // QUANTITY
            // =========================

            String quantityText =
                    quantityField
                            .getText()
                            .trim();


            if (quantityText.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter quantity.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }


            int quantity =
                    Integer.parseInt(quantityText);


            if (quantity <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Quantity must be greater than 0.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }


            // =========================
            // TOTAL AMOUNT
            // =========================

            double totalAmount =
                    selectedTicketPrice * quantity;


            // =========================
            // CONFIRMATION
            // =========================

            int choice =
                    JOptionPane.showConfirmDialog(
                            this,
                            String.format(
                                    "Ticket Price: ₹%.2f\n"
                                    + "Quantity: %d\n"
                                    + "Total Amount: ₹%.2f\n\n"
                                    + "Confirm booking?",
                                    selectedTicketPrice,
                                    quantity,
                                    totalAmount
                            ),
                            "Confirm Booking",
                            JOptionPane.YES_NO_OPTION
                    );


            if (choice != JOptionPane.YES_OPTION) {

                return;
            }


            // =========================
            // CREATE BOOKING
            // =========================

            Booking booking =
                    new Booking(
                            userId,
                            eventId,
                            selectedTicketTypeId,
                            quantity,
                            totalAmount
                    );


            // =========================
            // SAVE BOOKING
            // =========================

            String result =
                    bookingService.addBooking(booking);


            // =================================================
            // BOOKING SUCCESSFUL
            // =================================================

            if (result.startsWith(
                    "Booking Added Successfully")) {

                JOptionPane.showMessageDialog(
                        this,
                        result
                        + "\nBooking ID: "
                        + booking.getBookingId(),
                        "Booking Successful",
                        JOptionPane.INFORMATION_MESSAGE
                );


                // =============================================
                // OPEN PAYMENT FORM AUTOMATICALLY
                // =============================================

                new UserPaymentForm(
                        userId,
                        booking.getBookingId()
                ).setVisible(true);


                // Close booking form
                dispose();


            } else {

                // =========================
                // BOOKING FAILED
                // =========================

                JOptionPane.showMessageDialog(
                        this,
                        result,
                        "Booking Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }


        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid quantity.",
                    "Invalid Quantity",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error while booking:\n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =====================================================
    // MAIN METHOD
    // =====================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            // Testing only
            new UserBookingForm(
                    1,
                    1
            ).setVisible(true);

        });
    }
}

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

    JButton addButton, updateButton, deleteButton,
            viewButton, backButton;

    BookingService service;

    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public BookingForm() {

        setTitle("Booking Management");

        // Full laptop screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // =================================================
        // MAIN PANEL
        // =================================================

        JPanel mainPanel =
                new JPanel(new GridBagLayout());

        mainPanel.setBackground(
                new Color(242, 244, 247)
        );

        setContentPane(mainPanel);

        // =================================================
        // WHITE CENTER CARD
        // =================================================

        JPanel card =
                new RoundedPanel(30);

        card.setPreferredSize(
                new Dimension(700, 650)
        );

        card.setBackground(Color.WHITE);

        card.setLayout(null);

        mainPanel.add(card);

        // =================================================
        // SERVICE
        // =================================================

        service = new BookingService();

        // =================================================
        // TITLE
        // =================================================

        titleLabel =
                new JLabel("BOOKING MANAGEMENT");

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        titleLabel.setForeground(
                new Color(25, 55, 90)
        );

        titleLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        titleLabel.setBounds(
                150,
                30,
                400,
                35
        );

        card.add(titleLabel);

        // =================================================
        // BOOKING ID
        // =================================================

        bookingIdLabel =
                new JLabel("Booking ID:");

        bookingIdLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        bookingIdLabel.setForeground(
                new Color(70, 70, 70)
        );

        bookingIdLabel.setBounds(
                100,
                90,
                130,
                25
        );

        bookingIdField =
                new JTextField();

        bookingIdField.setBounds(
                250,
                90,
                300,
                30
        );

        card.add(bookingIdLabel);
        card.add(bookingIdField);

        // =================================================
        // USER ID
        // =================================================

        userIdLabel =
                new JLabel("User ID:");

        userIdLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        userIdLabel.setForeground(
                new Color(70, 70, 70)
        );

        userIdLabel.setBounds(
                100,
                135,
                130,
                25
        );

        userIdField =
                new JTextField();

        userIdField.setBounds(
                250,
                135,
                300,
                30
        );

        card.add(userIdLabel);
        card.add(userIdField);

        // =================================================
        // EVENT ID
        // =================================================

        eventIdLabel =
                new JLabel("Event ID:");

        eventIdLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        eventIdLabel.setForeground(
                new Color(70, 70, 70)
        );

        eventIdLabel.setBounds(
                100,
                180,
                130,
                25
        );

        eventIdField =
                new JTextField();

        eventIdField.setBounds(
                250,
                180,
                300,
                30
        );

        card.add(eventIdLabel);
        card.add(eventIdField);

        // =================================================
        // TICKET TYPE ID
        // =================================================

        ticketTypeIdLabel =
                new JLabel("Ticket Type ID:");

        ticketTypeIdLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        ticketTypeIdLabel.setForeground(
                new Color(70, 70, 70)
        );

        ticketTypeIdLabel.setBounds(
                100,
                225,
                130,
                25
        );

        ticketTypeIdField =
                new JTextField();

        ticketTypeIdField.setBounds(
                250,
                225,
                300,
                30
        );

        card.add(ticketTypeIdLabel);
        card.add(ticketTypeIdField);

        // =================================================
        // QUANTITY
        // =================================================

        quantityLabel =
                new JLabel("Quantity:");

        quantityLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        quantityLabel.setForeground(
                new Color(70, 70, 70)
        );

        quantityLabel.setBounds(
                100,
                270,
                130,
                25
        );

        quantityField =
                new JTextField();

        quantityField.setBounds(
                250,
                270,
                300,
                30
        );

        card.add(quantityLabel);
        card.add(quantityField);

        // =================================================
        // TOTAL AMOUNT
        // =================================================

        totalAmountLabel =
                new JLabel("Total Amount:");

        totalAmountLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        totalAmountLabel.setForeground(
                new Color(70, 70, 70)
        );

        totalAmountLabel.setBounds(
                100,
                315,
                130,
                25
        );

        totalAmountField =
                new JTextField();

        totalAmountField.setBounds(
                250,
                315,
                300,
                30
        );

        card.add(totalAmountLabel);
        card.add(totalAmountField);

        // =================================================
        // ADD BUTTON
        // =================================================

        addButton =
                createButton(
                        "Add",
                        new Color(45, 85, 130)
                );

        addButton.setBounds(
                70,
                390,
                120,
                40
        );

        card.add(addButton);

        // =================================================
        // UPDATE BUTTON
        // =================================================

        updateButton =
                createButton(
                        "Update",
                        new Color(45, 85, 130)
                );

        updateButton.setBounds(
                205,
                390,
                120,
                40
        );

        card.add(updateButton);

        // =================================================
        // DELETE BUTTON
        // =================================================

        deleteButton =
                createButton(
                        "Delete",
                        new Color(190, 70, 70)
                );

        deleteButton.setBounds(
                340,
                390,
                120,
                40
        );

        card.add(deleteButton);

        // =================================================
        // VIEW BUTTON
        // =================================================

        viewButton =
                createButton(
                        "View Bookings",
                        new Color(55, 140, 100)
                );

        viewButton.setBounds(
                475,
                390,
                150,
                40
        );

        card.add(viewButton);

        // =================================================
        // BACK BUTTON
        // =================================================

        backButton =
                createButton(
                        "← Back",
                        new Color(90, 90, 90)
                );

        backButton.setBounds(
                275,
                470,
                150,
                40
        );

        card.add(backButton);

        // =================================================
        // ACTION LISTENERS
        // =================================================

        addButton.addActionListener(this);

        updateButton.addActionListener(this);

        deleteButton.addActionListener(this);

        viewButton.addActionListener(this);

        backButton.addActionListener(this);

        setVisible(true);
    }

    // =====================================================
    // CREATE BUTTON
    // =====================================================

    private JButton createButton(
            String text,
            Color background) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        button.setBackground(background);

        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        return button;
    }

    // =====================================================
    // BUTTON ACTIONS
    // =====================================================

    @Override
    public void actionPerformed(
            ActionEvent e) {

        // =================================================
        // ADD BOOKING
        // =================================================

        if (e.getSource() == addButton) {

            try {

                if (userIdField
                        .getText()
                        .trim()
                        .isEmpty()) {

                    showError(
                            "Please enter User ID."
                    );

                    return;
                }

                if (eventIdField
                        .getText()
                        .trim()
                        .isEmpty()) {

                    showError(
                            "Please enter Event ID."
                    );

                    return;
                }

                if (ticketTypeIdField
                        .getText()
                        .trim()
                        .isEmpty()) {

                    showError(
                            "Please enter Ticket Type ID."
                    );

                    return;
                }

                if (quantityField
                        .getText()
                        .trim()
                        .isEmpty()) {

                    showError(
                            "Please enter Quantity."
                    );

                    return;
                }

                if (totalAmountField
                        .getText()
                        .trim()
                        .isEmpty()) {

                    showError(
                            "Please enter Total Amount."
                    );

                    return;
                }

                int userId =
                        Integer.parseInt(
                                userIdField
                                        .getText()
                                        .trim()
                        );

                int eventId =
                        Integer.parseInt(
                                eventIdField
                                        .getText()
                                        .trim()
                        );

                int ticketTypeId =
                        Integer.parseInt(
                                ticketTypeIdField
                                        .getText()
                                        .trim()
                        );

                int quantity =
                        Integer.parseInt(
                                quantityField
                                        .getText()
                                        .trim()
                        );

                double totalAmount =
                        Double.parseDouble(
                                totalAmountField
                                        .getText()
                                        .trim()
                        );

                Booking booking =
                        new Booking(
                                userId,
                                eventId,
                                ticketTypeId,
                                quantity,
                                totalAmount
                        );

                String result =
                        service.addBooking(
                                booking
                        );

                if (result.startsWith(
                        "Booking Added Successfully")) {

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

            } catch (
                    NumberFormatException ex) {

                showError(
                        "Please enter valid numbers in "
                        + "User ID, Event ID, Ticket Type ID, "
                        + "Quantity and Total Amount."
                );
            }
        }

        // =================================================
        // UPDATE BOOKING
        // =================================================

        else if (e.getSource() == updateButton) {

            try {

                if (bookingIdField
                        .getText()
                        .trim()
                        .isEmpty()) {

                    showError(
                            "Please enter Booking ID."
                    );

                    return;
                }

                if (userIdField
                        .getText()
                        .trim()
                        .isEmpty()) {

                    showError(
                            "Please enter User ID."
                    );

                    return;
                }

                if (eventIdField
                        .getText()
                        .trim()
                        .isEmpty()) {

                    showError(
                            "Please enter Event ID."
                    );

                    return;
                }

                if (ticketTypeIdField
                        .getText()
                        .trim()
                        .isEmpty()) {

                    showError(
                            "Please enter Ticket Type ID."
                    );

                    return;
                }

                if (quantityField
                        .getText()
                        .trim()
                        .isEmpty()) {

                    showError(
                            "Please enter Quantity."
                    );

                    return;
                }

                if (totalAmountField
                        .getText()
                        .trim()
                        .isEmpty()) {

                    showError(
                            "Please enter Total Amount."
                    );

                    return;
                }

                int bookingId =
                        Integer.parseInt(
                                bookingIdField
                                        .getText()
                                        .trim()
                        );

                int userId =
                        Integer.parseInt(
                                userIdField
                                        .getText()
                                        .trim()
                        );

                int eventId =
                        Integer.parseInt(
                                eventIdField
                                        .getText()
                                        .trim()
                        );

                int ticketTypeId =
                        Integer.parseInt(
                                ticketTypeIdField
                                        .getText()
                                        .trim()
                        );

                int quantity =
                        Integer.parseInt(
                                quantityField
                                        .getText()
                                        .trim()
                        );

                double totalAmount =
                        Double.parseDouble(
                                totalAmountField
                                        .getText()
                                        .trim()
                        );

                // =========================================
                // PASSWORD
                // =========================================

                JPasswordField passwordField =
                        new JPasswordField();

                int option =
                        JOptionPane.showConfirmDialog(
                                this,
                                passwordField,
                                "Enter Current Password",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.PLAIN_MESSAGE
                        );

                if (option !=
                        JOptionPane.OK_OPTION) {

                    return;
                }

                String password =
                        new String(
                                passwordField
                                        .getPassword()
                        );

                if (password
                        .trim()
                        .isEmpty()) {

                    showError(
                            "Password cannot be empty."
                    );

                    return;
                }

                Booking booking =
                        new Booking(
                                userId,
                                eventId,
                                ticketTypeId,
                                quantity,
                                totalAmount
                        );

                booking.setBookingId(
                        bookingId
                );

                String result =
                        service.updateBooking(
                                booking,
                                password
                        );

                if (result.startsWith(
                        "Booking Updated Successfully")) {

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

            } catch (
                    NumberFormatException ex) {

                showError(
                        "Please enter valid numbers "
                        + "in all numeric fields."
                );
            }
        }

        // =================================================
        // DELETE BOOKING
        // =================================================

        else if (e.getSource() == deleteButton) {

            try {

                if (bookingIdField
                        .getText()
                        .trim()
                        .isEmpty()) {

                    showError(
                            "Please enter Booking ID."
                    );

                    return;
                }

                int bookingId =
                        Integer.parseInt(
                                bookingIdField
                                        .getText()
                                        .trim()
                        );

                // =========================================
                // PASSWORD
                // =========================================

                JPasswordField passwordField =
                        new JPasswordField();

                int option =
                        JOptionPane.showConfirmDialog(
                                this,
                                passwordField,
                                "Enter Current Password",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.PLAIN_MESSAGE
                        );

                if (option !=
                        JOptionPane.OK_OPTION) {

                    return;
                }

                String password =
                        new String(
                                passwordField
                                        .getPassword()
                        );

                if (password
                        .trim()
                        .isEmpty()) {

                    showError(
                            "Password cannot be empty."
                    );

                    return;
                }

                String result =
                        service.deleteBooking(
                                bookingId,
                                password
                        );

                if (result.startsWith(
                        "Booking Deleted Successfully")) {

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

            } catch (
                    NumberFormatException ex) {

                showError(
                        "Please enter a valid Booking ID."
                );
            }
        }

        // =================================================
        // VIEW BOOKINGS
        // =================================================

        else if (e.getSource() == viewButton) {

            showBookingsTable();
        }

        // =================================================
        // BACK
        // =================================================

        else if (e.getSource() == backButton) {

            int choice =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Go back to Dashboard?",
                            "Back",
                            JOptionPane.YES_NO_OPTION
                    );

            if (choice ==
                    JOptionPane.YES_OPTION) {

                new Dashboard().setVisible(true);

                dispose();
            }
        }
    }

    // =====================================================
    // SHOW BOOKINGS TABLE
    // =====================================================

    private void showBookingsTable() {

        Object[][] data =
                service.getBookingsForTable();

        String[] columns = {

            "Booking ID",
            "User ID",
            "Event ID",
            "Ticket Type ID",
            "Quantity",
            "Total Amount"
        };

        JTable table =
                new JTable(
                        data,
                        columns
                );

        table.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        table.setRowHeight(30);

        table.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                14
                        )
                );

        table.setAutoResizeMode(
                JTable.AUTO_RESIZE_ALL_COLUMNS
        );

        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(table);

        // =================================================
        // VIEW FRAME
        // =================================================

        JFrame frame =
                new JFrame(
                        "All Bookings"
                );

        frame.setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        frame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(
                new Color(242, 244, 247)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        30,
                        30,
                        30
                )
        );

        // =================================================
        // TITLE
        // =================================================

        JLabel title =
                new JLabel(
                        "ALL BOOKINGS",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        26
                )
        );

        title.setForeground(
                new Color(25, 55, 90)
        );

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        0,
                        20,
                        0
                )
        );

        panel.add(
                title,
                BorderLayout.NORTH
        );

        // =================================================
        // TABLE
        // =================================================

        panel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =================================================
        // BACK BUTTON
        // =================================================

        JButton closeButton =
                new JButton("← Back");

        closeButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        closeButton.setBackground(
                new Color(90, 90, 90)
        );

        closeButton.setForeground(
                Color.WHITE
        );

        closeButton.setFocusPainted(false);

        closeButton.setBorderPainted(false);

        closeButton.addActionListener(
                e -> frame.dispose()
        );

        JPanel bottomPanel =
                new JPanel();

        bottomPanel.setBackground(
                new Color(242, 244, 247)
        );

        bottomPanel.add(
                closeButton
        );

        panel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        // =================================================
        // SHOW
        // =================================================

        frame.setContentPane(panel);

        frame.setVisible(true);
    }

    // =====================================================
    // ERROR MESSAGE
    // =====================================================

    private void showError(
            String message) {

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

    // =====================================================
    // ROUNDED PANEL
    // =====================================================

    class RoundedPanel extends JPanel {

        private int radius;

        RoundedPanel(int radius) {

            this.radius = radius;

            setOpaque(false);
        }

        @Override
        protected void paintComponent(
                Graphics g) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(
                    getBackground()
            );

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

    // =====================================================
    // MAIN
    // =====================================================

    public static void main(
            String[] args) {

        SwingUtilities.invokeLater(
                () -> new BookingForm()
        );
    }
}
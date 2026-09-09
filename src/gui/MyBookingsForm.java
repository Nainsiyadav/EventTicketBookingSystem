package gui;

import database.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MyBookingsForm extends JFrame {

    private int userId;
    private JTable bookingTable;

    public MyBookingsForm(int userId) {

        this.userId = userId;

        setTitle("My Bookings");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        // ================= TITLE =================

        JLabel titleLabel = new JLabel(
                "MY BOOKINGS",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        titleLabel.setForeground(
                new Color(25, 55, 90)
        );

        add(titleLabel, BorderLayout.NORTH);

        // ================= TABLE =================

        bookingTable = new JTable();

        bookingTable.getTableHeader().setBackground(
                new Color(45, 85, 130)
        );

        bookingTable.getTableHeader().setForeground(Color.WHITE);

        bookingTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        JScrollPane scrollPane =
                new JScrollPane(bookingTable);

        add(scrollPane, BorderLayout.CENTER);

        // ================= REFRESH BUTTON =================

        JButton refreshButton =
                new JButton("Refresh");

        refreshButton.setBackground(
                new Color(120, 80, 150)
        );

        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);

        refreshButton.addActionListener(e ->
                loadMyBookings()
        );

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Load bookings
        loadMyBookings();
    }

    // ================= LOAD USER BOOKINGS =================

    private void loadMyBookings() {

        String sql =
                "SELECT b.booking_id, " +
                "e.event_name, " +
                "tt.ticket_name, " +
                "b.quantity, " +
                "b.total_amount, " +
                "b.booking_date " +
                "FROM bookings b " +
                "JOIN events e ON b.event_id = e.event_id " +
                "JOIN ticket_types tt ON b.ticket_type_id = tt.ticket_type_id " +
                "WHERE b.user_id = ? " +
                "ORDER BY b.booking_id DESC";

        String[] columns = {
                "Booking ID",
                "Event",
                "Ticket Type",
                "Quantity",
                "Total Amount",
                "Booking Date"
        };

        DefaultTableModel model =
                new DefaultTableModel(columns, 0);

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                model.addRow(new Object[]{

                        rs.getInt("booking_id"),

                        rs.getString("event_name"),

                        rs.getString("ticket_name"),

                        rs.getInt("quantity"),

                        rs.getDouble("total_amount"),

                        rs.getString("booking_date")
                });
            }

            bookingTable.setModel(model);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading bookings: "
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ================= MAIN METHOD =================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            MyBookingsForm form =
                    new MyBookingsForm(1);

            form.setVisible(true);
        });
    }
}
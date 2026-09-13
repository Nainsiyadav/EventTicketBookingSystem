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
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ================= MAIN BACKGROUND =================

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(
                new Color(242, 244, 247)
        );

        setContentPane(mainPanel);

        // ================= WHITE CARD =================

        JPanel card = new RoundedPanel(30);

        card.setPreferredSize(
                new Dimension(1100, 700)
        );

        card.setBackground(Color.WHITE);

        card.setLayout(new BorderLayout(20, 20));

        card.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 30, 25, 30
                )
        );

        mainPanel.add(card);

        // ================= TITLE =================

        JLabel titleLabel = new JLabel(
                "MY BOOKINGS",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        titleLabel.setForeground(
                new Color(25, 55, 90)
        );

        titleLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        5, 0, 15, 0
                )
        );

        card.add(
                titleLabel,
                BorderLayout.NORTH
        );

        // ================= TABLE =================

        bookingTable = new JTable();

        bookingTable.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        bookingTable.setRowHeight(32);

        bookingTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        bookingTable.setGridColor(
                new Color(210, 210, 210)
        );

        bookingTable.setShowGrid(true);

        // Table Header

        bookingTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        bookingTable.getTableHeader().setBackground(
                new Color(45, 85, 130)
        );

        bookingTable.getTableHeader().setForeground(
                Color.WHITE
        );

        bookingTable.getTableHeader().setPreferredSize(
                new Dimension(0, 40)
        );

        JScrollPane scrollPane =
                new JScrollPane(bookingTable);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(200, 200, 200)
                )
        );

        card.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // ================= BUTTON PANEL =================

        JPanel buttonPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        20,
                        5
                )
        );

        buttonPanel.setOpaque(false);

        // Refresh Button

        JButton refreshButton =
                createButton(
                        "Refresh",
                        new Color(120, 80, 150)
                );

        refreshButton.addActionListener(
                e -> loadMyBookings()
        );

        // Back Button

        JButton backButton =
                createButton(
                        "← Back",
                        new Color(90, 90, 90)
                );

        backButton.addActionListener(e -> {

            int choice =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Go back to User Dashboard?",
                            "Back",
                            JOptionPane.YES_NO_OPTION
                    );

            if (choice ==
                    JOptionPane.YES_OPTION) {

                new UserDashboard(userId).setVisible(true);

                dispose();
            }
        });

        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);

        card.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // ================= LOAD BOOKINGS =================

        loadMyBookings();

        setVisible(true);
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
                "JOIN ticket_types tt " +
                "ON b.ticket_type_id = tt.ticket_type_id " +
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
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, userId);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                model.addRow(
                        new Object[]{

                                rs.getInt(
                                        "booking_id"
                                ),

                                rs.getString(
                                        "event_name"
                                ),

                                rs.getString(
                                        "ticket_name"
                                ),

                                rs.getInt(
                                        "quantity"
                                ),

                                rs.getDouble(
                                        "total_amount"
                                ),

                                rs.getString(
                                        "booking_date"
                                )
                        }
                );
            }

            bookingTable.setModel(model);

            // Column widths

            bookingTable.getColumnModel()
                    .getColumn(0)
                    .setPreferredWidth(100);

            bookingTable.getColumnModel()
                    .getColumn(1)
                    .setPreferredWidth(220);

            bookingTable.getColumnModel()
                    .getColumn(2)
                    .setPreferredWidth(180);

            bookingTable.getColumnModel()
                    .getColumn(3)
                    .setPreferredWidth(100);

            bookingTable.getColumnModel()
                    .getColumn(4)
                    .setPreferredWidth(150);

            bookingTable.getColumnModel()
                    .getColumn(5)
                    .setPreferredWidth(180);

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

    // ================= BUTTON STYLE =================

    private JButton createButton(
            String text,
            Color color) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        button.setBackground(color);

        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setPreferredSize(
                new Dimension(130, 42)
        );

        return button;
    }

    // ================= ROUNDED CARD =================

    class RoundedPanel extends JPanel {

        private int radius;

        RoundedPanel(int radius) {

            this.radius = radius;

            setOpaque(false);
        }

        @Override
        protected void paintComponent(
                Graphics g) {

            super.paintComponent(g);

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
                    getWidth() - 1,
                    getHeight() - 1,
                    radius,
                    radius
            );

            g2.dispose();
        }
    }

    // ================= MAIN =================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            MyBookingsForm form =
                    new MyBookingsForm(1);

            form.setVisible(true);
        });
    }
}
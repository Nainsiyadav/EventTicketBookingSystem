package gui;

import service.ReportService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class ReportForm extends JFrame {

    private ReportService reportService;

    private JLabel totalBookingsLabel;
    private JLabel totalRevenueLabel;
    private JLabel popularEventLabel;

    private JTable eventRevenueTable;
    private JTable paymentStatusTable;

    public ReportForm() {

        reportService = new ReportService();

        setTitle("Reports & Analytics");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ================= MAIN BACKGROUND =================

        JPanel mainBackground = new JPanel(new GridBagLayout());
        mainBackground.setBackground(new Color(235, 242, 250));

        setContentPane(mainBackground);

        // ================= WHITE CARD =================

        JPanel card = new RoundedPanel(35);
        card.setPreferredSize(new Dimension(1150, 700));
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout(15, 15));

        mainBackground.add(card);

        // ================= TITLE =================

        JLabel titleLabel = new JLabel(
                "REPORTS & ANALYTICS",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 30)
        );

        titleLabel.setForeground(
                new Color(25, 55, 90)
        );

        titleLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 10, 20
                )
        );

        card.add(titleLabel, BorderLayout.NORTH);

        // ================= CENTER PANEL =================

        JPanel centerPanel = new JPanel(
                new BorderLayout(15, 15)
        );

        centerPanel.setBackground(Color.WHITE);

        centerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        5, 20, 5, 20
                )
        );

        // ================= SUMMARY =================

        JPanel summaryPanel = new JPanel(
                new GridLayout(1, 3, 15, 15)
        );

        summaryPanel.setBackground(Color.WHITE);

        // Total Bookings

        totalBookingsLabel = new JLabel(
                "Total Bookings: 0",
                SwingConstants.CENTER
        );

        styleSummaryLabel(
                totalBookingsLabel,
                new Color(45, 130, 200)
        );

        // Total Revenue

        totalRevenueLabel = new JLabel(
                "Total Revenue: ₹0.00",
                SwingConstants.CENTER
        );

        styleSummaryLabel(
                totalRevenueLabel,
                new Color(55, 160, 110)
        );

        // Popular Event

        popularEventLabel = new JLabel(
                "Popular Event: None",
                SwingConstants.CENTER
        );

        styleSummaryLabel(
                popularEventLabel,
                new Color(125, 85, 175)
        );

        summaryPanel.add(totalBookingsLabel);
        summaryPanel.add(totalRevenueLabel);
        summaryPanel.add(popularEventLabel);

        centerPanel.add(
                summaryPanel,
                BorderLayout.NORTH
        );

        // ================= TABLES =================

        JPanel tablesPanel = new JPanel(
                new GridLayout(1, 2, 15, 15)
        );

        tablesPanel.setBackground(Color.WHITE);

        // ================= EVENT REVENUE =================

        JPanel eventPanel = new RoundedPanel(20);

        eventPanel.setBackground(
                new Color(245, 249, 255)
        );

        eventPanel.setLayout(
                new BorderLayout(10, 10)
        );

        JLabel eventTitle = new JLabel(
                "Event-wise Revenue",
                SwingConstants.CENTER
        );

        eventTitle.setFont(
                new Font("Arial", Font.BOLD, 19)
        );

        eventTitle.setForeground(
                new Color(45, 85, 130)
        );

        eventTitle.setBorder(
                BorderFactory.createEmptyBorder(
                        12, 5, 5, 5
                )
        );

        eventPanel.add(
                eventTitle,
                BorderLayout.NORTH
        );

        eventRevenueTable = new JTable();

        styleTable(eventRevenueTable);

        eventPanel.add(
                new JScrollPane(eventRevenueTable),
                BorderLayout.CENTER
        );

        tablesPanel.add(eventPanel);

        // ================= PAYMENT STATUS =================

        JPanel paymentPanel = new RoundedPanel(20);

        paymentPanel.setBackground(
                new Color(250, 247, 255)
        );

        paymentPanel.setLayout(
                new BorderLayout(10, 10)
        );

        JLabel paymentTitle = new JLabel(
                "Payment Status Summary",
                SwingConstants.CENTER
        );

        paymentTitle.setFont(
                new Font("Arial", Font.BOLD, 19)
        );

        paymentTitle.setForeground(
                new Color(125, 85, 175)
        );

        paymentTitle.setBorder(
                BorderFactory.createEmptyBorder(
                        12, 5, 5, 5
                )
        );

        paymentPanel.add(
                paymentTitle,
                BorderLayout.NORTH
        );

        paymentStatusTable = new JTable();

        styleTable(paymentStatusTable);

        paymentPanel.add(
                new JScrollPane(paymentStatusTable),
                BorderLayout.CENTER
        );

        tablesPanel.add(paymentPanel);

        centerPanel.add(
                tablesPanel,
                BorderLayout.CENTER
        );

        card.add(
                centerPanel,
                BorderLayout.CENTER
        );

        // ================= BUTTONS =================

        JPanel buttonPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        15,
                        12
                )
        );

        buttonPanel.setBackground(Color.WHITE);

        JButton refreshButton = createButton(
                "⟳ Refresh Reports",
                new Color(45, 130, 200)
        );

        JButton backButton = createButton(
                "← Back",
                new Color(70, 70, 80)
        );

        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);

        refreshButton.addActionListener(
                e -> loadReports()
        );

        backButton.addActionListener(e -> {

            int choice =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Go back to Dashboard?",
                            "Back",
                            JOptionPane.YES_NO_OPTION
                    );

            if (choice == JOptionPane.YES_OPTION) {

                new Dashboard().setVisible(true);

                dispose();
            }
        });

        card.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // ================= LOAD DATA =================

        loadReports();

        setVisible(true);
    }

    // =====================================================
    // SUMMARY LABEL STYLE
    // =====================================================

    private void styleSummaryLabel(
            JLabel label,
            Color color
    ) {

        label.setFont(
                new Font("Arial", Font.BOLD, 17)
        );

        label.setForeground(Color.WHITE);

        label.setOpaque(true);

        label.setBackground(color);

        label.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 10, 15, 10
                )
        );
    }

    // =====================================================
    // TABLE STYLE
    // =====================================================

    private void styleTable(JTable table) {

        table.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        table.setRowHeight(30);

        table.setGridColor(
                new Color(210, 215, 220)
        );

        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        table.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        table.getTableHeader().setBackground(
                new Color(45, 85, 130)
        );

        table.getTableHeader().setForeground(
                Color.WHITE
        );

        table.getTableHeader().setPreferredSize(
                new Dimension(0, 35)
        );
    }

    // =====================================================
    // BUTTON STYLE
    // =====================================================

    private JButton createButton(
            String text,
            Color background
    ) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        button.setBackground(background);

        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setPreferredSize(
                new Dimension(180, 42)
        );

        return button;
    }

    // =====================================================
    // LOAD REPORTS
    // =====================================================

    private void loadReports() {

        int totalBookings =
                reportService.getTotalBookings();

        totalBookingsLabel.setText(
                "Total Bookings: " + totalBookings
        );

        double totalRevenue =
                reportService.getTotalRevenue();

        totalRevenueLabel.setText(
                String.format(
                        "Total Revenue: ₹%.2f",
                        totalRevenue
                )
        );

        String popularEvent =
                reportService.getPopularEvent();

        popularEventLabel.setText(
                "Popular Event: " + popularEvent
        );

        loadEventWiseRevenue();

        loadPaymentStatus();
    }

    // =====================================================
    // EVENT-WISE REVENUE
    // =====================================================

    private void loadEventWiseRevenue() {

        List<Map<String, Object>> data =
                reportService.getEventWiseRevenue();

        String[] columns = {
                "Event Name",
                "Tickets Sold",
                "Revenue"
        };

        DefaultTableModel model =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        for (Map<String, Object> row : data) {

            model.addRow(
                    new Object[]{
                            row.get("event_name"),
                            row.get("tickets_sold"),
                            row.get("revenue")
                    }
            );
        }

        eventRevenueTable.setModel(model);

        styleTable(eventRevenueTable);
    }

    // =====================================================
    // PAYMENT STATUS
    // =====================================================

    private void loadPaymentStatus() {

        List<Map<String, Object>> data =
                reportService.getPaymentStatusSummary();

        String[] columns = {
                "Payment Status",
                "Total Payments"
        };

        DefaultTableModel model =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        for (Map<String, Object> row : data) {

            model.addRow(
                    new Object[]{
                            row.get("payment_status"),
                            row.get("total")
                    }
            );
        }

        paymentStatusTable.setModel(model);

        styleTable(paymentStatusTable);
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
        protected void paintComponent(Graphics g) {

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

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> new ReportForm()
        );
    }
}
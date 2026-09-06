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
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel titleLabel = new JLabel(
                "REPORTS & ANALYTICS",
                SwingConstants.CENTER
        );

        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        add(titleLabel, BorderLayout.NORTH);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1, 10, 10));

        // =========================
        // SUMMARY PANEL
        // =========================

        JPanel summaryPanel = new JPanel(
                new GridLayout(1, 3, 10, 10)
        );

        totalBookingsLabel = new JLabel(
                "Total Bookings: 0",
                SwingConstants.CENTER
        );

        totalRevenueLabel = new JLabel(
                "Total Revenue: ₹0.00",
                SwingConstants.CENTER
        );

        popularEventLabel = new JLabel(
                "Popular Event: None",
                SwingConstants.CENTER
        );

        Font labelFont = new Font("Arial", Font.BOLD, 16);

        totalBookingsLabel.setFont(labelFont);
        totalRevenueLabel.setFont(labelFont);
        popularEventLabel.setFont(labelFont);

        summaryPanel.add(totalBookingsLabel);
        summaryPanel.add(totalRevenueLabel);
        summaryPanel.add(popularEventLabel);

        mainPanel.add(summaryPanel);

        // =========================
        // EVENT-WISE REVENUE
        // =========================

        JPanel eventPanel = new JPanel(new BorderLayout());

        JLabel eventTitle = new JLabel(
                "Event-wise Revenue",
                SwingConstants.CENTER
        );

        eventTitle.setFont(new Font("Arial", Font.BOLD, 18));

        eventPanel.add(eventTitle, BorderLayout.NORTH);

        eventRevenueTable = new JTable();

        JScrollPane eventScrollPane =
                new JScrollPane(eventRevenueTable);

        eventPanel.add(eventScrollPane, BorderLayout.CENTER);

        mainPanel.add(eventPanel);

        // =========================
        // PAYMENT STATUS
        // =========================

        JPanel paymentPanel = new JPanel(new BorderLayout());

        JLabel paymentTitle = new JLabel(
                "Payment Status Summary",
                SwingConstants.CENTER
        );

        paymentTitle.setFont(new Font("Arial", Font.BOLD, 18));

        paymentPanel.add(paymentTitle, BorderLayout.NORTH);

        paymentStatusTable = new JTable();

        JScrollPane paymentScrollPane =
                new JScrollPane(paymentStatusTable);

        paymentPanel.add(paymentScrollPane, BorderLayout.CENTER);

        mainPanel.add(paymentPanel);

        add(mainPanel, BorderLayout.CENTER);

        // =========================
        // REFRESH BUTTON
        // =========================

        JButton refreshButton = new JButton("Refresh Reports");

        refreshButton.addActionListener(e -> loadReports());

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.SOUTH);
                // Load reports
        loadReports();

        // Show window
        setVisible(true);
                
            
    }


    // =========================
    // LOAD ALL REPORTS
    // =========================

    private void loadReports() {

        // Total Bookings
        int totalBookings =
                reportService.getTotalBookings();

        totalBookingsLabel.setText(
                "Total Bookings: " + totalBookings
        );


        // Total Revenue
        double totalRevenue =
                reportService.getTotalRevenue();

        totalRevenueLabel.setText(
                String.format(
                        "Total Revenue: ₹%.2f",
                        totalRevenue
                )
        );


        // Popular Event
        String popularEvent =
                reportService.getPopularEvent();

        popularEventLabel.setText(
                "Popular Event: " + popularEvent
        );


        // Event-wise Revenue
        loadEventWiseRevenue();


        // Payment Status
        loadPaymentStatus();
    }


    // =========================
    // EVENT-WISE REVENUE TABLE
    // =========================

    private void loadEventWiseRevenue() {

        List<Map<String, Object>> data =
                reportService.getEventWiseRevenue();

        String[] columns = {
                "Event Name",
                "Tickets Sold",
                "Revenue"
        };

        DefaultTableModel model =
                new DefaultTableModel(columns, 0);

        for (Map<String, Object> row : data) {

            model.addRow(new Object[]{
                    row.get("event_name"),
                    row.get("tickets_sold"),
                    row.get("revenue")
            });
        }

        eventRevenueTable.setModel(model);
    }


    // =========================
    // PAYMENT STATUS TABLE
    // =========================

    private void loadPaymentStatus() {

        List<Map<String, Object>> data =
                reportService.getPaymentStatusSummary();

        String[] columns = {
                "Payment Status",
                "Total Payments"
        };

        DefaultTableModel model =
                new DefaultTableModel(columns, 0);

        for (Map<String, Object> row : data) {

            model.addRow(new Object[]{
                    row.get("payment_status"),
                    row.get("total")
            });
        }

        paymentStatusTable.setModel(model);
    }


    // =========================
    // MAIN METHOD
    // =========================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            ReportForm form = new ReportForm();

            form.setVisible(true);
        });
    }
}

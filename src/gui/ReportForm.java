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

        // TITLE
        JLabel titleLabel = new JLabel(
                "REPORTS & ANALYTICS",
                SwingConstants.CENTER
        );
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(25, 55, 90));
        add(titleLabel, BorderLayout.NORTH);

        // MAIN PANEL
        JPanel mainPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        // SUMMARY
        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 10, 10));

        totalBookingsLabel = new JLabel(
                "Total Bookings: 0", SwingConstants.CENTER);
        totalRevenueLabel = new JLabel(
                "Total Revenue: ₹0.00", SwingConstants.CENTER);
        popularEventLabel = new JLabel(
                "Popular Event: None", SwingConstants.CENTER);

        Font labelFont = new Font("Arial", Font.BOLD, 16);

        totalBookingsLabel.setFont(labelFont);
        totalRevenueLabel.setFont(labelFont);
        popularEventLabel.setFont(labelFont);

        totalBookingsLabel.setForeground(new Color(45, 85, 130));
        totalRevenueLabel.setForeground(new Color(55, 140, 100));
        popularEventLabel.setForeground(new Color(25, 55, 90));

        summaryPanel.add(totalBookingsLabel);
        summaryPanel.add(totalRevenueLabel);
        summaryPanel.add(popularEventLabel);

        mainPanel.add(summaryPanel);

        // EVENT-WISE REVENUE
        JPanel eventPanel = new JPanel(new BorderLayout());

        JLabel eventTitle = new JLabel(
                "Event-wise Revenue", SwingConstants.CENTER);
        eventTitle.setFont(new Font("Arial", Font.BOLD, 18));
        eventTitle.setForeground(new Color(25, 55, 90));

        eventPanel.add(eventTitle, BorderLayout.NORTH);

        eventRevenueTable = new JTable();
        eventRevenueTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 13));

        eventPanel.add(
                new JScrollPane(eventRevenueTable),
                BorderLayout.CENTER);

        mainPanel.add(eventPanel);

        // PAYMENT STATUS
        JPanel paymentPanel = new JPanel(new BorderLayout());

        JLabel paymentTitle = new JLabel(
                "Payment Status Summary", SwingConstants.CENTER);
        paymentTitle.setFont(new Font("Arial", Font.BOLD, 18));
        paymentTitle.setForeground(new Color(25, 55, 90));

        paymentPanel.add(paymentTitle, BorderLayout.NORTH);

        paymentStatusTable = new JTable();
        paymentStatusTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 13));

        paymentPanel.add(
                new JScrollPane(paymentStatusTable),
                BorderLayout.CENTER);

        mainPanel.add(paymentPanel);

        add(mainPanel, BorderLayout.CENTER);

        // REFRESH BUTTON
        JButton refreshButton = new JButton("Refresh Reports");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        refreshButton.setBackground(new Color(45, 85, 130));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);

        refreshButton.addActionListener(e -> loadReports());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.SOUTH);

        loadReports();
        setVisible(true);
    }

    // LOAD REPORTS
    private void loadReports() {

        int totalBookings = reportService.getTotalBookings();
        totalBookingsLabel.setText(
                "Total Bookings: " + totalBookings);

        double totalRevenue = reportService.getTotalRevenue();
        totalRevenueLabel.setText(
                String.format("Total Revenue: ₹%.2f", totalRevenue));

        String popularEvent = reportService.getPopularEvent();
        popularEventLabel.setText(
                "Popular Event: " + popularEvent);

        loadEventWiseRevenue();
        loadPaymentStatus();
    }

    // EVENT-WISE REVENUE
    private void loadEventWiseRevenue() {

        List<Map<String, Object>> data =
                reportService.getEventWiseRevenue();

        String[] columns = {
                "Event Name", "Tickets Sold", "Revenue"
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

    // PAYMENT STATUS
    private void loadPaymentStatus() {

        List<Map<String, Object>> data =
                reportService.getPaymentStatusSummary();

        String[] columns = {
                "Payment Status", "Total Payments"
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

    // MAIN
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new ReportForm());
    }
}
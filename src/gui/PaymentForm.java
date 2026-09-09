package gui;

import model.Payment;
import service.PaymentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class PaymentForm extends JFrame implements ActionListener {

    private JTextField txtPaymentId, txtBookingId, txtAmount, txtPaymentDate;
    private JComboBox<String> cmbPaymentMethod, cmbPaymentStatus;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnRefresh;
    private JTable paymentTable;
    private DefaultTableModel tableModel;
    private PaymentService paymentService;

    public PaymentForm() {

        paymentService = new PaymentService();

        setTitle("Payment Management");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        createGUI();
        loadPayments();
        setVisible(true);
    }

    private void createGUI() {

        setLayout(new BorderLayout(10, 10));

        // TITLE
        JLabel title = new JLabel("PAYMENT MANAGEMENT", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(25, 55, 90));
        add(title, BorderLayout.NORTH);

        // FORM
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        formPanel.add(label("Payment ID:"));
        txtPaymentId = new JTextField();
        txtPaymentId.setEditable(false);
        formPanel.add(txtPaymentId);

        formPanel.add(label("Booking ID:"));
        txtBookingId = new JTextField();
        formPanel.add(txtBookingId);

        formPanel.add(label("Amount:"));
        txtAmount = new JTextField();
        txtAmount.setEditable(false);
        formPanel.add(txtAmount);

        formPanel.add(label("Payment Method:"));
        cmbPaymentMethod = new JComboBox<>(
                new String[]{"UPI", "Card", "Cash", "Net Banking"});
        formPanel.add(cmbPaymentMethod);

        formPanel.add(label("Payment Status:"));
        cmbPaymentStatus = new JComboBox<>(
                new String[]{"Paid", "Pending", "Failed"});
        formPanel.add(cmbPaymentStatus);

        formPanel.add(label("Payment Date:"));
        txtPaymentDate = new JTextField();
        formPanel.add(txtPaymentDate);

        add(formPanel, BorderLayout.WEST);

        // AUTO AMOUNT
        txtBookingId.addActionListener(e -> fetchBookingAmount());

        txtBookingId.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!txtBookingId.getText().trim().isEmpty())
                    fetchBookingAmount();
            }
        });

        // BUTTONS
        JPanel buttonPanel = new JPanel(new FlowLayout());

        btnAdd = new JButton("Add Payment");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");
        btnRefresh = new JButton("Refresh");

        styleButton(btnAdd, new Color(45, 85, 130));
        styleButton(btnUpdate, new Color(55, 140, 100));
        styleButton(btnDelete, new Color(190, 70, 70));
        styleButton(btnClear, new Color(120, 120, 120));
        styleButton(btnRefresh, new Color(120, 80, 150));

        btnAdd.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnClear.addActionListener(this);
        btnRefresh.addActionListener(this);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnRefresh);

        // TABLE
        String[] columns = {
                "Payment ID", "Booking ID", "Amount",
                "Payment Method", "Payment Status", "Payment Date"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        paymentTable = new JTable(tableModel);
        paymentTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);

        paymentTable.getTableHeader().setBackground(
                new Color(45, 85, 130));
        paymentTable.getTableHeader().setForeground(Color.WHITE);
        paymentTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 13));

        paymentTable.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int row = paymentTable.getSelectedRow();

                if (row != -1) {
                    txtPaymentId.setText(
                            tableModel.getValueAt(row, 0).toString());
                    txtBookingId.setText(
                            tableModel.getValueAt(row, 1).toString());
                    txtAmount.setText(
                            tableModel.getValueAt(row, 2).toString());

                    cmbPaymentMethod.setSelectedItem(
                            tableModel.getValueAt(row, 3).toString());

                    cmbPaymentStatus.setSelectedItem(
                            tableModel.getValueAt(row, 4).toString());

                    txtPaymentDate.setText(
                            tableModel.getValueAt(row, 5).toString());
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(paymentTable);

        JPanel centerPanel = new JPanel(
                new BorderLayout(10, 10));

        centerPanel.add(buttonPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }

    // LABEL STYLE
    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Arial", Font.BOLD, 13));
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

    // FETCH BOOKING AMOUNT
    private void fetchBookingAmount() {

        String text = txtBookingId.getText().trim();

        if (text.isEmpty()) {
            txtAmount.setText("");
            return;
        }

        try {

            int bookingId = Integer.parseInt(text);

            if (bookingId <= 0) {
                txtAmount.setText("");
                showError("Booking ID must be positive.",
                        "Invalid Booking ID");
                return;
            }

            double amount =
                    paymentService.getBookingAmount(bookingId);

            if (amount == -1) {
                txtAmount.setText("");
                showError("Booking ID not found.",
                        "Invalid Booking ID");
            } else {
                txtAmount.setText(String.format("%.2f", amount));
            }

        } catch (NumberFormatException e) {

            txtAmount.setText("");
            showError("Booking ID must be a valid number.",
                    "Invalid Booking ID");
        }
    }

    // ADD PAYMENT
    private void addPayment() {

        if (!fetchAndValidateBooking() || !validateFields())
            return;

        try {

            Payment payment = new Payment();

            payment.setBookingId(
                    Integer.parseInt(txtBookingId.getText().trim()));

            payment.setAmount(
                    Double.parseDouble(txtAmount.getText().trim()));

            payment.setPaymentMethod(
                    cmbPaymentMethod.getSelectedItem().toString());

            payment.setPaymentStatus(
                    cmbPaymentStatus.getSelectedItem().toString());

            payment.setPaymentDate(
                    txtPaymentDate.getText().trim());

            boolean result = paymentService.addPayment(payment);

            if (result) {

                JOptionPane.showMessageDialog(
                        this,
                        "Payment added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                clearFields();
                loadPayments();

            } else {
                showError("Failed to add payment.", "Error");
            }

        } catch (NumberFormatException e) {
            showError("Invalid Booking ID.", "Error");
        }
    }

    // FETCH + VALIDATE BOOKING
    private boolean fetchAndValidateBooking() {

        String text = txtBookingId.getText().trim();

        if (text.isEmpty()) {
            showError("Please enter Booking ID.",
                    "Validation Error");
            return false;
        }

        try {

            int bookingId = Integer.parseInt(text);

            if (bookingId <= 0) {
                showError("Booking ID must be positive.",
                        "Validation Error");
                return false;
            }

            double amount =
                    paymentService.getBookingAmount(bookingId);

            if (amount == -1) {

                txtAmount.setText("");
                showError("Booking ID does not exist.",
                        "Validation Error");
                return false;
            }

            txtAmount.setText(String.format("%.2f", amount));
            return true;

        } catch (NumberFormatException e) {

            showError("Booking ID must be a valid number.",
                    "Validation Error");
            return false;
        }
    }

    // UPDATE PAYMENT
    private void updatePayment() {

        if (txtPaymentId.getText().trim().isEmpty()) {
            showError("Please select a payment to update.",
                    "Validation Error");
            return;
        }

        if (!fetchAndValidateBooking() || !validateFields())
            return;

        try {

            Payment payment = new Payment();

            payment.setPaymentId(
                    Integer.parseInt(txtPaymentId.getText().trim()));

            payment.setBookingId(
                    Integer.parseInt(txtBookingId.getText().trim()));

            payment.setAmount(
                    Double.parseDouble(txtAmount.getText().trim()));

            payment.setPaymentMethod(
                    cmbPaymentMethod.getSelectedItem().toString());

            payment.setPaymentStatus(
                    cmbPaymentStatus.getSelectedItem().toString());

            payment.setPaymentDate(
                    txtPaymentDate.getText().trim());

            boolean result =
                    paymentService.updatePayment(payment);

            if (result) {

                JOptionPane.showMessageDialog(
                        this,
                        "Payment updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                clearFields();
                loadPayments();

            } else {
                showError("Failed to update payment.", "Error");
            }

        } catch (NumberFormatException e) {
            showError("Please enter valid values.", "Error");
        }
    }

    // DELETE PAYMENT
    private void deletePayment() {

        if (txtPaymentId.getText().trim().isEmpty()) {
            showError("Please select a payment to delete.",
                    "Validation Error");
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this payment?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {

            try {

                int paymentId =
                        Integer.parseInt(
                                txtPaymentId.getText().trim());

                boolean result =
                        paymentService.deletePayment(paymentId);

                if (result) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Payment deleted successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);

                    clearFields();
                    loadPayments();

                } else {
                    showError("Failed to delete payment.", "Error");
                }

            } catch (NumberFormatException e) {
                showError("Invalid Payment ID.", "Error");
            }
        }
    }

    // LOAD PAYMENTS
    private void loadPayments() {

        tableModel.setRowCount(0);

        List<Payment> payments =
                paymentService.getAllPayments();

        for (Payment payment : payments) {

            tableModel.addRow(new Object[]{
                    payment.getPaymentId(),
                    payment.getBookingId(),
                    payment.getAmount(),
                    payment.getPaymentMethod(),
                    payment.getPaymentStatus(),
                    payment.getPaymentDate()
            });
        }
    }

    // CLEAR FIELDS
    private void clearFields() {

        txtPaymentId.setText("");
        txtBookingId.setText("");
        txtAmount.setText("");
        txtPaymentDate.setText("");

        cmbPaymentMethod.setSelectedIndex(0);
        cmbPaymentStatus.setSelectedIndex(0);

        paymentTable.clearSelection();
    }

    // VALIDATION
    private boolean validateFields() {

        if (txtBookingId.getText().trim().isEmpty()
                || txtAmount.getText().trim().isEmpty()
                || txtPaymentDate.getText().trim().isEmpty()) {

            showError("Please fill all required fields.",
                    "Validation Error");
            return false;
        }

        try {

            int bookingId =
                    Integer.parseInt(
                            txtBookingId.getText().trim());

            double amount =
                    Double.parseDouble(
                            txtAmount.getText().trim());

            if (bookingId <= 0) {
                showError("Booking ID must be positive.",
                        "Validation Error");
                return false;
            }

            if (amount < 0) {
                showError("Amount cannot be negative.",
                        "Validation Error");
                return false;
            }

        } catch (NumberFormatException e) {

            showError("Invalid Booking ID.",
                    "Validation Error");
            return false;
        }

        return true;
    }

    // BUTTON ACTIONS
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnAdd)
            addPayment();

        else if (e.getSource() == btnUpdate)
            updatePayment();

        else if (e.getSource() == btnDelete)
            deletePayment();

        else if (e.getSource() == btnClear)
            clearFields();

        else if (e.getSource() == btnRefresh)
            loadPayments();
    }

    // ERROR MESSAGE
    private void showError(String message, String title) {

        JOptionPane.showMessageDialog(
                this,
                message,
                title,
                JOptionPane.ERROR_MESSAGE);
    }

    // MAIN
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaymentForm());
    }
}
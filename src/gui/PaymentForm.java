package gui;

import model.Payment;
import service.PaymentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PaymentForm extends JFrame implements ActionListener {

    private JTextField txtPaymentId;
    private JTextField txtBookingId;
    private JTextField txtAmount;
    private JTextField txtPaymentDate;

    private JComboBox<String> cmbPaymentMethod;
    private JComboBox<String> cmbPaymentStatus;

    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;
    private JButton btnRefresh;

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
    }

    private void createGUI() {

        setLayout(new BorderLayout(10, 10));

        // =========================
        // TITLE
        // =========================

        JLabel title = new JLabel("PAYMENT MANAGEMENT", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        add(title, BorderLayout.NORTH);

        // =========================
        // FORM PANEL
        // =========================

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        );

        formPanel.add(new JLabel("Payment ID:"));
        txtPaymentId = new JTextField();
        txtPaymentId.setEditable(false);
        formPanel.add(txtPaymentId);

        formPanel.add(new JLabel("Booking ID:"));
        txtBookingId = new JTextField();
        formPanel.add(txtBookingId);

        formPanel.add(new JLabel("Amount:"));
        txtAmount = new JTextField();
        formPanel.add(txtAmount);

        formPanel.add(new JLabel("Payment Method:"));

        cmbPaymentMethod = new JComboBox<>(
                new String[]{
                        "UPI",
                        "Card",
                        "Cash",
                        "Net Banking"
                }
        );

        formPanel.add(cmbPaymentMethod);

        formPanel.add(new JLabel("Payment Status:"));

        cmbPaymentStatus = new JComboBox<>(
                new String[]{
                        "Paid",
                        "Pending",
                        "Failed"
                }
        );

        formPanel.add(cmbPaymentStatus);

        formPanel.add(new JLabel("Payment Date:"));
        txtPaymentDate = new JTextField();
        formPanel.add(txtPaymentDate);

        add(formPanel, BorderLayout.WEST);

        // =========================
        // BUTTON PANEL
        // =========================

        JPanel buttonPanel = new JPanel(new FlowLayout());

        btnAdd = new JButton("Add Payment");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");
        btnRefresh = new JButton("Refresh");

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

        // =========================
        // TABLE
        // =========================

        String[] columns = {
                "Payment ID",
                "Booking ID",
                "Amount",
                "Payment Method",
                "Payment Status",
                "Payment Date"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        paymentTable = new JTable(tableModel);

        paymentTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        paymentTable.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {

                        int row = paymentTable.getSelectedRow();

                        if (row != -1) {

                            txtPaymentId.setText(
                                    tableModel.getValueAt(row, 0).toString()
                            );

                            txtBookingId.setText(
                                    tableModel.getValueAt(row, 1).toString()
                            );

                            txtAmount.setText(
                                    tableModel.getValueAt(row, 2).toString()
                            );

                            cmbPaymentMethod.setSelectedItem(
                                    tableModel.getValueAt(row, 3).toString()
                            );

                            cmbPaymentStatus.setSelectedItem(
                                    tableModel.getValueAt(row, 4).toString()
                            );

                            txtPaymentDate.setText(
                                    tableModel.getValueAt(row, 5).toString()
                            );
                        }
                    }
                });

        JScrollPane scrollPane = new JScrollPane(paymentTable);

        // =========================
        // CENTER PANEL
        // =========================

        JPanel centerPanel = new JPanel(
                new BorderLayout(10, 10)
        );

        centerPanel.add(buttonPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }

    // =========================
    // ADD PAYMENT
    // =========================

    private void addPayment() {

        if (!validateFields()) {
            return;
        }

        try {

            Payment payment = new Payment();

            payment.setBookingId(
                    Integer.parseInt(
                            txtBookingId.getText().trim()
                    )
            );

            payment.setAmount(
                    Double.parseDouble(
                            txtAmount.getText().trim()
                    )
            );

            payment.setPaymentMethod(
                    cmbPaymentMethod.getSelectedItem().toString()
            );

            payment.setPaymentStatus(
                    cmbPaymentStatus.getSelectedItem().toString()
            );

            payment.setPaymentDate(
                    txtPaymentDate.getText().trim()
            );

            boolean result =
                    paymentService.addPayment(payment);

            if (result) {

                JOptionPane.showMessageDialog(
                        this,
                        "Payment added successfully!"
                );

                clearFields();
                loadPayments();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to add payment."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Booking ID must be an integer and amount must be a number."
            );
        }
    }

    // =========================
    // UPDATE PAYMENT
    // =========================

    private void updatePayment() {

        if (txtPaymentId.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a payment to update."
            );

            return;
        }

        if (!validateFields()) {
            return;
        }

        try {

            Payment payment = new Payment();

            payment.setPaymentId(
                    Integer.parseInt(
                            txtPaymentId.getText().trim()
                    )
            );

            payment.setBookingId(
                    Integer.parseInt(
                            txtBookingId.getText().trim()
                    )
            );

            payment.setAmount(
                    Double.parseDouble(
                            txtAmount.getText().trim()
                    )
            );

            payment.setPaymentMethod(
                    cmbPaymentMethod.getSelectedItem().toString()
            );

            payment.setPaymentStatus(
                    cmbPaymentStatus.getSelectedItem().toString()
            );

            payment.setPaymentDate(
                    txtPaymentDate.getText().trim()
            );

            boolean result =
                    paymentService.updatePayment(payment);

            if (result) {

                JOptionPane.showMessageDialog(
                        this,
                        "Payment updated successfully!"
                );

                clearFields();
                loadPayments();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to update payment."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numeric values."
            );
        }
    }

    // =========================
    // DELETE PAYMENT
    // =========================

    private void deletePayment() {

        if (txtPaymentId.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a payment to delete."
            );

            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this payment?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {

            try {

                int paymentId = Integer.parseInt(
                        txtPaymentId.getText().trim()
                );

                boolean result =
                        paymentService.deletePayment(paymentId);

                if (result) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Payment deleted successfully!"
                    );

                    clearFields();
                    loadPayments();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Failed to delete payment."
                    );
                }

            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Payment ID."
                );
            }
        }
    }

    // =========================
    // LOAD PAYMENTS
    // =========================

    private void loadPayments() {

        tableModel.setRowCount(0);

        List<Payment> payments =
                paymentService.getAllPayments();

        for (Payment payment : payments) {

            Object[] row = {
                    payment.getPaymentId(),
                    payment.getBookingId(),
                    payment.getAmount(),
                    payment.getPaymentMethod(),
                    payment.getPaymentStatus(),
                    payment.getPaymentDate()
            };

            tableModel.addRow(row);
        }
    }

    // =========================
    // CLEAR FIELDS
    // =========================

    private void clearFields() {

        txtPaymentId.setText("");
        txtBookingId.setText("");
        txtAmount.setText("");
        txtPaymentDate.setText("");

        cmbPaymentMethod.setSelectedIndex(0);
        cmbPaymentStatus.setSelectedIndex(0);

        paymentTable.clearSelection();
    }

    // =========================
    // VALIDATION
    // =========================

    private boolean validateFields() {

        if (txtBookingId.getText().trim().isEmpty()
                || txtAmount.getText().trim().isEmpty()
                || txtPaymentDate.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields."
            );

            return false;
        }

        try {

            int bookingId = Integer.parseInt(
                    txtBookingId.getText().trim()
            );

            double amount = Double.parseDouble(
                    txtAmount.getText().trim()
            );

            if (bookingId <= 0 || amount < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Booking ID must be positive and amount cannot be negative."
                );

                return false;
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter a valid Booking ID and amount."
            );

            return false;
        }

        return true;
    }

    // =========================
    // BUTTON ACTIONS
    // =========================

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnAdd) {

            addPayment();

        } else if (e.getSource() == btnUpdate) {

            updatePayment();

        } else if (e.getSource() == btnDelete) {

            deletePayment();

        } else if (e.getSource() == btnClear) {

            clearFields();

        } else if (e.getSource() == btnRefresh) {

            loadPayments();
        }
    }

    // =========================
    // MAIN METHOD FOR TESTING
    // =========================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            PaymentForm form = new PaymentForm();
            form.setVisible(true);
        });
    }
}

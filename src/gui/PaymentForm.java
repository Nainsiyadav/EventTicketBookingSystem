package gui;

import model.Payment;
import service.PaymentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class PaymentForm extends JFrame implements ActionListener {

    private JTextField txtPaymentId, txtBookingId,
            txtAmount, txtPaymentDate;

    private JComboBox<String> cmbPaymentMethod,
            cmbPaymentStatus;

    private JButton btnAdd, btnUpdate, btnDelete,
            btnClear, btnRefresh, btnBack;

    private JTable paymentTable;

    private DefaultTableModel tableModel;

    private PaymentService paymentService;

    public PaymentForm() {

        paymentService = new PaymentService();

        setTitle("Payment Management");

        // ================= FULL SCREEN =================

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ================= BACKGROUND =================

        JPanel mainBackground =
                new JPanel(new GridBagLayout());

        mainBackground.setBackground(
                new Color(235, 242, 250)
        );

        setContentPane(mainBackground);

        // ================= WHITE CARD =================

        JPanel card = new RoundedPanel(35);

        card.setPreferredSize(
                new Dimension(1150, 700)
        );

        card.setBackground(Color.WHITE);

        card.setLayout(
                new BorderLayout(15, 15)
        );

        mainBackground.add(card);

        // ================= TITLE =================

        JLabel title =
                new JLabel(
                        "PAYMENT MANAGEMENT",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        30
                )
        );

        title.setForeground(
                new Color(25, 55, 90)
        );

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 10, 20
                )
        );

        card.add(
                title,
                BorderLayout.NORTH
        );

        // ================= FORM =================

        JPanel formPanel =
                new JPanel(
                        new GridLayout(
                                6,
                                2,
                                12,
                                12
                        )
                );

        formPanel.setBackground(Color.WHITE);

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        25,
                        15,
                        20
                )
        );

        // Payment ID

        formPanel.add(
                label("Payment ID:")
        );

        txtPaymentId =
                new JTextField();

        txtPaymentId.setEditable(false);

        styleTextField(txtPaymentId);

        formPanel.add(txtPaymentId);

        // Booking ID

        formPanel.add(
                label("Booking ID:")
        );

        txtBookingId =
                new JTextField();

        styleTextField(txtBookingId);

        formPanel.add(txtBookingId);

        // Amount

        formPanel.add(
                label("Amount:")
        );

        txtAmount =
                new JTextField();

        txtAmount.setEditable(false);

        styleTextField(txtAmount);

        formPanel.add(txtAmount);

        // Payment Method

        formPanel.add(
                label("Payment Method:")
        );

        cmbPaymentMethod =
                new JComboBox<>(
                        new String[]{
                                "UPI",
                                "Card",
                                "Cash",
                                "Net Banking"
                        }
                );

        styleComboBox(cmbPaymentMethod);

        formPanel.add(
                cmbPaymentMethod
        );

        // Payment Status

        formPanel.add(
                label("Payment Status:")
        );

        cmbPaymentStatus =
                new JComboBox<>(
                        new String[]{
                                "Paid",
                                "Pending",
                                "Failed"
                        }
                );

        styleComboBox(cmbPaymentStatus);

        formPanel.add(
                cmbPaymentStatus
        );

        // Payment Date

        formPanel.add(
                label("Payment Date:")
        );

        txtPaymentDate =
                new JTextField();

        styleTextField(txtPaymentDate);

        formPanel.add(
                txtPaymentDate
        );

        card.add(
                formPanel,
                BorderLayout.WEST
        );

        // ================= AUTO AMOUNT =================

        txtBookingId.addActionListener(
                e -> fetchBookingAmount()
        );

        txtBookingId.addFocusListener(
                new FocusAdapter() {

                    @Override
                    public void focusLost(
                            FocusEvent e
                    ) {

                        if (!txtBookingId
                                .getText()
                                .trim()
                                .isEmpty()) {

                            fetchBookingAmount();
                        }
                    }
                }
        );

        // ================= TABLE =================

        String[] columns = {

                "Payment ID",
                "Booking ID",
                "Amount",
                "Payment Method",
                "Payment Status",
                "Payment Date"
        };

        tableModel =
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

        paymentTable =
                new JTable(tableModel);

        styleTable(paymentTable);

        paymentTable
                .getSelectionModel()
                .addListSelectionListener(
                        e -> {

                            if (!e.getValueIsAdjusting()) {

                                int row =
                                        paymentTable
                                                .getSelectedRow();

                                if (row != -1) {

                                    txtPaymentId.setText(
                                            tableModel
                                                    .getValueAt(
                                                            row,
                                                            0
                                                    )
                                                    .toString()
                                    );

                                    txtBookingId.setText(
                                            tableModel
                                                    .getValueAt(
                                                            row,
                                                            1
                                                    )
                                                    .toString()
                                    );

                                    txtAmount.setText(
                                            tableModel
                                                    .getValueAt(
                                                            row,
                                                            2
                                                    )
                                                    .toString()
                                    );

                                    cmbPaymentMethod
                                            .setSelectedItem(
                                                    tableModel
                                                            .getValueAt(
                                                                    row,
                                                                    3
                                                            )
                                                            .toString()
                                            );

                                    cmbPaymentStatus
                                            .setSelectedItem(
                                                    tableModel
                                                            .getValueAt(
                                                                    row,
                                                                    4
                                                            )
                                                            .toString()
                                            );

                                    txtPaymentDate.setText(
                                            tableModel
                                                    .getValueAt(
                                                            row,
                                                            5
                                                    )
                                                    .toString()
                                    );
                                }
                            }
                        }
                );

        JScrollPane scrollPane =
                new JScrollPane(paymentTable);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                200,
                                210,
                                220
                        )
                )
        );

        JPanel tablePanel =
                new JPanel(
                        new BorderLayout()
                );

        tablePanel.setBackground(
                Color.WHITE
        );

        tablePanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        10,
                        5,
                        20
                )
        );

        tablePanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        card.add(
                tablePanel,
                BorderLayout.CENTER
        );

        // ================= BUTTONS =================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                12,
                                12
                        )
                );

        buttonPanel.setBackground(
                Color.WHITE
        );

        btnAdd =
                createButton(
                        "Add Payment",
                        new Color(
                                45,
                                130,
                                200
                        )
                );

        btnUpdate =
                createButton(
                        "Update",
                        new Color(
                                55,
                                160,
                                110
                        )
                );

        btnDelete =
                createButton(
                        "Delete",
                        new Color(
                                210,
                                70,
                                70
                        )
                );

        btnClear =
                createButton(
                        "Clear",
                        new Color(
                                120,
                                120,
                                120
                        )
                );

        btnRefresh =
                createButton(
                        "Refresh",
                        new Color(
                                125,
                                85,
                                175
                        )
                );

        btnBack =
                createButton(
                        "← Back",
                        new Color(
                                65,
                                65,
                                75
                        )
                );

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnBack);

        btnAdd.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnClear.addActionListener(this);
        btnRefresh.addActionListener(this);
        btnBack.addActionListener(this);

        card.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // ================= LOAD PAYMENTS =================

        loadPayments();

        setVisible(true);
    }

    // =====================================================
    // LABEL STYLE
    // =====================================================

    private JLabel label(String text) {

        JLabel l =
                new JLabel(text);

        l.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        l.setForeground(
                new Color(
                        60,
                        70,
                        80
                )
        );

        return l;
    }

    // =====================================================
    // TEXT FIELD STYLE
    // =====================================================

    private void styleTextField(
            JTextField field
    ) {

        field.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        180,
                                        195,
                                        210
                                )
                        ),
                        BorderFactory.createEmptyBorder(
                                5,
                                8,
                                5,
                                8
                        )
                )
        );
    }

    // =====================================================
    // COMBO BOX STYLE
    // =====================================================

    private void styleComboBox(
            JComboBox<String> combo
    ) {

        combo.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        combo.setBackground(Color.WHITE);
    }

    // =====================================================
    // TABLE STYLE
    // =====================================================

    private void styleTable(
            JTable table
    ) {

        table.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        table.setRowHeight(30);

        table.setGridColor(
                new Color(
                        210,
                        215,
                        220
                )
        );

        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        table.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                13
                        )
                );

        table.getTableHeader()
                .setBackground(
                        new Color(
                                45,
                                85,
                                130
                        )
                );

        table.getTableHeader()
                .setForeground(
                        Color.WHITE
                );

        table.getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                35
                        )
                );
    }

    // =====================================================
    // BUTTON STYLE
    // =====================================================

    private JButton createButton(
            String text,
            Color color
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        button.setBackground(color);

        button.setForeground(
                Color.WHITE
        );

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setPreferredSize(
                new Dimension(
                        130,
                        40
                )
        );

        return button;
    }

    // =====================================================
    // FETCH BOOKING AMOUNT
    // =====================================================

    private void fetchBookingAmount() {

        String text =
                txtBookingId
                        .getText()
                        .trim();

        if (text.isEmpty()) {

            txtAmount.setText("");

            return;
        }

        try {

            int bookingId =
                    Integer.parseInt(text);

            if (bookingId <= 0) {

                txtAmount.setText("");

                showError(
                        "Booking ID must be positive.",
                        "Invalid Booking ID"
                );

                return;
            }

            double amount =
                    paymentService
                            .getBookingAmount(
                                    bookingId
                            );

            if (amount == -1) {

                txtAmount.setText("");

                showError(
                        "Booking ID not found.",
                        "Invalid Booking ID"
                );

            } else {

                txtAmount.setText(
                        String.format(
                                "%.2f",
                                amount
                        )
                );
            }

        } catch (
                NumberFormatException e
        ) {

            txtAmount.setText("");

            showError(
                    "Booking ID must be a valid number.",
                    "Invalid Booking ID"
            );
        }
    }

    // =====================================================
    // ADD PAYMENT
    // =====================================================

    private void addPayment() {

        if (!fetchAndValidateBooking()
                || !validateFields()) {

            return;
        }

        try {

            Payment payment =
                    new Payment();

            payment.setBookingId(
                    Integer.parseInt(
                            txtBookingId
                                    .getText()
                                    .trim()
                    )
            );

            payment.setAmount(
                    Double.parseDouble(
                            txtAmount
                                    .getText()
                                    .trim()
                    )
            );

            payment.setPaymentMethod(
                    cmbPaymentMethod
                            .getSelectedItem()
                            .toString()
            );

            payment.setPaymentStatus(
                    cmbPaymentStatus
                            .getSelectedItem()
                            .toString()
            );

            payment.setPaymentDate(
                    txtPaymentDate
                            .getText()
                            .trim()
            );

            boolean result =
                    paymentService
                            .addPayment(payment);

            if (result) {

                JOptionPane.showMessageDialog(
                        this,
                        "Payment added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearFields();

                loadPayments();

            } else {

                showError(
                        "Failed to add payment.",
                        "Error"
                );
            }

        } catch (
                NumberFormatException e
        ) {

            showError(
                    "Invalid Booking ID.",
                    "Error"
            );
        }
    }

    // =====================================================
    // FETCH + VALIDATE BOOKING
    // =====================================================

    private boolean fetchAndValidateBooking() {

        String text =
                txtBookingId
                        .getText()
                        .trim();

        if (text.isEmpty()) {

            showError(
                    "Please enter Booking ID.",
                    "Validation Error"
            );

            return false;
        }

        try {

            int bookingId =
                    Integer.parseInt(text);

            if (bookingId <= 0) {

                showError(
                        "Booking ID must be positive.",
                        "Validation Error"
                );

                return false;
            }

            double amount =
                    paymentService
                            .getBookingAmount(
                                    bookingId
                            );

            if (amount == -1) {

                txtAmount.setText("");

                showError(
                        "Booking ID does not exist.",
                        "Validation Error"
                );

                return false;
            }

            txtAmount.setText(
                    String.format(
                            "%.2f",
                            amount
                    )
            );

            return true;

        } catch (
                NumberFormatException e
        ) {

            showError(
                    "Booking ID must be a valid number.",
                    "Validation Error"
            );

            return false;
        }
    }

    // =====================================================
    // UPDATE PAYMENT
    // =====================================================

    private void updatePayment() {

        if (txtPaymentId
                .getText()
                .trim()
                .isEmpty()) {

            showError(
                    "Please select a payment to update.",
                    "Validation Error"
            );

            return;
        }

        if (!fetchAndValidateBooking()
                || !validateFields()) {

            return;
        }

        try {

            Payment payment =
                    new Payment();

            payment.setPaymentId(
                    Integer.parseInt(
                            txtPaymentId
                                    .getText()
                                    .trim()
                    )
            );

            payment.setBookingId(
                    Integer.parseInt(
                            txtBookingId
                                    .getText()
                                    .trim()
                    )
            );

            payment.setAmount(
                    Double.parseDouble(
                            txtAmount
                                    .getText()
                                    .trim()
                    )
            );

            payment.setPaymentMethod(
                    cmbPaymentMethod
                            .getSelectedItem()
                            .toString()
            );

            payment.setPaymentStatus(
                    cmbPaymentStatus
                            .getSelectedItem()
                            .toString()
            );

            payment.setPaymentDate(
                    txtPaymentDate
                            .getText()
                            .trim()
            );

            boolean result =
                    paymentService
                            .updatePayment(
                                    payment
                            );

            if (result) {

                JOptionPane.showMessageDialog(
                        this,
                        "Payment updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearFields();

                loadPayments();

            } else {

                showError(
                        "Failed to update payment.",
                        "Error"
                );
            }

        } catch (
                NumberFormatException e
        ) {

            showError(
                    "Please enter valid values.",
                    "Error"
            );
        }
    }

    // =====================================================
    // DELETE PAYMENT
    // =====================================================

    private void deletePayment() {

        if (txtPaymentId
                .getText()
                .trim()
                .isEmpty()) {

            showError(
                    "Please select a payment to delete.",
                    "Validation Error"
            );

            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this payment?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice ==
                JOptionPane.YES_OPTION) {

            try {

                int paymentId =
                        Integer.parseInt(
                                txtPaymentId
                                        .getText()
                                        .trim()
                        );

                boolean result =
                        paymentService
                                .deletePayment(
                                        paymentId
                                );

                if (result) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Payment deleted successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    clearFields();

                    loadPayments();

                } else {

                    showError(
                            "Failed to delete payment.",
                            "Error"
                    );
                }

            } catch (
                    NumberFormatException e
            ) {

                showError(
                        "Invalid Payment ID.",
                        "Error"
                );
            }
        }
    }

    // =====================================================
    // LOAD PAYMENTS
    // =====================================================

    private void loadPayments() {

        tableModel.setRowCount(0);

        List<Payment> payments =
                paymentService
                        .getAllPayments();

        for (Payment payment : payments) {

            tableModel.addRow(
                    new Object[]{

                            payment.getPaymentId(),

                            payment.getBookingId(),

                            payment.getAmount(),

                            payment.getPaymentMethod(),

                            payment.getPaymentStatus(),

                            payment.getPaymentDate()
                    }
            );
        }
    }

    // =====================================================
    // CLEAR FIELDS
    // =====================================================

    private void clearFields() {

        txtPaymentId.setText("");

        txtBookingId.setText("");

        txtAmount.setText("");

        txtPaymentDate.setText("");

        cmbPaymentMethod
                .setSelectedIndex(0);

        cmbPaymentStatus
                .setSelectedIndex(0);

        paymentTable.clearSelection();
    }

    // =====================================================
    // VALIDATION
    // =====================================================

    private boolean validateFields() {

        if (txtBookingId
                .getText()
                .trim()
                .isEmpty()
                || txtAmount
                .getText()
                .trim()
                .isEmpty()
                || txtPaymentDate
                .getText()
                .trim()
                .isEmpty()) {

            showError(
                    "Please fill all required fields.",
                    "Validation Error"
            );

            return false;
        }

        try {

            int bookingId =
                    Integer.parseInt(
                            txtBookingId
                                    .getText()
                                    .trim()
                    );

            double amount =
                    Double.parseDouble(
                            txtAmount
                                    .getText()
                                    .trim()
                    );

            if (bookingId <= 0) {

                showError(
                        "Booking ID must be positive.",
                        "Validation Error"
                );

                return false;
            }

            if (amount < 0) {

                showError(
                        "Amount cannot be negative.",
                        "Validation Error"
                );

                return false;
            }

        } catch (
                NumberFormatException e
        ) {

            showError(
                    "Invalid Booking ID.",
                    "Validation Error"
            );

            return false;
        }

        return true;
    }

    // =====================================================
    // BUTTON ACTIONS
    // =====================================================

    @Override
    public void actionPerformed(
            ActionEvent e
    ) {

        if (e.getSource() == btnAdd) {

            addPayment();

        } else if (
                e.getSource() == btnUpdate
        ) {

            updatePayment();

        } else if (
                e.getSource() == btnDelete
        ) {

            deletePayment();

        } else if (
                e.getSource() == btnClear
        ) {

            clearFields();

        } else if (
                e.getSource() == btnRefresh
        ) {

            loadPayments();

        } else if (
                e.getSource() == btnBack
        ) {

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
    // ERROR MESSAGE
    // =====================================================

    private void showError(
            String message,
            String title
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                title,
                JOptionPane.ERROR_MESSAGE
        );
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
                Graphics g
        ) {

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
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> new PaymentForm()
        );
    }
}
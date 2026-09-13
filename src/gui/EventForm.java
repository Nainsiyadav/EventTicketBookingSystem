package gui;

import model.Event;
import service.EventService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class EventForm extends JFrame implements ActionListener {

    private JTextField txtEventId, txtEventName, txtEventDate,
            txtEventTime, txtVenue, txtTicketPrice, txtTotalTickets;

    private JButton btnAdd, btnUpdate, btnDelete, btnClear,
            btnRefresh, btnBack;

    private JTable eventTable;
    private DefaultTableModel tableModel;

    private EventService eventService;

    public EventForm() {

        eventService = new EventService();

        setTitle("Event Management");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(235, 242, 250));
        setContentPane(mainPanel);

        // ================= WHITE CARD =================

        JPanel card = new RoundedPanel(35);
        card.setPreferredSize(new Dimension(1150, 700));
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout(15, 15));

        mainPanel.add(card);

        // ================= TITLE =================

        JLabel title = new JLabel(
                "EVENT MANAGEMENT",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(new Color(25, 55, 90));

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 10, 20
                )
        );

        card.add(title, BorderLayout.NORTH);

        // ================= FORM =================

        JPanel formPanel = new JPanel(
                new GridLayout(7, 2, 12, 12)
        );

        formPanel.setBackground(Color.WHITE);

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 25, 10, 25
                )
        );

        formPanel.add(label("Event ID:"));

        txtEventId = new JTextField();
        txtEventId.setEditable(false);
        styleTextField(txtEventId);
        formPanel.add(txtEventId);

        formPanel.add(label("Event Name:"));

        txtEventName = new JTextField();
        styleTextField(txtEventName);
        formPanel.add(txtEventName);

        formPanel.add(label("Event Date (YYYY-MM-DD):"));

        txtEventDate = new JTextField();
        styleTextField(txtEventDate);
        formPanel.add(txtEventDate);

        formPanel.add(label("Event Time (HH:MM:SS):"));

        txtEventTime = new JTextField();
        styleTextField(txtEventTime);
        formPanel.add(txtEventTime);

        formPanel.add(label("Venue:"));

        txtVenue = new JTextField();
        styleTextField(txtVenue);
        formPanel.add(txtVenue);

        formPanel.add(label("Ticket Price:"));

        txtTicketPrice = new JTextField();
        styleTextField(txtTicketPrice);
        formPanel.add(txtTicketPrice);

        formPanel.add(label("Total Tickets:"));

        txtTotalTickets = new JTextField();
        styleTextField(txtTotalTickets);
        formPanel.add(txtTotalTickets);

        card.add(formPanel, BorderLayout.WEST);

        // ================= TABLE =================

        String[] columns = {
                "Event ID",
                "Event Name",
                "Date",
                "Time",
                "Venue",
                "Ticket Price",
                "Total Tickets"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return false;
            }
        };

        eventTable = new JTable(tableModel);

        eventTable.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );

        eventTable.setRowHeight(30);

        eventTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        eventTable.getTableHeader().setBackground(
                new Color(45, 85, 130)
        );

        eventTable.getTableHeader().setForeground(
                Color.WHITE
        );

        eventTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        eventTable.setGridColor(
                new Color(210, 210, 210)
        );

        eventTable.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {

                        int row =
                                eventTable.getSelectedRow();

                        if (row != -1) {

                            txtEventId.setText(
                                    tableModel.getValueAt(
                                            row, 0
                                    ).toString()
                            );

                            txtEventName.setText(
                                    tableModel.getValueAt(
                                            row, 1
                                    ).toString()
                            );

                            txtEventDate.setText(
                                    tableModel.getValueAt(
                                            row, 2
                                    ).toString()
                            );

                            txtEventTime.setText(
                                    tableModel.getValueAt(
                                            row, 3
                                    ).toString()
                            );

                            txtVenue.setText(
                                    tableModel.getValueAt(
                                            row, 4
                                    ).toString()
                            );

                            txtTicketPrice.setText(
                                    tableModel.getValueAt(
                                            row, 5
                                    ).toString()
                            );

                            txtTotalTickets.setText(
                                    tableModel.getValueAt(
                                            row, 6
                                    ).toString()
                            );
                        }
                    }
                });

        JScrollPane scrollPane =
                new JScrollPane(eventTable);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(200, 210, 220)
                )
        );

        JPanel tablePanel =
                new JPanel(new BorderLayout());

        tablePanel.setBackground(Color.WHITE);

        tablePanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 20
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

        buttonPanel.setBackground(Color.WHITE);

        btnAdd = createButton(
                "Add Event",
                new Color(45, 130, 200)
        );

        btnUpdate = createButton(
                "Update",
                new Color(55, 160, 110)
        );

        btnDelete = createButton(
                "Delete",
                new Color(210, 70, 70)
        );

        btnClear = createButton(
                "Clear",
                new Color(120, 120, 120)
        );

        btnRefresh = createButton(
                "Refresh",
                new Color(125, 85, 175)
        );

        btnBack = createButton(
                "← Back",
                new Color(60, 60, 70)
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

        loadEvents();

        setVisible(true);
    }

    // ================= LABEL =================

    private JLabel label(String text) {

        JLabel l = new JLabel(text);

        l.setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        l.setForeground(
                new Color(60, 70, 80)
        );

        return l;
    }

    // ================= TEXT FIELD STYLE =================

    private void styleTextField(JTextField field) {

        field.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(180, 195, 210)
                        ),
                        BorderFactory.createEmptyBorder(
                                5, 8, 5, 8
                        )
                )
        );
    }

    // ================= BUTTON =================

    private JButton createButton(
            String text,
            Color background
    ) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        button.setBackground(background);

        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setPreferredSize(
                new Dimension(120, 40)
        );

        return button;
    }

    // ================= ADD =================

    private void addEvent() {

        if (!validateFields()) {
            return;
        }

        try {

            Event event = new Event();

            event.setEventName(
                    txtEventName.getText().trim()
            );

            event.setEventDate(
                    txtEventDate.getText().trim()
            );

            event.setEventTime(
                    txtEventTime.getText().trim()
            );

            event.setVenue(
                    txtVenue.getText().trim()
            );

            event.setTicketPrice(
                    Double.parseDouble(
                            txtTicketPrice.getText().trim()
                    )
            );

            event.setTotalTickets(
                    Integer.parseInt(
                            txtTotalTickets.getText().trim()
                    )
            );

            boolean result =
                    eventService.addEvent(event);

            if (result) {

                JOptionPane.showMessageDialog(
                        this,
                        "Event added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearFields();

                loadEvents();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to add event.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ticket price and total tickets must be valid numbers.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ================= UPDATE =================

    private void updateEvent() {

        if (txtEventId.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an event to update.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (!validateFields()) {
            return;
        }

        try {

            Event event = new Event();

            event.setEventId(
                    Integer.parseInt(
                            txtEventId.getText().trim()
                    )
            );

            event.setEventName(
                    txtEventName.getText().trim()
            );

            event.setEventDate(
                    txtEventDate.getText().trim()
            );

            event.setEventTime(
                    txtEventTime.getText().trim()
            );

            event.setVenue(
                    txtVenue.getText().trim()
            );

            event.setTicketPrice(
                    Double.parseDouble(
                            txtTicketPrice.getText().trim()
                    )
            );

            event.setTotalTickets(
                    Integer.parseInt(
                            txtTotalTickets.getText().trim()
                    )
            );

            boolean result =
                    eventService.updateEvent(event);

            if (result) {

                JOptionPane.showMessageDialog(
                        this,
                        "Event updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearFields();

                loadEvents();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to update event.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numbers.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ================= DELETE =================

    private void deleteEvent() {

        if (txtEventId.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an event to delete.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this event?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice == JOptionPane.YES_OPTION) {

            try {

                int eventId =
                        Integer.parseInt(
                                txtEventId.getText().trim()
                        );

                boolean result =
                        eventService.deleteEvent(eventId);

                if (result) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Event deleted successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    clearFields();

                    loadEvents();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Failed to delete event.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Event ID.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // ================= LOAD EVENTS =================

    private void loadEvents() {

        tableModel.setRowCount(0);

        List<Event> events =
                eventService.getAllEvents();

        for (Event event : events) {

            Object[] row = {

                    event.getEventId(),

                    event.getEventName(),

                    event.getEventDate(),

                    event.getEventTime(),

                    event.getVenue(),

                    event.getTicketPrice(),

                    event.getTotalTickets()
            };

            tableModel.addRow(row);
        }
    }

    // ================= CLEAR =================

    private void clearFields() {

        txtEventId.setText("");

        txtEventName.setText("");

        txtEventDate.setText("");

        txtEventTime.setText("");

        txtVenue.setText("");

        txtTicketPrice.setText("");

        txtTotalTickets.setText("");

        eventTable.clearSelection();
    }

    // ================= VALIDATION =================

    private boolean validateFields() {

        if (txtEventName.getText().trim().isEmpty()
                || txtEventDate.getText().trim().isEmpty()
                || txtEventTime.getText().trim().isEmpty()
                || txtVenue.getText().trim().isEmpty()
                || txtTicketPrice.getText().trim().isEmpty()
                || txtTotalTickets.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );

            return false;
        }

        try {

            double price =
                    Double.parseDouble(
                            txtTicketPrice.getText().trim()
                    );

            int tickets =
                    Integer.parseInt(
                            txtTotalTickets.getText().trim()
                    );

            Date.valueOf(
                    txtEventDate.getText().trim()
            );

            Time.valueOf(
                    txtEventTime.getText().trim()
            );

            if (price < 0 || tickets < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ticket price and total tickets cannot be negative.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );

                return false;
            }

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter date as YYYY-MM-DD and time as HH:MM:SS.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }

        return true;
    }

    // ================= BUTTON ACTIONS =================

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnAdd) {

            addEvent();

        } else if (e.getSource() == btnUpdate) {

            updateEvent();

        } else if (e.getSource() == btnDelete) {

            deleteEvent();

        } else if (e.getSource() == btnClear) {

            clearFields();

        } else if (e.getSource() == btnRefresh) {

            loadEvents();

        } else if (e.getSource() == btnBack) {

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
        }
    }

    // ================= ROUNDED CARD =================

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

            g2.setColor(getBackground());

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

    // ================= MAIN =================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> new EventForm()
        );
    }
}
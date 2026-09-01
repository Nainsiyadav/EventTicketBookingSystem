package gui;

import model.Event;
import service.EventService;
import java.sql.Date;
import java.sql.Time;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EventForm extends JFrame implements ActionListener {

    private JTextField txtEventId;
    private JTextField txtEventName;
    private JTextField txtEventDate;
    private JTextField txtEventTime;
    private JTextField txtVenue;
    private JTextField txtTicketPrice;
    private JTextField txtTotalTickets;

    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;
    private JButton btnRefresh;

    private JTable eventTable;
    private DefaultTableModel tableModel;

    private EventService eventService;

    public EventForm() {

        eventService = new EventService();

        setTitle("Event Management");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        createGUI();
        loadEvents();
    }

    private void createGUI() {

        setLayout(new BorderLayout(10, 10));

        // =========================
        // TITLE
        // =========================

        JLabel title = new JLabel("EVENT MANAGEMENT", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        add(title, BorderLayout.NORTH);

        // =========================
        // FORM PANEL
        // =========================

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        );

        formPanel.add(new JLabel("Event ID:"));
        txtEventId = new JTextField();
        txtEventId.setEditable(false);
        formPanel.add(txtEventId);

        formPanel.add(new JLabel("Event Name:"));
        txtEventName = new JTextField();
        formPanel.add(txtEventName);

        formPanel.add(new JLabel("Event Date (YYYY-MM-DD):"));
        txtEventDate = new JTextField();
        formPanel.add(txtEventDate);

        formPanel.add(new JLabel("Event Time (HH:MM:SS):"));
        txtEventTime = new JTextField();
        formPanel.add(txtEventTime);

        formPanel.add(new JLabel("Venue:"));
        txtVenue = new JTextField();
        formPanel.add(txtVenue);

        formPanel.add(new JLabel("Ticket Price:"));
        txtTicketPrice = new JTextField();
        formPanel.add(txtTicketPrice);

        formPanel.add(new JLabel("Total Tickets:"));
        txtTotalTickets = new JTextField();
        formPanel.add(txtTotalTickets);

        add(formPanel, BorderLayout.WEST);

        // =========================
        // BUTTON PANEL
        // =========================

        JPanel buttonPanel = new JPanel(new FlowLayout());

        btnAdd = new JButton("Add Event");
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
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        eventTable = new JTable(tableModel);

        eventTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        eventTable.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int row = eventTable.getSelectedRow();

                if (row != -1) {

                    txtEventId.setText(
                            tableModel.getValueAt(row, 0).toString()
                    );

                    txtEventName.setText(
                            tableModel.getValueAt(row, 1).toString()
                    );

                    txtEventDate.setText(
                            tableModel.getValueAt(row, 2).toString()
                    );

                    txtEventTime.setText(
                            tableModel.getValueAt(row, 3).toString()
                    );

                    txtVenue.setText(
                            tableModel.getValueAt(row, 4).toString()
                    );

                    txtTicketPrice.setText(
                            tableModel.getValueAt(row, 5).toString()
                    );

                    txtTotalTickets.setText(
                            tableModel.getValueAt(row, 6).toString()
                    );
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(eventTable);

        // =========================
        // CENTER PANEL
        // =========================

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        centerPanel.add(buttonPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }

    // =========================
    // ADD EVENT
    // =========================

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

            boolean result = eventService.addEvent(event);

            if (result) {

                JOptionPane.showMessageDialog(
                        this,
                        "Event added successfully!"
                );

                clearFields();
                loadEvents();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to add event."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ticket price and total tickets must be valid numbers."
            );
        }
    }

    // =========================
    // UPDATE EVENT
    // =========================

    private void updateEvent() {

        if (txtEventId.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an event to update."
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

            boolean result = eventService.updateEvent(event);

            if (result) {

                JOptionPane.showMessageDialog(
                        this,
                        "Event updated successfully!"
                );

                clearFields();
                loadEvents();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to update event."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numbers."
            );
        }
    }

    // =========================
    // DELETE EVENT
    // =========================

    private void deleteEvent() {

        if (txtEventId.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an event to delete."
            );

            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this event?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {

            try {

                int eventId = Integer.parseInt(
                        txtEventId.getText().trim()
                );

                boolean result =
                        eventService.deleteEvent(eventId);

                if (result) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Event deleted successfully!"
                    );

                    clearFields();
                    loadEvents();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Failed to delete event."
                    );
                }

            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Event ID."
                );
            }
        }
    }

    // =========================
    // LOAD EVENTS
    // =========================

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

    // =========================
    // CLEAR FIELDS
    // =========================

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

    // =========================
    // VALIDATION
    // =========================

    private boolean validateFields() {

        if (txtEventName.getText().trim().isEmpty()
                || txtEventDate.getText().trim().isEmpty()
                || txtEventTime.getText().trim().isEmpty()
                || txtVenue.getText().trim().isEmpty()
                || txtTicketPrice.getText().trim().isEmpty()
                || txtTotalTickets.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields."
            );

            return false;
        }

        try {

            double price = Double.parseDouble(
                    txtTicketPrice.getText().trim()
            );

            int tickets = Integer.parseInt(
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
                        "Ticket price and total tickets cannot be negative."
                );

                return false;
            }

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter date as YYYY-MM-DD and time as HH:MM:SS."
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

            addEvent();

        } else if (e.getSource() == btnUpdate) {

            updateEvent();

        } else if (e.getSource() == btnDelete) {

            deleteEvent();

        } else if (e.getSource() == btnClear) {

            clearFields();

        } else if (e.getSource() == btnRefresh) {

            loadEvents();
        }
    }

    // =========================
    // MAIN METHOD FOR TESTING
    // =========================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            EventForm form = new EventForm();
            form.setVisible(true);
        });
    }
}
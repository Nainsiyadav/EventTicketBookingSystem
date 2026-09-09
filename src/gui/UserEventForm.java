package gui;

import model.Event;
import service.EventService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserEventForm extends JFrame {

    private EventService eventService;
    private JTable eventTable;
    private DefaultTableModel tableModel;
    private int userId;

    public UserEventForm(int userId) {

        this.userId = userId;
        eventService = new EventService();

        setTitle("Available Events");
        setSize(950, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        createGUI();
        loadEvents();
    }

    // =========================
    // CREATE GUI
    // =========================

    private void createGUI() {

        setLayout(new BorderLayout(10, 10));

        // TITLE
        JLabel title = new JLabel(
                "AVAILABLE EVENTS",
                SwingConstants.CENTER
        );

        title.setFont(
                new Font("Arial", Font.BOLD, 26)
        );

        title.setForeground(
                new Color(25, 55, 90)
        );

        add(title, BorderLayout.NORTH);


        // TABLE COLUMNS
        String[] columns = {
                "Event ID",
                "Event Name",
                "Date",
                "Time",
                "Venue",
                "Ticket Price",
                "Available Tickets"
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

        eventTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        eventTable.setRowHeight(25);

        eventTable.getTableHeader().setBackground(
                new Color(45, 85, 130)
        );

        eventTable.getTableHeader().setForeground(
                Color.WHITE
        );

        eventTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 13)
        );


        JScrollPane scrollPane =
                new JScrollPane(eventTable);

        add(scrollPane, BorderLayout.CENTER);


        // BUTTON PANEL
        JPanel buttonPanel =
                new JPanel(new FlowLayout(
                        FlowLayout.CENTER,
                        15,
                        8
                ));

        JButton refreshButton =
                new JButton("Refresh");

        JButton bookButton =
                new JButton("Book Ticket");

        JButton backButton =
                new JButton("Back");


        styleButton(
                refreshButton,
                new Color(120, 80, 150)
        );

        styleButton(
                bookButton,
                new Color(55, 140, 100)
        );

        styleButton(
                backButton,
                new Color(190, 70, 70)
        );


        buttonPanel.add(refreshButton);
        buttonPanel.add(bookButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);


        // REFRESH
        refreshButton.addActionListener(e -> {

            loadEvents();

        });


        // BOOK TICKET
        bookButton.addActionListener(e -> {

            int row =
                    eventTable.getSelectedRow();

            if (row == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select an event first."
                );

                return;
            }


            int eventId =
                    Integer.parseInt(
                            tableModel
                                    .getValueAt(row, 0)
                                    .toString()
                    );


            new UserBookingForm(
                    userId,
                    eventId
            ).setVisible(true);

        });


        // BACK
        backButton.addActionListener(e -> {

            dispose();

        });
    }


    // =========================
    // BUTTON STYLE
    // =========================

    private void styleButton(
            JButton button,
            Color color
    ) {

        button.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        button.setBackground(color);
        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);
        button.setBorderPainted(false);
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

                    "₹" + event.getTicketPrice(),

                    event.getTotalTickets()
            };


            tableModel.addRow(row);
        }
    }


    // =========================
    // MAIN METHOD
    // =========================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            UserEventForm form =
                    new UserEventForm(1);

            form.setVisible(true);

        });
    }
}
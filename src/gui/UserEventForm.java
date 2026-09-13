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
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        createGUI();
        loadEvents();
        setVisible(true);
    }

    private void createGUI() {

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(242, 244, 247));
        setContentPane(mainPanel);

        JPanel card = new RoundedPanel(30);
        card.setPreferredSize(new Dimension(1150, 700));
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout(15, 15));
        card.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );

        mainPanel.add(card);

        // ================= TITLE =================

        JLabel title = new JLabel(
                "AVAILABLE EVENTS",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(25, 55, 90));

        card.add(title, BorderLayout.NORTH);

        // ================= TABLE =================

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
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        eventTable = new JTable(tableModel);

        eventTable.setFont(new Font("Arial", Font.PLAIN, 14));
        eventTable.setRowHeight(32);
        eventTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        eventTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        eventTable.getTableHeader().setBackground(
                new Color(45, 85, 130)
        );

        eventTable.getTableHeader().setForeground(Color.WHITE);

        eventTable.getTableHeader().setPreferredSize(
                new Dimension(0, 40)
        );

        JScrollPane scrollPane = new JScrollPane(eventTable);

        card.add(scrollPane, BorderLayout.CENTER);

        // ================= BUTTONS =================

        JPanel buttonPanel =
                new JPanel(new FlowLayout(
                        FlowLayout.CENTER, 20, 5
                ));

        buttonPanel.setOpaque(false);

        JButton refreshButton =
                new JButton("Refresh");

        JButton bookButton =
                new JButton("Book Ticket");

        JButton backButton =
                new JButton("← Back");

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
                new Color(90, 90, 90)
        );

        buttonPanel.add(refreshButton);
        buttonPanel.add(bookButton);
        buttonPanel.add(backButton);

        card.add(buttonPanel, BorderLayout.SOUTH);

        // ================= REFRESH =================

        refreshButton.addActionListener(e ->
                loadEvents()
        );

        // ================= BOOK TICKET =================

        bookButton.addActionListener(e -> {

            int row = eventTable.getSelectedRow();

            if (row == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select an event first.",
                        "Select Event",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            int eventId = Integer.parseInt(
                    tableModel.getValueAt(row, 0).toString()
            );

            new UserBookingForm(
                    userId,
                    eventId
            ).setVisible(true);

            dispose();
        });

        // ================= BACK =================

        backButton.addActionListener(e -> {

            new UserDashboard(userId).setVisible(true);

            dispose();
        });
    }

    // ================= BUTTON STYLE =================

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
        button.setPreferredSize(
                new Dimension(140, 42)
        );
    }

    // ================= LOAD EVENTS =================

    private void loadEvents() {

        tableModel.setRowCount(0);

        List<Event> events =
                eventService.getAllEvents();

        for (Event event : events) {

            tableModel.addRow(new Object[]{

                    event.getEventId(),
                    event.getEventName(),
                    event.getEventDate(),
                    event.getEventTime(),
                    event.getVenue(),
                    "₹" + event.getTicketPrice(),
                    event.getTotalTickets()
            });
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

        SwingUtilities.invokeLater(() ->
                new UserEventForm(1)
        );
    }
}
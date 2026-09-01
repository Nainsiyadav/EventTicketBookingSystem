package service;

import database.DBConnection;
import interfaces.EventOperations;
import model.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventService implements EventOperations {

    // CREATE
    @Override
    public boolean addEvent(Event event) {

        String sql = "INSERT INTO events " +
                "(event_name, event_date, event_time, venue, ticket_price, total_tickets) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, event.getEventName());
            ps.setDate(2, Date.valueOf(event.getEventDate()));
            ps.setTime(3, Time.valueOf(event.getEventTime()));
            ps.setString(4, event.getVenue());
            ps.setDouble(5, event.getTicketPrice());
            ps.setInt(6, event.getTotalTickets());

            return ps.executeUpdate() > 0;

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error adding event: " + e.getMessage());
            return false;
        }
    }

    // READ
    @Override
    public List<Event> getAllEvents() {

        List<Event> events = new ArrayList<>();

        String sql = "SELECT * FROM events";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Event event = new Event();

                event.setEventId(rs.getInt("event_id"));
                event.setEventName(rs.getString("event_name"));
                event.setEventDate(rs.getDate("event_date").toString());
                event.setEventTime(rs.getTime("event_time").toString());
                event.setVenue(rs.getString("venue"));
                event.setTicketPrice(rs.getDouble("ticket_price"));
                event.setTotalTickets(rs.getInt("total_tickets"));

                events.add(event);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching events: " + e.getMessage());
        }

        return events;
    }

    // UPDATE
    @Override
    public boolean updateEvent(Event event) {

        String sql = "UPDATE events SET " +
                "event_name = ?, event_date = ?, event_time = ?, " +
                "venue = ?, ticket_price = ?, total_tickets = ? " +
                "WHERE event_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, event.getEventName());
            ps.setDate(2, Date.valueOf(event.getEventDate()));
            ps.setTime(3, Time.valueOf(event.getEventTime()));
            ps.setString(4, event.getVenue());
            ps.setDouble(5, event.getTicketPrice());
            ps.setInt(6, event.getTotalTickets());
            ps.setInt(7, event.getEventId());

            return ps.executeUpdate() > 0;

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error updating event: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    @Override
    public boolean deleteEvent(int eventId) {

        String sql = "DELETE FROM events WHERE event_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, eventId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting event: " + e.getMessage());
            return false;
        }
    }
}
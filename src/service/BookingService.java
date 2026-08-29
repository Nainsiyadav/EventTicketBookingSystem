package service;

import database.DBConnection;
import interfaces.BookingOperations;
import model.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookingService implements BookingOperations {

    // CREATE
    @Override
    public void addBooking(Booking booking) {

        String sql = "INSERT INTO bookings(user_id, event_id, ticket_type_id, quantity, total_amount) VALUES (?, ?, ?, ?, ?)";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, booking.getUserId());
            ps.setInt(2, booking.getEventId());
            ps.setInt(3, booking.getTicketTypeId());
            ps.setInt(4, booking.getQuantity());
            ps.setDouble(5, booking.getTotalAmount());

            ps.executeUpdate();

            System.out.println("Booking added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // READ
    @Override
    public void viewBookings() {

        String sql = "SELECT * FROM bookings";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                System.out.println(
                    "Booking ID: " + rs.getInt("booking_id") +
                    " | User ID: " + rs.getInt("user_id") +
                    " | Event ID: " + rs.getInt("event_id") +
                    " | Ticket Type ID: " + rs.getInt("ticket_type_id") +
                    " | Quantity: " + rs.getInt("quantity") +
                    " | Total: " + rs.getDouble("total_amount")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    @Override
    public void updateBooking(Booking booking) {

        String sql = "UPDATE bookings SET user_id=?, event_id=?, ticket_type_id=?, quantity=?, total_amount=? WHERE booking_id=?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, booking.getUserId());
            ps.setInt(2, booking.getEventId());
            ps.setInt(3, booking.getTicketTypeId());
            ps.setInt(4, booking.getQuantity());
            ps.setDouble(5, booking.getTotalAmount());
            ps.setInt(6, booking.getBookingId());

            ps.executeUpdate();

            System.out.println("Booking updated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DELETE
    @Override
    public void deleteBooking(int bookingId) {

        String sql = "DELETE FROM bookings WHERE booking_id=?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, bookingId);

            ps.executeUpdate();

            System.out.println("Booking deleted successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
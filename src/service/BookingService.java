package service;

import database.DBConnection;
import interfaces.BookingOperations;
import model.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookingService implements BookingOperations {

    // =====================================================
    // ADD BOOKING
    // =====================================================

    @Override
    public String addBooking(Booking booking) {

        Connection con = null;

        try {

            con = DBConnection.getConnection();

            con.setAutoCommit(false);

            // ================= CHECK USER =================

            String userSql =
                    "SELECT user_id FROM users WHERE user_id=?";

            try (PreparedStatement ps =
                         con.prepareStatement(userSql)) {

                ps.setInt(1, booking.getUserId());

                try (ResultSet rs = ps.executeQuery()) {

                    if (!rs.next()) {

                        con.rollback();

                        return "User ID does not exist.";
                    }
                }
            }

            // ================= CHECK EVENT =================

            String eventSql =
                    "SELECT event_id FROM events WHERE event_id=?";

            try (PreparedStatement ps =
                         con.prepareStatement(eventSql)) {

                ps.setInt(1, booking.getEventId());

                try (ResultSet rs = ps.executeQuery()) {

                    if (!rs.next()) {

                        con.rollback();

                        return "Event ID does not exist.";
                    }
                }
            }

            // ================= CHECK TICKET =================

            String ticketSql =
                    "SELECT price, available_quantity, event_id " +
                    "FROM ticket_types WHERE ticket_type_id=?";

            double price;
            int availableQuantity;
            int ticketEventId;

            try (PreparedStatement ps =
                         con.prepareStatement(ticketSql)) {

                ps.setInt(1, booking.getTicketTypeId());

                try (ResultSet rs = ps.executeQuery()) {

                    if (!rs.next()) {

                        con.rollback();

                        return "Ticket Type ID does not exist.";
                    }

                    price =
                            rs.getDouble("price");

                    availableQuantity =
                            rs.getInt("available_quantity");

                    ticketEventId =
                            rs.getInt("event_id");
                }
            }

            // ================= CHECK EVENT/TICKET =================

            if (ticketEventId != booking.getEventId()) {

                con.rollback();

                return "Selected Ticket Type does not belong to the selected Event.";
            }

            // ================= CHECK QUANTITY =================

            if (booking.getQuantity() <= 0) {

                con.rollback();

                return "Quantity must be greater than 0.";
            }

            if (booking.getQuantity() > availableQuantity) {

                con.rollback();

                return "Only " + availableQuantity +
                        " tickets are available.";
            }

            // ================= CHECK AMOUNT =================

            double expectedAmount =
                    price * booking.getQuantity();

            if (Math.abs(
                    expectedAmount -
                    booking.getTotalAmount()
            ) > 0.01) {

                con.rollback();

                return String.format(
                        "Invalid Total Amount.\n" +
                        "Expected Amount: %.2f\n" +
                        "Entered Amount: %.2f",
                        expectedAmount,
                        booking.getTotalAmount()
                );
            }

            // ================= INSERT BOOKING =================

            String insertSql =
                    "INSERT INTO bookings " +
                    "(user_id, event_id, ticket_type_id, " +
                    "quantity, total_amount) " +
                    "VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement ps =
                         con.prepareStatement(insertSql)) {

                ps.setInt(
                        1,
                        booking.getUserId()
                );

                ps.setInt(
                        2,
                        booking.getEventId()
                );

                ps.setInt(
                        3,
                        booking.getTicketTypeId()
                );

                ps.setInt(
                        4,
                        booking.getQuantity()
                );

                ps.setDouble(
                        5,
                        booking.getTotalAmount()
                );

                int rows =
                        ps.executeUpdate();

                if (rows == 0) {

                    con.rollback();

                    return "Booking could not be added.";
                }
            }

            // ================= REDUCE TICKET =================

            String updateTicketSql =
                    "UPDATE ticket_types " +
                    "SET available_quantity = " +
                    "available_quantity - ? " +
                    "WHERE ticket_type_id=?";

            try (PreparedStatement ps =
                         con.prepareStatement(updateTicketSql)) {

                ps.setInt(
                        1,
                        booking.getQuantity()
                );

                ps.setInt(
                        2,
                        booking.getTicketTypeId()
                );

                ps.executeUpdate();
            }

            con.commit();

            return "Booking Added Successfully!";

        } catch (Exception e) {

            try {

                if (con != null) {

                    con.rollback();
                }

            } catch (Exception ignored) {
            }

            e.printStackTrace();

            return "Database error while adding booking.";

        } finally {

            try {

                if (con != null) {

                    con.close();
                }

            } catch (Exception ignored) {
            }
        }
    }

    // =====================================================
    // VIEW BOOKINGS - TERMINAL
    // =====================================================

    @Override
    public void viewBookings() {

        String sql =
                "SELECT * FROM bookings";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            System.out.println();

            System.out.println(
                    "========== BOOKINGS =========="
            );

            while (rs.next()) {

                System.out.println(
                        "Booking ID: " +
                        rs.getInt("booking_id") +

                        " | User ID: " +
                        rs.getInt("user_id") +

                        " | Event ID: " +
                        rs.getInt("event_id") +

                        " | Ticket Type ID: " +
                        rs.getInt("ticket_type_id") +

                        " | Quantity: " +
                        rs.getInt("quantity") +

                        " | Total: " +
                        rs.getDouble("total_amount")
                );
            }

            System.out.println(
                    "=============================="
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // =====================================================
    // NEW METHOD - GET BOOKINGS FOR GUI TABLE
    // =====================================================

    public Object[][] getBookingsForTable() {

        String sql =
                "SELECT booking_id, user_id, event_id, " +
                "ticket_type_id, quantity, total_amount " +
                "FROM bookings " +
                "ORDER BY booking_id";

        java.util.ArrayList<Object[]> rows =
                new java.util.ArrayList<>();

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                Object[] row = {

                    rs.getInt("booking_id"),

                    rs.getInt("user_id"),

                    rs.getInt("event_id"),

                    rs.getInt("ticket_type_id"),

                    rs.getInt("quantity"),

                    rs.getDouble("total_amount")
                };

                rows.add(row);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        Object[][] data =
                new Object[rows.size()][6];

        for (int i = 0; i < rows.size(); i++) {

            data[i] = rows.get(i);
        }

        return data;
    }

    // =====================================================
    // UPDATE BOOKING
    // =====================================================

    @Override
    public String updateBooking(
            Booking booking,
            String password) {

        Connection con = null;

        try {

            con = DBConnection.getConnection();

            con.setAutoCommit(false);

            // ================= OLD BOOKING =================

            String oldSql =
                    "SELECT user_id, event_id, " +
                    "ticket_type_id, quantity " +
                    "FROM bookings WHERE booking_id=?";

            int oldUserId;
            int oldEventId;
            int oldTicketTypeId;
            int oldQuantity;

            try (PreparedStatement ps =
                         con.prepareStatement(oldSql)) {

                ps.setInt(
                        1,
                        booking.getBookingId()
                );

                try (ResultSet rs =
                             ps.executeQuery()) {

                    if (!rs.next()) {

                        con.rollback();

                        return "Booking ID does not exist.";
                    }

                    oldUserId =
                            rs.getInt("user_id");

                    oldEventId =
                            rs.getInt("event_id");

                    oldTicketTypeId =
                            rs.getInt("ticket_type_id");

                    oldQuantity =
                            rs.getInt("quantity");
                }
            }

            // ================= PASSWORD =================

            String passwordSql =
                    "SELECT password FROM users " +
                    "WHERE user_id=?";

            try (PreparedStatement ps =
                         con.prepareStatement(passwordSql)) {

                ps.setInt(
                        1,
                        oldUserId
                );

                try (ResultSet rs =
                             ps.executeQuery()) {

                    if (!rs.next()) {

                        con.rollback();

                        return "User associated with this booking was not found.";
                    }

                    String actualPassword =
                            rs.getString("password");

                    if (!actualPassword.equals(password)) {

                        con.rollback();

                        return "Incorrect password. Booking was not updated.";
                    }
                }
            }

            // ================= CHECK USER =================

            String userSql =
                    "SELECT user_id FROM users " +
                    "WHERE user_id=?";

            try (PreparedStatement ps =
                         con.prepareStatement(userSql)) {

                ps.setInt(
                        1,
                        booking.getUserId()
                );

                try (ResultSet rs =
                             ps.executeQuery()) {

                    if (!rs.next()) {

                        con.rollback();

                        return "User ID does not exist.";
                    }
                }
            }

            // ================= CHECK EVENT =================

            String eventSql =
                    "SELECT event_id FROM events " +
                    "WHERE event_id=?";

            try (PreparedStatement ps =
                         con.prepareStatement(eventSql)) {

                ps.setInt(
                        1,
                        booking.getEventId()
                );

                try (ResultSet rs =
                             ps.executeQuery()) {

                    if (!rs.next()) {

                        con.rollback();

                        return "Event ID does not exist.";
                    }
                }
            }

            // ================= QUANTITY =================

            if (booking.getQuantity() <= 0) {

                con.rollback();

                return "Quantity must be greater than 0.";
            }

            // ================= TICKET =================

            String ticketSql =
                    "SELECT price, available_quantity, event_id " +
                    "FROM ticket_types " +
                    "WHERE ticket_type_id=?";

            double price;
            int availableQuantity;
            int ticketEventId;

            try (PreparedStatement ps =
                         con.prepareStatement(ticketSql)) {

                ps.setInt(
                        1,
                        booking.getTicketTypeId()
                );

                try (ResultSet rs =
                             ps.executeQuery()) {

                    if (!rs.next()) {

                        con.rollback();

                        return "Ticket Type ID does not exist.";
                    }

                    price =
                            rs.getDouble("price");

                    availableQuantity =
                            rs.getInt("available_quantity");

                    ticketEventId =
                            rs.getInt("event_id");
                }
            }

            if (ticketEventId != booking.getEventId()) {

                con.rollback();

                return "Selected Ticket Type does not belong to the selected Event.";
            }

            if (oldTicketTypeId ==
                    booking.getTicketTypeId()) {

                availableQuantity += oldQuantity;
            }

            if (booking.getQuantity() >
                    availableQuantity) {

                con.rollback();

                return "Only " +
                        availableQuantity +
                        " tickets are available.";
            }

            // ================= AMOUNT =================

            double expectedAmount =
                    price * booking.getQuantity();

            if (Math.abs(
                    expectedAmount -
                    booking.getTotalAmount()
            ) > 0.01) {

                con.rollback();

                return String.format(
                        "Invalid Total Amount.\n" +
                        "Expected Amount: %.2f\n" +
                        "Entered Amount: %.2f",
                        expectedAmount,
                        booking.getTotalAmount()
                );
            }

            // ================= RESTORE OLD TICKETS =================

            String restoreSql =
                    "UPDATE ticket_types " +
                    "SET available_quantity = " +
                    "available_quantity + ? " +
                    "WHERE ticket_type_id=?";

            try (PreparedStatement ps =
                         con.prepareStatement(restoreSql)) {

                ps.setInt(
                        1,
                        oldQuantity
                );

                ps.setInt(
                        2,
                        oldTicketTypeId
                );

                ps.executeUpdate();
            }

            // ================= REDUCE NEW TICKETS =================

            String reduceSql =
                    "UPDATE ticket_types " +
                    "SET available_quantity = " +
                    "available_quantity - ? " +
                    "WHERE ticket_type_id=?";

            try (PreparedStatement ps =
                         con.prepareStatement(reduceSql)) {

                ps.setInt(
                        1,
                        booking.getQuantity()
                );

                ps.setInt(
                        2,
                        booking.getTicketTypeId()
                );

                ps.executeUpdate();
            }

            // ================= UPDATE BOOKING =================

            String updateSql =
                    "UPDATE bookings SET " +
                    "user_id=?, event_id=?, " +
                    "ticket_type_id=?, quantity=?, " +
                    "total_amount=? " +
                    "WHERE booking_id=?";

            try (PreparedStatement ps =
                         con.prepareStatement(updateSql)) {

                ps.setInt(
                        1,
                        booking.getUserId()
                );

                ps.setInt(
                        2,
                        booking.getEventId()
                );

                ps.setInt(
                        3,
                        booking.getTicketTypeId()
                );

                ps.setInt(
                        4,
                        booking.getQuantity()
                );

                ps.setDouble(
                        5,
                        booking.getTotalAmount()
                );

                ps.setInt(
                        6,
                        booking.getBookingId()
                );

                int rows =
                        ps.executeUpdate();

                if (rows == 0) {

                    con.rollback();

                    return "Booking could not be updated.";
                }
            }

            con.commit();

            return "Booking Updated Successfully!";

        } catch (Exception e) {

            try {

                if (con != null) {

                    con.rollback();
                }

            } catch (Exception ignored) {
            }

            e.printStackTrace();

            return "Database error while updating booking.";

        } finally {

            try {

                if (con != null) {

                    con.close();
                }

            } catch (Exception ignored) {
            }
        }
    }

    // =====================================================
    // DELETE BOOKING
    // =====================================================

    @Override
    public String deleteBooking(
            int bookingId,
            String password) {

        Connection con = null;

        try {

            con = DBConnection.getConnection();

            con.setAutoCommit(false);

            // ================= FIND BOOKING =================

            String findSql =
                    "SELECT user_id, ticket_type_id, quantity " +
                    "FROM bookings WHERE booking_id=?";

            int userId;
            int ticketTypeId;
            int quantity;

            try (PreparedStatement ps =
                         con.prepareStatement(findSql)) {

                ps.setInt(
                        1,
                        bookingId
                );

                try (ResultSet rs =
                             ps.executeQuery()) {

                    if (!rs.next()) {

                        con.rollback();

                        return "Booking ID does not exist.";
                    }

                    userId =
                            rs.getInt("user_id");

                    ticketTypeId =
                            rs.getInt("ticket_type_id");

                    quantity =
                            rs.getInt("quantity");
                }
            }

            // ================= PASSWORD =================

            String passwordSql =
                    "SELECT password FROM users " +
                    "WHERE user_id=?";

            try (PreparedStatement ps =
                         con.prepareStatement(passwordSql)) {

                ps.setInt(
                        1,
                        userId
                );

                try (ResultSet rs =
                             ps.executeQuery()) {

                    if (!rs.next()) {

                        con.rollback();

                        return "User associated with this booking was not found.";
                    }

                    String actualPassword =
                            rs.getString("password");

                    if (!actualPassword.equals(password)) {

                        con.rollback();

                        return "Incorrect password. Booking was not deleted.";
                    }
                }
            }

            // ================= DELETE =================

            String deleteSql =
                    "DELETE FROM bookings " +
                    "WHERE booking_id=?";

            try (PreparedStatement ps =
                         con.prepareStatement(deleteSql)) {

                ps.setInt(
                        1,
                        bookingId
                );

                int rows =
                        ps.executeUpdate();

                if (rows == 0) {

                    con.rollback();

                    return "Booking could not be deleted.";
                }
            }

            // ================= RESTORE TICKETS =================

            String restoreSql =
                    "UPDATE ticket_types " +
                    "SET available_quantity = " +
                    "available_quantity + ? " +
                    "WHERE ticket_type_id=?";

            try (PreparedStatement ps =
                         con.prepareStatement(restoreSql)) {

                ps.setInt(
                        1,
                        quantity
                );

                ps.setInt(
                        2,
                        ticketTypeId
                );

                ps.executeUpdate();
            }

            con.commit();

            return "Booking Deleted Successfully!";

        } catch (Exception e) {

            try {

                if (con != null) {

                    con.rollback();
                }

            } catch (Exception ignored) {
            }

            e.printStackTrace();

            return "Database error while deleting booking.";

        } finally {

            try {

                if (con != null) {

                    con.close();
                }

            } catch (Exception ignored) {
            }
        }
    }
}
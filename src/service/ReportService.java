package service;

import database.DBConnection;
import interfaces.ReportOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportService implements ReportOperations {

    // 1. Total Bookings
    @Override
    public int getTotalBookings() {

        String sql = "SELECT COUNT(*) FROM bookings";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Error getting total bookings: "
                    + e.getMessage());
        }

        return 0;
    }


    // 2. Total Revenue
    @Override
    public double getTotalRevenue() {

        String sql = "SELECT COALESCE(SUM(total_amount), 0) FROM bookings";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble(1);
            }

        } catch (SQLException e) {
            System.out.println("Error getting total revenue: "
                    + e.getMessage());
        }

        return 0.0;
    }


    // 3. Event-wise Revenue
    @Override
    public List<Map<String, Object>> getEventWiseRevenue() {

        List<Map<String, Object>> report = new ArrayList<>();

        String sql = "SELECT e.event_name, "
                   + "SUM(b.quantity) AS tickets_sold, "
                   + "SUM(b.total_amount) AS revenue "
                   + "FROM events e "
                   + "JOIN bookings b ON e.event_id = b.event_id "
                   + "GROUP BY e.event_id, e.event_name "
                   + "ORDER BY revenue DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Map<String, Object> row = new HashMap<>();

                row.put("event_name", rs.getString("event_name"));
                row.put("tickets_sold", rs.getInt("tickets_sold"));
                row.put("revenue", rs.getDouble("revenue"));

                report.add(row);
            }

        } catch (SQLException e) {
            System.out.println("Error getting event-wise revenue: "
                    + e.getMessage());
        }

        return report;
    }


    // 4. Popular Event
    @Override
    public String getPopularEvent() {

        String sql = "SELECT e.event_name "
                   + "FROM events e "
                   + "JOIN bookings b ON e.event_id = b.event_id "
                   + "GROUP BY e.event_id, e.event_name "
                   + "ORDER BY SUM(b.quantity) DESC "
                   + "LIMIT 1";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getString("event_name");
            }

        } catch (SQLException e) {
            System.out.println("Error getting popular event: "
                    + e.getMessage());
        }

        return "No bookings available";
    }


    // 5. Payment Status Summary
    @Override
    public List<Map<String, Object>> getPaymentStatusSummary() {

        List<Map<String, Object>> report = new ArrayList<>();

        String sql = "SELECT payment_status, COUNT(*) AS total "
                   + "FROM payments "
                   + "GROUP BY payment_status";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Map<String, Object> row = new HashMap<>();

                row.put("payment_status",
                        rs.getString("payment_status"));

                row.put("total",
                        rs.getInt("total"));

                report.add(row);
            }

        } catch (SQLException e) {
            System.out.println("Error getting payment status summary: "
                    + e.getMessage());
        }

        return report;
    }
}
package service;

import database.DBConnection;
import interfaces.PaymentOperations;
import model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PaymentService implements PaymentOperations {

    // CREATE - Add Payment
    @Override
    public boolean addPayment(Payment payment) {

        String sql = "INSERT INTO payments " +
                "(booking_id, amount, payment_method, payment_status, payment_date) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, payment.getBookingId());
            ps.setDouble(2, payment.getAmount());
            ps.setString(3, payment.getPaymentMethod());
            ps.setString(4, payment.getPaymentStatus());
            ps.setString(5, payment.getPaymentDate());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error adding payment: " + e.getMessage());
            return false;
        }
    }

    // READ - Get All Payments
    @Override
    public List<Payment> getAllPayments() {

        List<Payment> payments = new ArrayList<>();

        String sql = "SELECT * FROM payments";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Payment payment = new Payment();

                payment.setPaymentId(rs.getInt("payment_id"));
                payment.setBookingId(rs.getInt("booking_id"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setPaymentMethod(
                        rs.getString("payment_method")
                );
                payment.setPaymentStatus(
                        rs.getString("payment_status")
                );
                payment.setPaymentDate(
                        rs.getString("payment_date")
                );

                payments.add(payment);
            }

        } catch (Exception e) {
            System.out.println("Error fetching payments: " + e.getMessage());
        }

        return payments;
    }

    // UPDATE - Update Payment
    @Override
    public boolean updatePayment(Payment payment) {

        String sql = "UPDATE payments SET " +
                "booking_id = ?, amount = ?, payment_method = ?, " +
                "payment_status = ?, payment_date = ? " +
                "WHERE payment_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, payment.getBookingId());
            ps.setDouble(2, payment.getAmount());
            ps.setString(3, payment.getPaymentMethod());
            ps.setString(4, payment.getPaymentStatus());
            ps.setString(5, payment.getPaymentDate());
            ps.setInt(6, payment.getPaymentId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error updating payment: " + e.getMessage());
            return false;
        }
    }

    // DELETE - Delete Payment
    @Override
    public boolean deletePayment(int paymentId) {

        String sql = "DELETE FROM payments WHERE payment_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, paymentId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error deleting payment: " + e.getMessage());
            return false;
        }
    }
}
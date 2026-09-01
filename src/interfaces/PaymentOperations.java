package interfaces;

import model.Payment;
import java.util.List;

public interface PaymentOperations {

    // Create
    boolean addPayment(Payment payment);

    // Read
    List<Payment> getAllPayments();

    // Update
    boolean updatePayment(Payment payment);

    // Delete
    boolean deletePayment(int paymentId);
}

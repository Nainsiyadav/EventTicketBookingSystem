package main;

import model.Payment;
import service.PaymentService;

public class TestPayment {

    public static void main(String[] args) {

        PaymentService paymentService = new PaymentService();

        Payment payment = new Payment();

        payment.setBookingId(8);
        payment.setPaymentMethod("UPI");
        payment.setPaymentStatus("PAID");

        boolean result = paymentService.addPayment(payment);

        if (result) {
            System.out.println("Payment Added Successfully!");
        } else {
            System.out.println("Payment Addition Failed!");
        }
    }
}

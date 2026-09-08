package main;

import model.Booking;
import service.BookingService;

public class TestBooking {

    public static void main(String[] args) {

        BookingService service = new BookingService();

        // ================= ADD BOOKING TEST =================

        Booking booking = new Booking(
        1,      // User ID
        1,      // Event ID
        1,      // Ticket Type ID
        2,      // Quantity
        1000.0  // Total Amount
        );

        String result = service.addBooking(booking);

        System.out.println(result);


        // ================= VIEW BOOKINGS =================

        service.viewBookings();


        // ================= UPDATE TEST =================
        // Agar update test karna ho to Booking ID set karo

        /*
        booking.setBookingId(1);

        booking.setQuantity(1);
        booking.setTotalAmount(500.0);

        String updateResult =
                service.updateBooking(booking, "123456");

        System.out.println(updateResult);
        */


        // ================= DELETE TEST =================
        // Agar delete test karna ho:

        /*
        String deleteResult =
                service.deleteBooking(1, "123456");

        System.out.println(deleteResult);
        */
    }
}
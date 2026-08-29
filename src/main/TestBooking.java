package main;

import model.Booking;
import service.BookingService;

public class TestBooking {

    public static void main(String[] args) {

        // Create Booking object
        Booking booking = new Booking(
                1,      // user_id
                1,      // event_id
                1,      // ticket_type_id
                2,      // quantity
                1000.0  // total_amount
        );

        // Create BookingService object
        BookingService service = new BookingService();

        // Add booking
       // service.addBooking(booking);

        // View all bookings
        service.viewBookings();

        booking.setBookingId(2);
        booking.setQuantity(3);
        booking.setTotalAmount(1500.0);

        service.updateBooking(booking);

        service.viewBookings();

        service.deleteBooking(2);

        service.viewBookings();

        service.deleteBooking(2);

        service.viewBookings();
    }
}
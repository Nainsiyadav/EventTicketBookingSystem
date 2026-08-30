package interfaces;

import model.Booking;

public interface BookingOperations {

    String addBooking(Booking booking);

    void viewBookings();

    String updateBooking(Booking booking, String password);

    String deleteBooking(int bookingId, String password);
}
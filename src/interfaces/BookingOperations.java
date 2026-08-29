package interfaces;

import model.Booking;

public interface BookingOperations {

    void addBooking(Booking booking);

    void viewBookings();

    void updateBooking(Booking booking);

    void deleteBooking(int bookingId);
}

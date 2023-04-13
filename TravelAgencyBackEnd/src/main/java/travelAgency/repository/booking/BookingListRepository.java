package travelAgency.repository.booking;

import travelAgency.domain.booking.Reservation;

import java.util.List;
import java.util.Optional;

public interface BookingListRepository {
    void book(Reservation reservation);
    void cancel(String ticketNumber);
    Optional<Reservation> findBooking(String ticketNumber);
    Optional<Reservation> findBookingByFlightNumber(String flightNumber);
    List<Reservation> getAllBookings();
    void truncate();
}

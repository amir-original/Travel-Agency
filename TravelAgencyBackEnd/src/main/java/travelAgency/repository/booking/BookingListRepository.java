package travelAgency.repository.booking;

import travelAgency.domain.booking.FlightTicket;

import java.util.List;
import java.util.Optional;

public interface BookingListRepository {
    void book(FlightTicket flightTicket);
    void cancel(String ticketNumber);
    Optional<FlightTicket> findBooking(String ticketNumber);
    List<FlightTicket> getAllBookings();
    void truncate();
}

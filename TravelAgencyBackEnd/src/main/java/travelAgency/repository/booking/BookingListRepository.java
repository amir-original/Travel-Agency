package travelAgency.repository.booking;

import travelAgency.domain.booking.FlightTicket;

import java.util.List;
import java.util.Optional;

public interface BookingListRepository {
    void book(FlightTicket flightTicket);
    void cancel(FlightTicket flightTicket);
    Optional<FlightTicket> findBooking(String ticketNumber);
    List<FlightTicket> findBookings(String flightNumber);
    List<FlightTicket> getAllBookings();
    void truncate();
}

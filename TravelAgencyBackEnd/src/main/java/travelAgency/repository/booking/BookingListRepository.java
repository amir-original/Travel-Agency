package travelAgency.repository.booking;

import travelAgency.domain.booking.FlightTicket;

import java.util.List;
import java.util.Optional;

public interface BookingListRepository {
    void book(FlightTicket flightTicket);
    Optional<FlightTicket> ticket(String ticketNumber);
    List<FlightTicket> tickets();
    void remove(FlightTicket flightTicket);
    int numberOfBookedFlight(String flightNumber);
    int numberOfSeatsAvailable(String flightNumber);

    void truncate();
}

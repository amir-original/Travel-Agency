package travelAgency.repository.booking;

import travelAgency.domain.FlightTicket;

import java.util.List;
import java.util.Optional;

public interface BookingFlightRepository {

    int book(FlightTicket flightTicket);
    Optional<FlightTicket> ticket(String ticketNumber);

    List<FlightTicket> tickets();

    void truncate();


}

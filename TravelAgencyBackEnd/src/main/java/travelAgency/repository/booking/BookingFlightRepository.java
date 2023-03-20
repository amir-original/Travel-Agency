package travelAgency.repository.booking;

import travelAgency.domain.FlightTicket;
import travelAgency.domain.FlightTicketInfo;

public interface BookingFlightRepository {
    void book(FlightTicket flightTicket);
    void truncate();
}

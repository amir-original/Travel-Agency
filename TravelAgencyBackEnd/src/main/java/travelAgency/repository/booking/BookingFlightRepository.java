package travelAgency.repository.booking;

import travelAgency.domain.booking.FlightTicket;

public interface BookingFlightRepository {
    void book(FlightTicket flightTicket);
    void truncate();
}

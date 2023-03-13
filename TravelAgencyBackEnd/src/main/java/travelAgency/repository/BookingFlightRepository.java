package travelAgency.repository;

import travelAgency.domain.FlightTicket;

public interface BookingFlightRepository {

    void book(FlightTicket bookingInformation);
}

package travelAgency.services.booking;

import travelAgency.domain.booking.FlightTicket;
import travelAgency.domain.booking.FlightTicketInfo;

public interface BookingFlightService {
    FlightTicket book(FlightTicketInfo flightTicketInfo);
}

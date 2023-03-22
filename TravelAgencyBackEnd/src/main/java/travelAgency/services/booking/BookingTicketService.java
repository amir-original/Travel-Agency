package travelAgency.services.booking;

import travelAgency.domain.booking.FlightTicket;
import travelAgency.domain.booking.FlightTicketInfo;

public interface BookingTicketService {

    FlightTicket book(FlightTicketInfo flightTicketInfo);
}

package travelAgency.services.booking;

import travelAgency.domain.FlightTicket;
import travelAgency.domain.FlightTicketInfo;

public interface BookingTicketService {

    FlightTicket book(FlightTicketInfo flightTicketInfo);
}

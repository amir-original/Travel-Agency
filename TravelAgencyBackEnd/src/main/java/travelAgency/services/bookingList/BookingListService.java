package travelAgency.services.bookingList;

import travelAgency.domain.booking.FlightTicket;

import java.util.List;
import java.util.Optional;

public interface BookingListService {
    List<FlightTicket> getAllTickets();
    Optional<FlightTicket> getTicket(String ticketNumber);
}

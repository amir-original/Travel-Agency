package travelAgency.services.bookingList;

import travelAgency.domain.FlightTicket;

import java.util.List;

public interface BookingListService {
    List<FlightTicket> getAllTickets();
}

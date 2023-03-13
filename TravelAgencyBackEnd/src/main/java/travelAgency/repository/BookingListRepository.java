package travelAgency.repository;

import travelAgency.domain.FlightTicket;

import java.util.List;

public interface BookingListRepository {

    List<FlightTicket> getAllTickets();
}

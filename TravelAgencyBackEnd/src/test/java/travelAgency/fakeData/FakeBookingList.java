package travelAgency.fakeData;

import travelAgency.domain.FlightTicket;
import travelAgency.repository.BookingListRepository;

import java.util.List;

import static travelAgency.fakeData.FakeFlightTicketBuilder.flightTicket;

public class FakeBookingList implements BookingListRepository {

    @Override
    public List<FlightTicket> getAllTickets() {
        return List.of(flightTicket().build());
    }
}

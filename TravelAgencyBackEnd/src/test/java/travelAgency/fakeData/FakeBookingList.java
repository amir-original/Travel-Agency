package travelAgency.fakeData;

import travelAgency.domain.FlightTicket;
import travelAgency.repository.booking.BookingListRepository;

import java.util.List;
import java.util.Optional;

import static travelAgency.fakeData.FakeFlightTicketBuilder.flightTicket;

public class FakeBookingList implements BookingListRepository {

    @Override
    public Optional<FlightTicket> ticket(String ticketNumber) {
        return Optional.empty();
    }

    @Override
    public List<FlightTicket> tickets() {
        return List.of(flightTicket().build());
    }
}

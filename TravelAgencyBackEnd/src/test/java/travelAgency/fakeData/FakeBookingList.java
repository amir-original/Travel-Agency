package travelAgency.fakeData;

import travelAgency.domain.booking.FlightTicket;
import travelAgency.repository.booking.BookingListRepository;

import java.util.List;
import java.util.Optional;

import static travelAgency.fakeData.FakeFlightTicketInfoBuilder.flightTicketInfo;

public class FakeBookingList implements BookingListRepository {

    @Override
    public Optional<FlightTicket> ticket(String ticketNumber) {
        return Optional.empty();
    }

    @Override
    public List<FlightTicket> tickets() {
        return List.of(
                new FlightTicket("78456587", flightTicketInfo().build()),
                new FlightTicket("84146521", flightTicketInfo().build()),
                new FlightTicket("64125521", flightTicketInfo().build())
        );
    }

    @Override
    public void remove(FlightTicket flightTicket) {

    }
}

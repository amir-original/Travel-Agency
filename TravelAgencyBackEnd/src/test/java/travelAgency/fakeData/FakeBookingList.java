package travelAgency.fakeData;

import travelAgency.domain.booking.FlightTicket;
import travelAgency.repository.booking.BookingListRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static travelAgency.fakeData.FakeFlightTicketInfoBuilder.flightTicketInfo;

public class FakeBookingList implements BookingListRepository {

    private final List<FlightTicket> tickets;

    {
        tickets = new LinkedList<>( List.of(
                new FlightTicket("78456587", flightTicketInfo().build()),
                new FlightTicket("84146521", flightTicketInfo().build()),
                new FlightTicket("64125521", flightTicketInfo().build())
        ));
    }

    @Override
    public void book(FlightTicket flightTicket) {
        tickets.add(flightTicket);
    }

    @Override
    public Optional<FlightTicket> ticket(String ticketNumber) {
        return tickets.stream()
                .filter(ticket -> ticket.isEqualTicketNumber(ticketNumber))
                .findFirst();
    }

    @Override
    public List<FlightTicket> tickets() {
        return tickets;
    }

    @Override
    public void remove(FlightTicket flightTicket) {
        tickets.remove(flightTicket);
    }

    @Override
    public int numberOfBookedFlight(String flightNumber) {
        return tickets.stream().mapToInt(FlightTicket::numberOfTickets).sum();
    }

    @Override
    public int numberOfSeatsAvailable(String flightNumber) {
        return 0;
    }

    @Override
    public void truncate() {
        this.tickets.clear();
    }
}

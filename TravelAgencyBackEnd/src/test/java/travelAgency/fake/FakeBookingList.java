package travelAgency.fake;

import travelAgency.domain.booking.FlightTicket;
import travelAgency.repository.booking.BookingListRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static travelAgency.fake.FakeBookingInformationBuilder.bookingInformation;

public class FakeBookingList implements BookingListRepository {

    private final List<FlightTicket> tickets = new LinkedList<>();

    {
        tickets.addAll(List.of(
                new FlightTicket("78456587", bookingInformation().build()),
                new FlightTicket("84146521", bookingInformation().build()),
                new FlightTicket("64125521", bookingInformation().build())
        ));
    }

    @Override
    public void book(FlightTicket flightTicket) {
        tickets.add(flightTicket);
    }

    @Override
    public Optional<FlightTicket> findBooking(String ticketNumber) {
        return tickets.stream()
                .filter(ticket -> ticket.isEqualTicketNumber(ticketNumber))
                .findFirst();
    }

    @Override
    public List<FlightTicket> findBookings(String flightNumber) {
        return tickets.stream()
                .filter(flightTicket -> flightTicket.flightNumber().equals(flightNumber))
                .toList();
    }

    @Override
    public List<FlightTicket> getAllBookings() {
        return tickets;
    }

    @Override
    public void cancel(FlightTicket flightTicket) {
        tickets.remove(flightTicket);
    }

    @Override
    public void truncate() {
        this.tickets.clear();
    }
}

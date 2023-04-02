package travelAgency.fake;

import travelAgency.domain.booking.FlightTicket;
import travelAgency.domain.exceptions.NotFoundAnyBookingFlightException;
import travelAgency.repository.booking.BookingListRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static travelAgency.fake.FakeBookingInformationBuilder.bookingInformation;

public class FakeBookingList implements BookingListRepository {

    private final List<FlightTicket> bookings = new LinkedList<>();

    {
        bookings.addAll(List.of(
                new FlightTicket("78456587", bookingInformation().build()),
                new FlightTicket("84146521", bookingInformation().build()),
                new FlightTicket("64125521", bookingInformation().build())
        ));
    }

    @Override
    public void book(FlightTicket flightTicket) {
        bookings.add(flightTicket);
    }

    @Override
    public Optional<FlightTicket> findBooking(String ticketNumber) {
        return bookings.stream()
                .filter(ticket -> ticket.isEqualTicketNumber(ticketNumber))
                .findFirst();
    }

    @Override
    public List<FlightTicket> findBookings(String flightNumber) {
        return bookings.stream()
                .filter(flightTicket -> flightTicket.flightNumber().equals(flightNumber))
                .toList();
    }

    @Override
    public List<FlightTicket> getAllBookings() {
        return bookings;
    }

    @Override
    public void cancel(FlightTicket flightTicket) {
        bookings.remove(flightTicket);
    }

    @Override
    public void truncate() {
        this.bookings.clear();
    }

    public static FlightTicket flightTicket(String ticketNumber){
        return new FakeBookingList()
                .findBooking(ticketNumber)
                .orElseThrow(NotFoundAnyBookingFlightException::new);
    }
}

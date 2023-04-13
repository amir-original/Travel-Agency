package travelAgency.use_case.fake;

import travelAgency.domain.booking.Reservation;
import travelAgency.domain.exceptions.NotFoundAnyBookingFlightException;
import travelAgency.repository.booking.BookingListRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static travelAgency.use_case.fake.FakeBookingInformationBuilder.bookingInformation;

public class FakeBookingList implements BookingListRepository {

    private final List<Reservation> bookings = new LinkedList<>();

    {
        bookings.addAll(List.of(
                new Reservation("78456587", bookingInformation().build()),
                new Reservation("84146521", bookingInformation().build()),
                new Reservation("64125521", bookingInformation().build())
        ));
    }

    @Override
    public void book(Reservation reservation) {
        bookings.add(reservation);
    }

    @Override
    public Optional<Reservation> findBooking(String ticketNumber) {
        return bookings.stream()
                .filter(ticket -> ticket.isEqualTicketNumber(ticketNumber))
                .findFirst();
    }

    @Override
    public Optional<Reservation> findBookingByFlightNumber(String flightNumber) {
        return bookings
                .stream()
                .filter(reservation -> reservation.canMatchWith(flightNumber))
                .findFirst();
    }

    @Override
    public List<Reservation> getAllBookings() {
        return bookings;
    }

    @Override
    public void cancel(String ticketNumber) {
        bookings.stream()
                .filter(flightTicket -> flightTicket.ticketNumber().equals(ticketNumber))
                .findFirst()
                .ifPresent(bookings::remove);
    }

    @Override
    public void truncate() {
        this.bookings.clear();
    }

    public static Reservation flightTicket(String ticketNumber){
        return new FakeBookingList()
                .findBooking(ticketNumber)
                .orElseThrow(NotFoundAnyBookingFlightException::new);
    }
}

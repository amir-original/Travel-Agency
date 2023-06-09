package travelAgency.use_case.fake;

import travelAgency.domain.reservation.Reservation;
import travelAgency.exceptions.ReservationNotFoundException;
import travelAgency.dao.database.reservation.ReservationListRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static travelAgency.use_case.fake.FakeReservationInformationBuilder.bookingInformation;

public class FakeReservationList implements ReservationListRepository {

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
    public Optional<Reservation> findReservation(String reservationNumber) {
        return bookings.stream()
                .filter(ticket -> ticket.hasSameTicketNumber(reservationNumber))
                .findFirst();
    }

    @Override
    public Optional<Reservation> findReservationByFlightNumber(String flightNumber) {
        return bookings
                .stream()
                .filter(reservation -> reservation.canMatchWith(flightNumber))
                .findFirst();
    }

    @Override
    public List<Reservation> getReservations() {
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
        return new FakeReservationList()
                .findReservation(ticketNumber)
                .orElseThrow(ReservationNotFoundException::new);
    }

}

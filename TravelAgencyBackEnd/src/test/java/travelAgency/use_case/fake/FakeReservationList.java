package travelAgency.use_case.fake;

import org.jetbrains.annotations.NotNull;
import travelAgency.dao.database.reservation.ReservationListRepository;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.passenger.Passenger;
import travelAgency.domain.reservation.Reservation;
import travelAgency.domain.reservation.ReservationNumber;
import travelAgency.exceptions.ReservationNotFoundException;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static travelAgency.use_case.fake.FakeFlight.flight;
import static travelAgency.use_case.fake.FakePassenger.passenger;

public class FakeReservationList implements ReservationListRepository {
    private static final String EXIST_FLIGHT_NUMBER = "0321";

    private final Flight flight = flight(EXIST_FLIGHT_NUMBER);

    private final Passenger passenger = passenger().build();

    private int numberOfTickets = 2;
    private final List<Reservation> bookings = new LinkedList<>();

    {
        bookings.addAll(List.of(
                makeReservation("AA-7845-65874"),
                makeReservation("AA-8525-32121"),
                makeReservation("AA-2145-45652")
        ));
    }

    @NotNull
    private Reservation makeReservation(String s) {
        final ReservationNumber reservationNumber = ReservationNumber.ofString(s);
        return Reservation.make(reservationNumber, flight, passenger, numberOfTickets);
    }

    @Override
    public void book(Reservation reservation) {
        bookings.add(reservation);
    }

    @Override
    public Optional<Reservation> findReservation(String reservationNumber) {
        return bookings.stream()
                .filter(reservation -> reservation.hasSameTicketNumber(reservationNumber))
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
    public void cancel(String reservationNumber) {
        bookings.stream()
                .filter(reservation -> reservation.reservationNumber().equals(reservationNumber))
                .findFirst()
                .ifPresent(bookings::remove);
    }

    @Override
    public void cancel(Reservation reservation) {
        cancel(reservation.reservationNumber());
    }

    @Override
    public void truncate() {
        this.bookings.clear();
    }

    public static Reservation getReservation(String reservationNumber){
        return new FakeReservationList()
                .findReservation(reservationNumber)
                .orElseThrow(ReservationNotFoundException::new);
    }

}

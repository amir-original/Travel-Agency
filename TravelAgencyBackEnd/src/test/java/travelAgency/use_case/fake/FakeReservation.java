package travelAgency.use_case.fake;

import org.jetbrains.annotations.NotNull;
import travelAgency.model.reservation.ReservationRepository;
import travelAgency.model.flight.Flight;
import travelAgency.model.passenger.Passenger;
import travelAgency.model.reservation.Reservation;
import travelAgency.model.reservation.ReservationNumber;
import travelAgency.application.use_case.ReservationNotFoundException;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static travelAgency.use_case.fake.FakeFlight.flight;
import static travelAgency.use_case.fake.FakePassenger.passenger;

public class FakeReservation implements ReservationRepository {
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
        return new FakeReservation()
                .findReservation(reservationNumber)
                .orElseThrow(ReservationNotFoundException::new);
    }

}

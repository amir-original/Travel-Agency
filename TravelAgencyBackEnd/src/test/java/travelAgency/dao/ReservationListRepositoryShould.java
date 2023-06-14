package travelAgency.dao;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.reservation.Reservation;
import travelAgency.use_case.fake.FakeReservationList;
import travelAgency.dao.database.reservation.ReservationListRepository;
import travelAgency.dao.database.reservation.ReservationListRepositoryImpl;
import travelAgency.dao.database.db_config.mysq.MySQLDbConnection;
import travelAgency.dao.database.flight.FlightRepository;
import travelAgency.dao.database.flight.FlightRepositoryImpl;
import travelAgency.dao.database.passenger.PassengerRepository;
import travelAgency.dao.database.passenger.PassengerRepositoryImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static travelAgency.use_case.fake.FakeFlight.flight;

public class ReservationListRepositoryShould {

    private FlightRepository flights;
    private PassengerRepository passengers;
    private ReservationListRepository bookingLists;

    @BeforeEach
    void setUp() {
        flights = new FlightRepositoryImpl(new MySQLDbConnection());
        passengers = new PassengerRepositoryImpl(new MySQLDbConnection());
        bookingLists = new ReservationListRepositoryImpl(new MySQLDbConnection());
    }

    @Test
    void book_a_flight_ticket_in_db() {

        final Reservation reservation = insertBooking();

        final Optional<Reservation> ticket = bookingLists.findReservation(reservation.reservationNumber());

        if (ticket.isEmpty()) {
            fail("findReservation not found!");
        }

        assertThat(ticket.get()).isEqualTo(reservation);
    }

    @Test
    void cancel_a_booking() {
        final Reservation reservation = insertBooking();

        bookingLists.cancel(reservation.reservationNumber());

        final boolean isEmpty = bookingLists.findReservation(reservation.reservationNumber()).isEmpty();

        assertThat(isEmpty).isTrue();
    }

    @NotNull
    private Reservation insertBooking() {
        final Reservation reservation = FakeReservationList.getReservation("AA-7845-65874");

        flights.addFlight(flight(reservation.flightNumber()));
        passengers.save(reservation.passenger());
        bookingLists.book(reservation);
        return reservation;
    }

    @AfterEach
    void tearDown() {
        bookingLists.truncate();
        flights.truncate();
        passengers.truncate();
    }
}

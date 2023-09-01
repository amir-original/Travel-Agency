package travelAgency.dao;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.infrastructure.persistence.jdbc_mysql.reservation.CouldNotBookReservation;
import travelAgency.infrastructure.db.ConnectionConfiguration;
import travelAgency.infrastructure.db.ConnectionConfigurationImpl;
import travelAgency.model.passenger.Passenger;
import travelAgency.model.reservation.Reservation;
import travelAgency.infrastructure.io.PropertiesReader;
import travelAgency.use_case.fake.FakeReservation;
import travelAgency.model.reservation.ReservationRepository;
import travelAgency.infrastructure.persistence.jdbc_mysql.reservation.ReservationRepositoryImpl;
import travelAgency.infrastructure.db.MySQLDbConnection;
import travelAgency.model.flight.FlightRepository;
import travelAgency.infrastructure.persistence.jdbc_mysql.flight.FlightRepositoryImpl;
import travelAgency.model.passenger.PassengerRepository;
import travelAgency.infrastructure.persistence.jdbc_mysql.passenger.PassengerRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static travelAgency.use_case.fake.FakeFlight.flight;
import static travelAgency.use_case.fake.FakePassenger.passenger;

public class ReservationRepositoryShould {

    private FlightRepository flights;
    private PassengerRepository passengers;
    private ReservationRepository bookingLists;

    @BeforeEach
    void setUp() {
        final ConnectionConfiguration configuration =
                ConnectionConfigurationImpl.of(PropertiesReader.of("test-db-config"));
        flights = new FlightRepositoryImpl(new MySQLDbConnection(configuration));
        passengers = new PassengerRepositoryImpl(new MySQLDbConnection(configuration));
        bookingLists = new ReservationRepositoryImpl(new MySQLDbConnection(configuration));
    }

    @Test
    void book_a_valid_reservation_without_throwing_any_exception() {

        final Reservation reservation = createReservation();

        final Optional<Reservation> ticket = bookingLists.findReservation(reservation.reservationNumber());

        if (ticket.isEmpty()) {
            fail("findReservation not found!");
            return;
        }

        assertThat(ticket.get()).isEqualTo(reservation);
    }

    @Test
    void cancel_a_booking_without_throwing_any_exception() {
        final Reservation reservation = createReservation();

        bookingLists.cancel(reservation.reservationNumber());

        final boolean isEmpty = bookingLists.findReservation(reservation.reservationNumber()).isEmpty();

        assertThat(isEmpty).isTrue();
    }


    @Test
    void fetch_all_reservations() {
        createReservation();

        final List<Reservation> reservations = bookingLists.getReservations();

        assertThat(reservations).isNotEmpty();
        assertThat(reservations.size()).isEqualTo(1);
    }

    @Test
    void not_create_reservation_when_reservation_number_is_duplicate() {
        createReservation();
        final Reservation reservation = FakeReservation.getReservation("AA-7845-65874");

        assertThatExceptionOfType(CouldNotBookReservation.class)
                .isThrownBy(() -> bookingLists.book(reservation));
    }


    @NotNull
    private Reservation createReservation() {
        final Reservation reservation = FakeReservation.getReservation("AA-7845-65874");
        Passenger passenger = passenger(reservation.passengerId());

        flights.addFlight(flight(reservation.flightNumber()));
        passengers.enroll(passenger);
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

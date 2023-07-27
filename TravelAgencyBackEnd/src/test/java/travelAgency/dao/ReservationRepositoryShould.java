package travelAgency.dao;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.infrastructure.db.ConnectionConfiguration;
import travelAgency.infrastructure.db.ConnectionConfigurationImpl;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static travelAgency.use_case.fake.FakeFlight.flight;
import static travelAgency.use_case.fake.FakePassenger.passenger;
import static travelAgency.use_case.fake.FakeReservationInformation.reservationInformation;

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
    void book_a__valid_reservation_without_throwing_any_exception() {

        final Reservation reservation = insertBooking();

        final Optional<Reservation> ticket = bookingLists.findReservation(reservation.reservationNumber());

        if (ticket.isEmpty()) {
            fail("findReservation not found!");
        }

        assertThat(ticket.get()).isEqualTo(reservation);
    }

    @Test
    void cancel_a_booking_without_throwing_any_exception() {
        final Reservation reservation = insertBooking();

        bookingLists.cancel(reservation.reservationNumber());

        final boolean isEmpty = bookingLists.findReservation(reservation.reservationNumber()).isEmpty();

        assertThat(isEmpty).isTrue();
    }

    @NotNull
    private Reservation insertBooking() {
        final Reservation reservation = FakeReservation.getReservation("AA-7845-65874");

        flights.addFlight(flight(reservation.flightNumber()));
        passengers.enroll(reservation.passenger());
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

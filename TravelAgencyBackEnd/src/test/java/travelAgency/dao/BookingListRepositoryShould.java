package travelAgency.dao;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.reservation.Reservation;
import travelAgency.use_case.fake.FakeBookingList;
import travelAgency.repository.booking.BookingListRepository;
import travelAgency.repository.booking.BookingListRepositoryImpl;
import travelAgency.repository.db.mysq.MySQLDbConnection;
import travelAgency.repository.flight.FlightRepository;
import travelAgency.repository.flight.FlightRepositoryImpl;
import travelAgency.repository.passenger.PassengerRepository;
import travelAgency.repository.passenger.PassengerRepositoryImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static travelAgency.use_case.fake.FakeFlight.flight;

public class BookingListRepositoryShould {

    private FlightRepository flights;
    private PassengerRepository passengers;
    private BookingListRepository bookingLists;

    @BeforeEach
    void setUp() {
        flights = new FlightRepositoryImpl(new MySQLDbConnection());
        passengers = new PassengerRepositoryImpl(new MySQLDbConnection());
        bookingLists = new BookingListRepositoryImpl(new MySQLDbConnection());
    }

    @Test
    void book_a_flight_ticket_in_db() {

        final Reservation reservation = insertBooking();

        final Optional<Reservation> ticket = bookingLists.findBooking(reservation.ticketNumber());

        if (ticket.isEmpty()) {
            fail("findBooking not found!");
        }

        assertThat(ticket.get()).isEqualTo(reservation);
    }

    @Test
    void cancel_a_booking() {
        final Reservation reservation = insertBooking();

        bookingLists.cancel(reservation.ticketNumber());

        final boolean isEmpty = bookingLists.findBooking(reservation.ticketNumber()).isEmpty();

        assertThat(isEmpty).isTrue();
    }

    @NotNull
    private Reservation insertBooking() {
        final Reservation reservation = FakeBookingList.flightTicket("78456587");

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

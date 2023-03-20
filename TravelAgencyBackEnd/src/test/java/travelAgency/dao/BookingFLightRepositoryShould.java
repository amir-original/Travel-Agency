package travelAgency.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.Flight;
import travelAgency.domain.FlightTicket;
import travelAgency.repository.booking.BookingFlightRepository;
import travelAgency.repository.booking.BookingFlightRepositoryImpl;
import travelAgency.repository.booking.BookingListRepository;
import travelAgency.repository.booking.BookingListRepositoryImpl;
import travelAgency.repository.db.mysq.MySQLDbConnection;
import travelAgency.repository.flight.FlightRepositoryImpl;
import travelAgency.repository.passenger.PassengerRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static travelAgency.fakeData.FakeFlightBuilder.flight;
import static travelAgency.fakeData.FakeFlightTicketBuilder.flightTicket;

public class BookingFLightRepositoryShould {

    private BookingFlightRepository api;
    private PassengerRepositoryImpl passengerApi;
    private FlightRepositoryImpl flightApi;
    private BookingListRepository findBooking;

    @BeforeEach
    void setUp() {
        final MySQLDbConnection mysql = new MySQLDbConnection();
        api = new BookingFlightRepositoryImpl(mysql);
        findBooking = new BookingListRepositoryImpl(mysql);
        flightApi = new FlightRepositoryImpl(mysql);
        passengerApi = new PassengerRepositoryImpl(mysql);
        clearTables();
    }

    @Test
    void book_information() {
        final FlightTicket flightTicket = flightTicket().build();

        passengerApi.save(flightTicket.passenger());
        api.book(flightTicket);
        Optional<FlightTicket> fetchedTicket = findBooking.ticket(flightTicket.ticketNumber());
        assertThat(fetchedTicket).isEqualTo(fetchedTicket);
    }

    @Test
    void fetch_all_tickets() {
        insertSingleFlight();
        insertSingleTicket();

        final List<FlightTicket> tickets = findBooking.tickets();

        assertThat(tickets).isNotEmpty();
        assertThat(tickets.size()).isEqualTo(1);
    }

    private void insertSingleFlight() {
        final Flight flight = flight().build();
        flightApi.createFlight(flight);
    }

    private void insertSingleTicket() {
        final FlightTicket flightTicket = flightTicket().build();
        passengerApi.save(flightTicket.passenger());
        api.book(flightTicket);
    }

    @AfterEach
    void tearDown() {
        clearTables();
    }

    private void clearTables() {
        passengerApi.truncate();
        flightApi.truncate();
        api.truncate();
    }
}

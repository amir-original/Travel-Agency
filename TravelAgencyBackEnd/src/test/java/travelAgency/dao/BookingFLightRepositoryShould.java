package travelAgency.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.booking.FlightTicket;
import travelAgency.domain.flight.Flight;
import travelAgency.use_case.fake.FakeBookingList;
import travelAgency.repository.booking.BookingListRepository;
import travelAgency.repository.booking.BookingListRepositoryImpl;
import travelAgency.repository.db.mysq.MySQLDbConnection;
import travelAgency.repository.flight.FlightRepositoryImpl;
import travelAgency.repository.passenger.PassengerRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static travelAgency.use_case.fake.FakeFlight.flight;

public class BookingFLightRepositoryShould {

    private BookingListRepository api;
    private PassengerRepositoryImpl passengerApi;
    private FlightRepositoryImpl flightApi;


    @BeforeEach
    void setUp() {
        final MySQLDbConnection mysql = new MySQLDbConnection();
        api = new BookingListRepositoryImpl(mysql);
        flightApi = new FlightRepositoryImpl(mysql);
        passengerApi = new PassengerRepositoryImpl(mysql);
    }

    @Test
    void book_information() {
        var flightTicket = insertSingleTicket();
        Optional<FlightTicket> fetchedTicket = api.findBooking(flightTicket.ticketNumber());
        assertThat(fetchedTicket).isEqualTo(fetchedTicket);
    }

    @Test
    void fetch_all_tickets() {
        insertSingleFlight();
        insertSingleTicket();

        final List<FlightTicket> tickets = api.getAllBookings();

        assertThat(tickets).isNotEmpty();
        assertThat(tickets.size()).isEqualTo(1);
    }

    private void insertSingleFlight() {
        final Flight flight = flight("0321");
        flightApi.addFlight(flight);
    }

    private FlightTicket insertSingleTicket() {
        final FlightTicket flightTicket = FakeBookingList.flightTicket("78456587");
        passengerApi.save(flightTicket.passenger());
        api.book(flightTicket);
        return flightTicket;
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

package travelAgency.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.reservation.Reservation;
import travelAgency.domain.flight.Flight;
import travelAgency.use_case.fake.FakeReservationList;
import travelAgency.dao.database.reservation.ReservationListRepository;
import travelAgency.dao.database.reservation.ReservationListRepositoryImpl;
import travelAgency.dao.database.db_config.mysq.MySQLDbConnection;
import travelAgency.dao.database.flight.FlightRepositoryImpl;
import travelAgency.dao.database.passenger.PassengerRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static travelAgency.use_case.fake.FakeFlight.flight;

public class BookingFLightRepositoryShould {

    private ReservationListRepository api;
    private PassengerRepositoryImpl passengerApi;
    private FlightRepositoryImpl flightApi;


    @BeforeEach
    void setUp() {
        final MySQLDbConnection mysql = new MySQLDbConnection();
        api = new ReservationListRepositoryImpl(mysql);
        flightApi = new FlightRepositoryImpl(mysql);
        passengerApi = new PassengerRepositoryImpl(mysql);
    }

    @Test
    void book_information() {
        var flightTicket = insertSingleTicket();
        Optional<Reservation> fetchedTicket = api.findReservation(flightTicket.reservationNumber());
        assertThat(fetchedTicket).isEqualTo(fetchedTicket);
    }

    @Test
    void fetch_all_tickets() {
        insertSingleFlight();
        insertSingleTicket();

        final List<Reservation> tickets = api.getReservations();

        assertThat(tickets).isNotEmpty();
        assertThat(tickets.size()).isEqualTo(1);
    }

    private void insertSingleFlight() {
        final Flight flight = flight("0321");
        flightApi.addFlight(flight);
    }

    private Reservation insertSingleTicket() {
        final Reservation reservation =
                FakeReservationList.getReservation("AA-7845-65874");
        passengerApi.save(reservation.passenger());
        api.book(reservation);
        return reservation;
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

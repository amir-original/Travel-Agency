package travelAgency.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.dao.database.db_config.ConnectionConfiguration;
import travelAgency.dao.database.db_config.ConnectionConfigurationImpl;
import travelAgency.domain.reservation.Reservation;
import travelAgency.domain.flight.Flight;
import travelAgency.exceptions.CouldNotBookReservation;
import travelAgency.helper.PropertiesReader;
import travelAgency.use_case.fake.FakeReservationList;
import travelAgency.dao.database.reservation.ReservationListRepository;
import travelAgency.dao.database.reservation.ReservationListRepositoryImpl;
import travelAgency.dao.database.db_config.mysql.MySQLDbConnection;
import travelAgency.dao.database.flight.FlightRepositoryImpl;
import travelAgency.dao.database.passenger.PassengerRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static travelAgency.use_case.fake.FakeFlight.flight;

public class BookingFLightRepositoryShould {

    private ReservationListRepository api;
    private PassengerRepositoryImpl passengerApi;
    private FlightRepositoryImpl flightApi;


    @BeforeEach
    void setUp() {
        final ConnectionConfiguration configuration =
                ConnectionConfigurationImpl.of(PropertiesReader.of("test-db-config"));
        final MySQLDbConnection mysql = new MySQLDbConnection(configuration);
        api = new ReservationListRepositoryImpl(mysql);
        flightApi = new FlightRepositoryImpl(mysql);
        passengerApi = new PassengerRepositoryImpl(mysql);
    }

    @Test
    void book_information() {
        var flightTicket = insertSingleReservation();
        Optional<Reservation> fetchedTicket = api.findReservation(flightTicket.reservationNumber());
        assertThat(fetchedTicket).isEqualTo(fetchedTicket);
    }

    @Test
    void fetch_all_reservations() {
        insertSingleFlight();
        insertSingleReservation();

        final List<Reservation> reservations = api.getReservations();

        assertThat(reservations).isNotEmpty();
        assertThat(reservations.size()).isEqualTo(1);
    }

    @Test
    void throw_CouldNotBookReservation_when_reservation_number_is_duplicate() {
        insertSingleReservation();
        final Reservation reservation = FakeReservationList.getReservation("AA-7845-65874");

        assertThatExceptionOfType(CouldNotBookReservation.class)
                .isThrownBy(() -> api.book(reservation));
    }

    private void insertSingleFlight() {
        final Flight flight = flight("0321");

        flightApi.addFlight(flight);
    }

    private Reservation insertSingleReservation() {
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

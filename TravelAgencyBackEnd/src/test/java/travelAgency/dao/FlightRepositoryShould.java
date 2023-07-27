package travelAgency.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.infrastructure.db.ConnectionConfiguration;
import travelAgency.infrastructure.db.ConnectionConfigurationImpl;
import travelAgency.model.flight.Flight;
import travelAgency.exceptions.CouldNotFoundFlight;
import travelAgency.exceptions.CouldNotStoreFlight;
import travelAgency.infrastructure.io.PropertiesReader;
import travelAgency.use_case.fake.FakeFlight;
import travelAgency.infrastructure.db.MySQLDbConnection;
import travelAgency.model.flight.FlightRepository;
import travelAgency.infrastructure.persistence.jdbc_mysql.flight.FlightRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static travelAgency.use_case.fake.FakeFlight.flight;

public class FlightRepositoryShould {


    private FlightRepository api;
    private FakeFlight fakeFlight;

    @BeforeEach
    void setUp() {
        final ConnectionConfiguration configuration =
                ConnectionConfigurationImpl.of(PropertiesReader.of("test-db-config"));
        api = new FlightRepositoryImpl(new MySQLDbConnection(configuration));
        api.truncate();
        fakeFlight = new FakeFlight();
    }


    @Test
    void add_a_flight_without_throwing_any_exception() {
        final Flight flight = insertSingleFlight();

        final Optional<Flight> fetchedFlight = api.findFlight(flight.flightNumber());

        assertThat(fetchedFlight.get()).isEqualTo(flight);
    }

    @Test
    void not_be_deleted_any_flight_when_flight_doesnt_exist() {
        final Flight flight = insertSingleFlight();

        api.deleteFlight(flight);

        assertThatExceptionOfType(CouldNotFoundFlight.class)
                .isThrownBy(()-> api.findFlight(flight.flightNumber()));
    }

    @Test
    void not_added_any_flight_when_flight_is_duplicate() {
        insertSingleFlight();

        assertThatExceptionOfType(CouldNotStoreFlight.class).isThrownBy(this::insertSingleFlight);
    }

    @Test
    void create_multiple_flights() {
        insertMultipleFlights();

        final List<Flight> fetchedFlights = api.flights();

        assertThat(fetchedFlights).isNotEmpty();
        assertThat(fetchedFlights.size()).isEqualTo(7);
    }

    @Test
    void truncate_flight_table() {
        insertMultipleFlights();

        api.truncate();

        final List<Flight> fetchedFlights = api.flights();
        assertThat(fetchedFlights).isEmpty();
    }

    private void insertMultipleFlights() {
        api.addFlights(fakeFlight.flights());
    }

    private Flight insertSingleFlight() {
        final Flight flight = flight("0321");

        api.addFlight(flight);
        return flight;
    }

    @AfterEach
    void tearDown() {
        api.truncate();
    }

}

package travelAgency.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.dao.database.db_config.ConnectionConfiguration;
import travelAgency.dao.database.db_config.ConnectionConfigurationImpl;
import travelAgency.domain.flight.Flight;
import travelAgency.exceptions.CouldNotFoundFlight;
import travelAgency.exceptions.CouldNotStoreFlight;
import travelAgency.helper.PropertiesReader;
import travelAgency.use_case.fake.FakeFlight;
import travelAgency.dao.database.db_config.mysql.MySQLDbConnection;
import travelAgency.dao.database.flight.FlightRepository;
import travelAgency.dao.database.flight.FlightRepositoryImpl;

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
    void create_new_flight() {
        final Flight flight = insertSingleFlight();

        final Optional<Flight> fetchedFlight = api.findFlight(flight.flightNumber());

        assertThat(fetchedFlight.get()).isEqualTo(flight);
    }

    @Test
    void throw_CouldNotFoundFlight_when_delete_flight() {
        final Flight flight = insertSingleFlight();

        api.deleteFlight(flight);

        assertThatExceptionOfType(CouldNotFoundFlight.class)
                .isThrownBy(()-> api.findFlight(flight.flightNumber()));
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

    @Test
    void throw_CouldNotStoreFlight_when_insert_duplicated_data() {
        insertSingleFlight();

        assertThatExceptionOfType(CouldNotStoreFlight.class).isThrownBy(this::insertSingleFlight);
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

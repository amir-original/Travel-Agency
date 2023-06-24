package travelAgency.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.dao.database.db_config.ConnectionConfiguration;
import travelAgency.dao.database.db_config.ConnectionConfigurationImpl;
import travelAgency.domain.flight.Flight;
import travelAgency.exceptions.CouldNotFoundFlight;
import travelAgency.helper.PropertiesReader;
import travelAgency.use_case.fake.FakeFlight;
import travelAgency.dao.database.db_config.mysql.MySQLDbConnection;
import travelAgency.dao.database.flight.FlightRepository;
import travelAgency.dao.database.flight.FlightRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class FindFlightRepositoryShould {

    private FlightRepository api;
    private FakeFlight fakeFlight;

    @BeforeEach
    void setUp() {
        final ConnectionConfiguration configuration =
                ConnectionConfigurationImpl.of(PropertiesReader.of("test-db-config"));
        api = new FlightRepositoryImpl(new MySQLDbConnection(configuration));
        fakeFlight = new FakeFlight();
    }

    @Test
    void find_flight_with_flight_number() {
        final Flight flight = insertSingleFlight("0321");

        final Optional<Flight> fetchedFlight = api.findFlight(flight.flightNumber());

        assertThat(fetchedFlight.get().flightNumber()).isEqualTo(flight.flightNumber());
    }

    @Test
    void find_all_flights() {
        insertFlights();

        final List<Flight> flights = api.flights();

        assertThat(flights).isNotEmpty();
        assertThat(flights.size()).isEqualTo(7);
    }

    @Test
    void delete_flight_by_flight_number() {
        final String flightNumber = "4784";
        final Flight flight = insertSingleFlight(flightNumber);

        assertThat(api.findFlight(flightNumber).get()).isEqualTo(flight);

        api.deleteFlight(flight);

        assertThatExceptionOfType(CouldNotFoundFlight.class)
                .isThrownBy(()->api.findFlight(flightNumber));
    }

    @Test
    void throw_CouldNotFoundFlight_when_find_flight_that_is_not_exist() {
        assertThatExceptionOfType(CouldNotFoundFlight.class)
                .isThrownBy(()->api.findFlight("Not Found Flight Number"));
    }

    private Flight insertSingleFlight(String flightNumber) {
        final Flight flight = FakeFlight.flight(flightNumber);
        api.addFlight(flight);
        return flight;
    }

    private void insertFlights() {
        api.addFlights(fakeFlight.flights());
    }

    @AfterEach
    void tearDown() {
        api.truncate();
    }
}

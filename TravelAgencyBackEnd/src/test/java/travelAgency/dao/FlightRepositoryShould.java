package travelAgency.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.flight.Flight;
import travelAgency.repository.flight.SQLFlightInsertionException;
import travelAgency.use_case.fake.FakeFlight;
import travelAgency.repository.db.mysq.MySQLDbConnection;
import travelAgency.repository.flight.FlightRepository;
import travelAgency.repository.flight.FlightRepositoryImpl;

import java.sql.SQLIntegrityConstraintViolationException;
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
        api = new FlightRepositoryImpl(new MySQLDbConnection());
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
    void delete_flight() {
        final Flight flight = insertSingleFlight();

        api.deleteFlight(flight.flightNumber());

        final Optional<Flight> fetchedFlight = api.findFlight(flight.flightNumber());

        assertThat(fetchedFlight.isPresent()).isFalse();
    }

    @Test
    void create_multiple_flights() {
        insertMultipleFlights();

        final List<Flight> fetchedFlights = api.flights();

        assertThat(fetchedFlights).isNotEmpty();
        assertThat(fetchedFlights.size()).isEqualTo(5);
    }

    @Test
    void truncate_flight_table() {
        insertMultipleFlights();

        api.truncate();

        final List<Flight> fetchedFlights = api.flights();
        assertThat(fetchedFlights).isEmpty();
    }

    @Test
    void throw_SQLFlightInsertionException_when_insert_duplicated_data() {
        insertSingleFlight();

        assertThatExceptionOfType(SQLFlightInsertionException.class).isThrownBy(this::insertSingleFlight);

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

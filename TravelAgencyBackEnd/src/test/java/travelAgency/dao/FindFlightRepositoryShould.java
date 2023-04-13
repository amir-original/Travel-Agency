package travelAgency.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.flight.Flight;
import travelAgency.use_case.fake.FakeFlight;
import travelAgency.repository.db.mysq.MySQLDbConnection;
import travelAgency.repository.flight.FlightRepository;
import travelAgency.repository.flight.FlightRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class FindFlightRepositoryShould {

    private FlightRepository api;
    private FakeFlight fakeFlight;

    @BeforeEach
    void setUp() {
        api = new FlightRepositoryImpl(new MySQLDbConnection());
        fakeFlight = new FakeFlight();
    }

    @Test
    void find_flight_with_flight_number() {
        final Flight flight = insertSingleFlight();

        final Optional<Flight> fetchedFlight = api.findFlight(flight.flightNumber());

        assertThat(fetchedFlight.get().flightNumber()).isEqualTo(flight.flightNumber());
    }

    @Test
    void find_all_matched_flights_with_entered_flight_plan() {
        insertFlights();

        final Flight flight =FakeFlight.flight().build();
        final List<Flight> flights = flight.plan().search(api.flights());

        assertThat(flights).isNotEmpty();
        assertThat(flights.size()).isEqualTo(5);
    }

    private Flight insertSingleFlight() {
        final Flight flight = FakeFlight.flight("0321");
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

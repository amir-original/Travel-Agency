package travelAgency.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightBuilder;
import travelAgency.domain.city.City;
import travelAgency.fake.FakeFlight;
import travelAgency.repository.db.mysq.MySQLDbConnection;
import travelAgency.repository.flight.FlightRepository;
import travelAgency.repository.flight.FlightRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;
import static travelAgency.domain.city.City.PARIS;
import static travelAgency.domain.city.City.TEHRAN;
import static travelAgency.fake.FakeFlightBuilder.flight;

public class FindFlightRepositoryShould {

    private FlightRepository api;

    @BeforeEach
    void setUp() {
        api = new FlightRepositoryImpl(new MySQLDbConnection());
        api.truncate();
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

        final Flight flight = flight().build();
        final List<Flight> flights = api.findFlights(flight.plan());

        assertThat(flights).isNotEmpty();
        assertThat(flights.size()).isEqualTo(5);
    }

    private Flight insertSingleFlight() {
        final Flight flight = new FlightBuilder()
                .withFlightNumber("fly580")
                .from(TEHRAN)
                .to(PARIS)
                .departureAt(of(2023, 3, 3))
                .arrivalAt(of(2023, 3, 6))
                .withPrice(47500)
                .build();

        api.addFlight(flight);
        return flight;
    }

    private void insertFlights() {
        final List<Flight> flights = List.of(
                flight().withFlightNumber("145").withPrice(456).build(),
                flight().withFlightNumber("478").withPrice(100).build(),
                flight().withFlightNumber("748").withPrice(700).build(),
                flight().withFlightNumber("887").withPrice(500).build(),
                flight().withFlightNumber("874").withPrice(700).build());

        api.addFlights(flights);
    }
}

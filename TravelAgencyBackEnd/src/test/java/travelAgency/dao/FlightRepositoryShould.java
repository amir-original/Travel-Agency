package travelAgency.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.*;
import travelAgency.repository.flight.FindFlightRepository;
import travelAgency.repository.flight.FindFlightRepositoryImpl;
import travelAgency.repository.flight.FlightRepository;
import travelAgency.repository.flight.FlightRepositoryImpl;
import travelAgency.repository.db.mysq.MySQLDbConnection;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;
import static travelAgency.domain.city.City.PARIS;
import static travelAgency.domain.city.City.TEHRAN;
import static travelAgency.fakeData.FakeFlightBuilder.flight;

public class FlightRepositoryShould {


    private static final String HOST = "jdbc:mysql://localhost:3306/travel_agency";
    private static final String USER = "root";
    private static final String PASS = "";
    private static final String DELETE_SQL = "delete from flights order by id desc limit 1";
    private FlightRepository api;
    private FindFlightRepository findApi;

    @BeforeEach
    void setUp() {
        api = new FlightRepositoryImpl(new MySQLDbConnection());
        findApi = new FindFlightRepositoryImpl(new MySQLDbConnection());
        api.truncate();
    }


    @Test
    void create_new_flight() {
        final Flight flight = insertSingleFlight();

        final Optional<Flight> fetchedFlight = findApi.findFlight(flight.flightNumber());

        assertThat(fetchedFlight.get()).isEqualTo(flight);
    }

    @Test
    void delete_flight() {
        final Flight flight = insertSingleFlight();

        api.deleteFlight(flight.flightNumber());

        final Optional<Flight> fetchedFlight = findApi.findFlight(flight.flightNumber());

        assertThat(fetchedFlight.isPresent()).isFalse();
    }

    @Test
    void create_multiple_flights() {
        insertFlights();

        final List<Flight> fetchedFlights = findApi.getFlights();

        assertThat(fetchedFlights).isNotEmpty();
        assertThat(fetchedFlights.size()).isEqualTo(4);
    }

    @Test
    void truncate_flight_table() {
        insertFlights();

        api.truncate();

        final List<Flight> fetchedFlights = findApi.getFlights();
        assertThat(fetchedFlights).isEmpty();
    }

    private void insertFlights() {
        final List<Flight> flights = List.of(flight().withFlightNumber("145").withPrice(456).build(),
                flight().withFlightNumber("478").withPrice(100).build(),
                flight().withFlightNumber("748").withPrice(700).build(),
                flight().withFlightNumber("887").withPrice(500).build());

        api.createFlights(flights);
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

    @AfterEach
    void tearDown() {
        try (final Connection conn = DriverManager.getConnection(HOST, USER, PASS);
        final PreparedStatement delete = conn.prepareStatement(DELETE_SQL)) {
         delete.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        }
    }

    private void fail() {
        Assertions.fail("can't connect to database");
    }
}

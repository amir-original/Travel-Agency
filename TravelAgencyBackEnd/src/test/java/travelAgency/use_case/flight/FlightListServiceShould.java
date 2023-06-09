package travelAgency.use_case.flight;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.exceptions.FlightNotFoundException;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;
import travelAgency.services.flight.FlightListService;
import travelAgency.services.flight.FlightListServiceImpl;
import travelAgency.use_case.fake.FakeFlight;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static travelAgency.domain.city.City.BAGHDAD;
import static travelAgency.domain.city.City.LONDON;
import static travelAgency.use_case.fake.FakeFlight.flight;
import static travelAgency.use_case.fake.FakeFlightPlanBuilder.flightPlan;

public class FlightListServiceShould {

    private static final String NOT_EXIST_FLIGHT_NUMBER = "fwefefef";
    private FlightListService app;

    @BeforeEach
    void setUp() {
        app = new FlightListServiceImpl(new FakeFlight());
    }

    @Test
    void find_flights_with_entered_flight_information() {
        final FlightPlan flightPlan = flightPlan().build();
        final List<Flight> flights = app.searchFlights(flightPlan);

        assertAll(
                () -> assertThat(flights).isNotEmpty(),
                () -> assertThat(flights.get(0).plan().equals(flightPlan)).isTrue()
        );
    }

    @Test
    void return_empty_list_when_entered_wrong_information() {
        FlightPlan flightPlan = flightPlan().from(LONDON).to(BAGHDAD).build();
        assertThat(app.searchFlights(flightPlan)).isEmpty();
    }

    @Test
    void find_flight_with_flight_number() {
        final String flightNumber = "0321";
        assertThat(app.findFlight(flightNumber)).isEqualTo(flight(flightNumber));
    }

    @Test
    void throw_FlightNotFoundException_when_find_flight_with_wrong_flight_number() {
        assertThatExceptionOfType(FlightNotFoundException.class)
                .isThrownBy(() -> app.findFlight(NOT_EXIST_FLIGHT_NUMBER));
    }
}

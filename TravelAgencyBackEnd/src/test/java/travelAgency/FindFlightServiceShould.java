package travelAgency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.Flight;
import travelAgency.domain.FlightPlan;
import travelAgency.domain.exceptions.FlightNumberNotFoundException;
import travelAgency.fakeData.FakeFindFlight;
import travelAgency.services.flights.FindFlights;
import travelAgency.services.flights.FindFlightsService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static travelAgency.domain.city.City.BAGHDAD;
import static travelAgency.domain.city.City.LONDON;
import static travelAgency.fakeData.FakeFlightBuilder.flight;
import static travelAgency.fakeData.FakeFlightPlanBuilder.flightPlan;

public class FindFlightServiceShould {

    private static final String NOT_EXIST_FLIGHT_NUMBER = "fwefefef";
    private FindFlightsService app;

    @BeforeEach
    void setUp() {
        app = new FindFlights(new FakeFindFlight());
    }

    @Test
    void find_flights_with_entered_flight_information() {
        final FlightPlan flightPlan = flightPlan().build();
        final List<Flight> flights = app.findFlights(flightPlan);

        assertAll(
                () -> assertThat(flights).isNotEmpty(),
                () -> assertThat(flights.get(0).plan().equals(flightPlan)).isTrue()
        );
    }

    @Test
    void return_empty_list_when_entered_wrong_information() {
        FlightPlan flightPlan = flightPlan().from(LONDON).to(BAGHDAD).build();
        assertThat(app.findFlights(flightPlan)).isEmpty();
    }

    @Test
    void find_flight_with_flight_number() {
        final Flight flight = flight().build();

        assertAll(
                () -> assertThat(app.isExistThisFlight(flight.plan())).isTrue(),
                () -> assertThat(app.findFlight(flight.flightNumber())).isEqualTo(flight().build())

        );
    }

    @Test
    void throw_NotFoundAnyFlightException_when_find_flight_with_wrong_flight_number() {
        assertThatExceptionOfType(FlightNumberNotFoundException.class)
                .isThrownBy(() -> app.findFlight(NOT_EXIST_FLIGHT_NUMBER));
    }

    @Test
    void get_all_flights() {
        assertThat(app.getFlights()).isNotEmpty();
        assertThat(app.getFlights().size()).isEqualTo(5);
    }
}

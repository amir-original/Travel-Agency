package travelAgency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.Flight;
import travelAgency.domain.FlightLocation;
import travelAgency.fakeData.FakeFlight;
import travelAgency.services.flights.SearchFlightEngine;
import travelAgency.domain.country.France;
import travelAgency.domain.country.India;
import travelAgency.domain.country.Iran;
import travelAgency.fakeData.FakeFlightData;
import travelAgency.services.flights.FindFlights;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class SearchFlightEngineShould {

    private SearchFlightEngine engine;
    private FakeFlightData fakeFlight;

    @BeforeEach
    void setUp() {
        engine = new SearchFlightEngine(new FindFlights(new FakeFlight()));
        fakeFlight = new FakeFlightData();
    }

    @Test
    void search_in_flights_by_entered_search_flight_plan() {
        final FlightLocation existFlightPlan = new FlightLocation(Iran.TEHRAN, France.PARIS);
        final List<Flight> flights = engine.search(fakeFlight.getFlightPlan(existFlightPlan));
        assertAll(
                () -> assertThat(flights).isNotEmpty(),
                () -> assertThat(flights.size()).isEqualTo(2)
        );

    }

    @Test
    void return_empty_list_when_not_match_exists_flights_with_search_flight() {
        final FlightLocation notExistFlightPlan = new FlightLocation(Iran.TEHRAN, India.DELHI);
        final List<Flight> flights = engine.search(fakeFlight.getFlightPlan(notExistFlightPlan));
        assertAll(
                () -> assertThat(flights).isEmpty(),
                () -> assertThat(flights.size()).isEqualTo(0)
        );
    }
}

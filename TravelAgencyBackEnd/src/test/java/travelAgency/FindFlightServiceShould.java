package travelAgency;

import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.Flight;
import travelAgency.domain.FlightPlan;
import travelAgency.fakeData.FakeFindFlight;
import travelAgency.services.flights.FindFlights;
import travelAgency.services.flights.FindFlightsService;

import java.time.LocalDate;
import java.util.List;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static travelAgency.domain.city.City.BAGHDAD;
import static travelAgency.domain.city.City.LONDON;
import static travelAgency.fakeData.FakeFlightBuilder.flight;
import static travelAgency.fakeData.FakeFlightPlanBuilder.flightPlan;

public class FindFlightServiceShould {

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
        assertAll(
                () -> assertThat(app.findFlights(flightPlan)).isEmpty(),
                () -> assertThat(app.isExistThisFlight(flightPlan)).isFalse()
        );

    }

  /*  @Test
    @IgnoreForBinding
    void find_flight_information() {
        final LocalDate departure = of(2023, 3, 3);
        final LocalDate threeDaysLater = departure.plusDays(3);
        FlightPlan flightPlan = flightPlan().departureAt(departure).arrivalAt(threeDaysLater).build();

        assertAll(
                () -> assertThat(app.isExistThisFlight(flightPlan)).isTrue(),
                () -> assertThat(app.findFlight(flightPlan).get()).isEqualTo(flight().build())

        );
    }*/

    @Test
    void get_all_flights() {
        assertThat(app.getFlights()).isNotEmpty();
    }
}

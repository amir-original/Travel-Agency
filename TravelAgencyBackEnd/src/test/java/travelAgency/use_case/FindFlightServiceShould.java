package travelAgency.use_case;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.application.dto.FlightDto;
import travelAgency.application.dto.FlightPlanRequest;
import travelAgency.infrastructure.mapper.FlightMapper;
import travelAgency.exceptions.FlightNotFoundException;
import travelAgency.model.flight.Flight;
import travelAgency.model.flight.FlightPlan;
import travelAgency.application.use_case.FindFlightService;
import travelAgency.application.use_case.FindFlight;
import travelAgency.use_case.fake.FakeFlight;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static travelAgency.model.city.City.BAGHDAD;
import static travelAgency.model.city.City.LONDON;
import static travelAgency.use_case.fake.FakeFlight.flight;
import static travelAgency.use_case.fake.FakeFlightPlanBuilder.flightPlan;

public class FindFlightServiceShould {

    private static final String NOT_EXIST_FLIGHT_NUMBER = "fwefefef";
    private FindFlightService app;
    private FlightMapper flightMapper;

    @BeforeEach
    void setUp() {
        app = new FindFlight(new FakeFlight());
        flightMapper = new FlightMapper();
    }

    @Test
    void find_flights_with_entered_valid_flight_plan() {
        final FlightPlan flightPlan = flightPlan().build();
        FlightPlanRequest flightPlanRequest = flightMapper.toView(flightPlan);
        final List<FlightDto> flights = app.searchFlights(flightPlanRequest);

        //@TODO i should be refactor flight dto to accept flight plan request as a dto
        assertAll(
                () -> assertThat(flights).isNotEmpty(),
                () -> assertThat(flights.get(0).from().equals(flightPlanRequest.from())).isTrue()
        );
    }

    @Test
    void not_return_anything_when_flight_information_is_wrong() {
        FlightPlan flightPlan = flightPlan().from(LONDON).to(BAGHDAD).build();

        FlightPlanRequest flightPlanRequest = flightMapper.toView(flightPlan);
        assertThat(app.searchFlights(flightPlanRequest)).isEmpty();
    }

    @Test
    void find_flight_with_flight_number_without_throwing_any_exception() {
        final String flightNumber = "0321";
        final FlightDto flightDto = app.findFlight(flightNumber);

        final Flight flight = flightMapper.toEntity(flightDto);
        assertThat(flight).isEqualTo(flight(flightNumber));
    }

    @Test
    void not_find_any_flight_when_flight_number_is_incorrect() {
        assertThatExceptionOfType(FlightNotFoundException.class)
                .isThrownBy(() -> app.findFlight(NOT_EXIST_FLIGHT_NUMBER));
    }
}

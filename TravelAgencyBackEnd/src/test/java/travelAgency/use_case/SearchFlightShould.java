package travelAgency.use_case;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.application.dto.FlightDto;
import travelAgency.infrastructure.libraries.currency_converter.ExchangeRateDAO;
import travelAgency.infrastructure.mapper.FlightMapper;
import travelAgency.model.flight.Flight;
import travelAgency.model.flight.FlightPlan;
import travelAgency.model.rate.Money;
import travelAgency.infrastructure.libraries.currency_converter.CurrencyConverter;
import travelAgency.infrastructure.libraries.currency_converter.FindExchangeRate;
import travelAgency.application.use_case.FindFlightService;
import travelAgency.application.use_case.FindFlight;
import travelAgency.use_case.fake.FakeFlight;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static travelAgency.model.rate.Currency.IRR;
import static travelAgency.model.rate.Currency.USD;
import static travelAgency.use_case.fake.FakeFlight.flight;
import static travelAgency.use_case.fake.FakeFlightPlanBuilder.flightPlan;

public class SearchFlightShould {

    private FindFlightService app;
    private FlightMapper flightMapper;

    @BeforeEach
    void setUp() {
        app = new FindFlight(new FakeFlight());

        flightMapper = new FlightMapper();
    }

    @Test
    void be_find_all_flights_that_matched_with_searched_criteria_flight_plan() {
        FlightPlan flightPlan = flightPlan().build();
        final List<FlightDto> flights = app.searchFlights(flightMapper.toView(flightPlan));
        assertAll(
                () -> assertThat(flights).isNotEmpty(),
                () -> assertThat(flights.size()).isEqualTo(5)
        );
    }

    @Test
    void not_be_return_anything_when_no_flights_match_the_searched_criteria() {
        FlightPlan flightPlan = flightPlan().withNotExistLocation().build();
        final List<FlightDto> flights = app.searchFlights(flightMapper.toView(flightPlan));
        assertAll(
                () -> assertThat(flights).isEmpty(),
                () -> assertThat(flights.size()).isEqualTo(0)
        );
    }

    @Test
    void not_be_find_any_flight_when_location_is_null() {
        assertAll(
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> {
                            FlightPlan flightPlan = flightPlan().departureAt(null).build();
                            app.searchFlights(flightMapper.toView(flightPlan));
                        }),
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> {
                            FlightPlan plan = flightPlan().arrivalAt(null).build();
                            app.searchFlights(flightMapper.toView(plan));
                        })
        );

    }

}

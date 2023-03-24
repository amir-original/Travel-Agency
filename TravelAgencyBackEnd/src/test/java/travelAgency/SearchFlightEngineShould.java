package travelAgency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.flight.Flight;
import travelAgency.fakeData.FakeFindFlight;
import travelAgency.services.flights.FlightServiceImpl;
import travelAgency.services.flights.SearchFlightEngine;
import travelAgency.services.priceConverter.CurrencyConverterServiceImpl;
import travelAgency.services.priceConverter.currencyApi.CurrencyConverterApiService;
import travelAgency.services.priceConverter.currencyApi.DollarToRialConverterApi;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static travelAgency.fakeData.FakeFlightPlanBuilder.flightPlan;

public class SearchFlightEngineShould {

    private static final double ONE_DOLLAR_TO_RIAL = 42700D;
    private SearchFlightEngine engine;

    @BeforeEach
    void setUp() {
        engine = new SearchFlightEngine(new FlightServiceImpl(new FakeFindFlight()));
    }

    @Test
    void search_in_flights_by_entered_search_flight_plan() {
        final List<Flight> flights = engine.search(flightPlan().build());
        assertAll(
                () -> assertThat(flights).isNotEmpty(),
                () -> assertThat(flights.size()).isEqualTo(2)
        );

    }

    @Test
    void return_empty_list_when_not_match_exists_flights_with_search_flight() {
        final List<Flight> flights = engine.search(flightPlan().withNotExistLocation().build());
        assertAll(
                () -> assertThat(flights).isEmpty(),
                () -> assertThat(flights.size()).isEqualTo(0)
        );
    }

    @Test
    void throw_IllegalArgumentException_when_enter_flight_location_null() {
        assertAll(
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> engine.search(flightPlan().departureAt(null).build())),
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> engine.search(flightPlan().arrivalAt(null).build()))
                );

    }

    @Test
    void converted_search_flight_price_from_dollar_to_rial() {
        final List<Flight> flights = engine.search(flightPlan().build());

        final CurrencyConverterApiService mock = mock(DollarToRialConverterApi.class);
        when(mock.diffAmount()).thenReturn(ONE_DOLLAR_TO_RIAL);

        final CurrencyConverterServiceImpl dollarConverter = new CurrencyConverterServiceImpl(mock);

        assertAll(
                () -> assertThat(flights.get(0).price()).isEqualTo(145),
                () -> assertThat(flights.get(0).price(dollarConverter)).isEqualTo(6191500.0)
        );
    }
}

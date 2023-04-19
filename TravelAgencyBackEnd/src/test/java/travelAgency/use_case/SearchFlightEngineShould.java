package travelAgency.use_case;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.flight.Flight;
import travelAgency.services.priceConverter.CurrencyConverterService;
import travelAgency.services.priceConverter.currencyApi.IRRToUSDConverter;
import travelAgency.services.priceConverter.exception.AmountNotNegativeException;
import travelAgency.use_case.fake.FakeBookingList;
import travelAgency.use_case.fake.FakeFlight;
import travelAgency.services.flight.FlightService;
import travelAgency.services.flight.FlightServiceImpl;
import travelAgency.services.priceConverter.CurrencyConverterServiceImpl;
import travelAgency.services.priceConverter.currencyApi.ExchangeRateService;
import travelAgency.services.priceConverter.currencyApi.USDToIRRConverter;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static travelAgency.use_case.fake.FakeFlight.flight;
import static travelAgency.use_case.fake.FakeFlightPlanBuilder.flightPlan;

public class SearchFlightEngineShould {

    private static final double ONE_DOLLAR_TO_RIAL = 42700D;
    private static final Double ONE_RIAL_TO_DOLLAR = 0.000024;
    private FlightService app;
    private ExchangeRateService dollarToRialConverter;
    private ExchangeRateService rialToDollarConverter;

    @BeforeEach
    void setUp() {
        app = new FlightServiceImpl(new FakeFlight());
        dollarToRialConverter = getCurrencyConverter(USDToIRRConverter.class, ONE_DOLLAR_TO_RIAL);
        rialToDollarConverter = getCurrencyConverter(IRRToUSDConverter.class, ONE_RIAL_TO_DOLLAR);
    }

    @Test
    void get_flights_by_search_flight_plan() {
        final List<Flight> flights = app.searchFlights(flightPlan().build());
        assertAll(
                () -> assertThat(flights).isNotEmpty(),
                () -> assertThat(flights.size()).isEqualTo(5)
        );
    }

    @Test
    void return_empty_list_when_not_match_exists_flights_with_search_flight() {
        final List<Flight> flights = app.searchFlights(flightPlan().withNotExistLocation().build());
        assertAll(
                () -> assertThat(flights).isEmpty(),
                () -> assertThat(flights.size()).isEqualTo(0)
        );
    }

    @Test
    void throw_IllegalArgumentException_when_flight_location_is_null() {
        assertAll(
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.searchFlights(flightPlan().departureAt(null).build())),
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.searchFlights(flightPlan().arrivalAt(null).build()))
        );

    }

    @Test
    void converted_price_from_dollar_to_rial() {
        final List<Flight> flights = app.searchFlights(flightPlan().build());

        final CurrencyConverterService dollarConverter = getCurrencyConverterService(dollarToRialConverter);
        final Flight flight = flight("0321");

        assertAll(
                () -> assertThat(flights).contains(flight),
                () -> assertThat(flight.price(dollarConverter)).isEqualTo("6,191,500.00")
        );
    }

    @Test
    void converted_price_from_rial_to_dollar() {
        final CurrencyConverterService dollarConverter = getCurrencyConverterService(rialToDollarConverter);
        double flightPrice = 870000;
        final double convertedPrice = flightPrice * ONE_RIAL_TO_DOLLAR;

        assertAll(
                () -> assertThat(dollarConverter.convert(flightPrice))
                        .isEqualTo(convertedPrice)
        );
    }

    @Test
    void throw_AmountNotNegativeException_when_converted_negative_amount() {
        final CurrencyConverterService dollarConverter = getCurrencyConverterService(rialToDollarConverter);

        assertThatExceptionOfType(AmountNotNegativeException.class)
                .isThrownBy(() -> dollarConverter.convert(-500));
    }

    @NotNull
    private CurrencyConverterService getCurrencyConverterService(ExchangeRateService rialToDollarConverter) {
        return new CurrencyConverterServiceImpl(rialToDollarConverter);
    }

    @NotNull
    private ExchangeRateService
    getCurrencyConverter(Class<? extends ExchangeRateService> classToMock, Double currencyAmount) {
        final ExchangeRateService mock = mock(classToMock);
        when(mock.diffAmount()).thenReturn(currencyAmount);
        return mock;
    }
}

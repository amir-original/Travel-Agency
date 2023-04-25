package travelAgency.use_case.flight;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.dao.api.ExchangeRateDAO;
import travelAgency.domain.flight.currency.Currency;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.currency.Money;
import travelAgency.services.flight.FlightListService;
import travelAgency.services.flight.FlightListServiceImpl;
import travelAgency.services.currency_exchange.CurrencyConverterService;
import travelAgency.services.currency_exchange.CurrencyConverterServiceImpl;
import travelAgency.services.currency_exchange.currency_api.ExchangeRateService;
import travelAgency.services.currency_exchange.currency_api.IRRToUSDConverter;
import travelAgency.services.currency_exchange.currency_api.USDToIRRConverter;
import travelAgency.use_case.fake.FakeFlight;

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

    private FlightListService app;
    private CurrencyConverterService usdToIrrConverter;
    private CurrencyConverterService irrToUsdConverter;

    @BeforeEach
    void setUp() {
        app = new FlightListServiceImpl(new FakeFlight());
        final ExchangeRateDAO exchangeRateDAO = mockExchangeRateDAO();
        ExchangeRateService dollarToRialConverter = new USDToIRRConverter(exchangeRateDAO);
        ExchangeRateService rialToDollarConverter = new IRRToUSDConverter(exchangeRateDAO);
        usdToIrrConverter = getCurrencyConverterService(dollarToRialConverter);
        irrToUsdConverter = getCurrencyConverterService(rialToDollarConverter);
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

        final Flight flight = flight("0321");

        Money expectedMoney = new Money(4270000.00,Currency.IRR);

        assertAll(
                () -> assertThat(flights).contains(flight),
                () -> assertThat(usdToIrrConverter.convert(flight.price())).isEqualTo(expectedMoney)
        );
    }

    @Test
    void converted_price_from_rial_to_dollar() {
        Money flightPrice = new Money(870000, Currency.IRR);

        final double convertedPrice = flightPrice.amount() * ONE_RIAL_TO_DOLLAR;


        assertThat(irrToUsdConverter.convert(flightPrice).amount()).isEqualTo(convertedPrice);
    }

    @Test
    void does_not_convert_price_when_has_not_the_same_currency() {
        Money irrFlightPrice = new Money(870000, Currency.IRR);
        Money usdFlightPrice = new Money(240, Currency.USD);


        assertThat(usdToIrrConverter.convert(irrFlightPrice).amount()).isEqualTo(870000);

        assertThat(irrToUsdConverter.convert(usdFlightPrice).amount()).isEqualTo(240);
    }

    @Test
    void throw_IllegalArgumentException_when_converted_negative_amount() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> irrToUsdConverter.convert(new Money(-500, Currency.USD)));
    }



    @NotNull
    private CurrencyConverterService getCurrencyConverterService(ExchangeRateService rialToDollarConverter) {
        return new CurrencyConverterServiceImpl(rialToDollarConverter);
    }

    @NotNull
    private ExchangeRateDAO mockExchangeRateDAO() {
        final ExchangeRateDAO mock = mock(ExchangeRateDAO.class);
        when(mock.usdToIrr()).thenReturn(ONE_DOLLAR_TO_RIAL);
        when(mock.irrToUsd()).thenReturn(ONE_RIAL_TO_DOLLAR);
        return mock;
    }
}

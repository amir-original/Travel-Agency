package travelAgency.use_case.flight;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.application.exchange_rates.ExchangeRateDAO;
import travelAgency.model.flight.Flight;
import travelAgency.model.rate.currency.Money;
import travelAgency.application.exchange_rates.CurrencyConverter;
import travelAgency.application.exchange_rates.ExchangeRates;
import travelAgency.application.flight.FlightListService;
import travelAgency.application.flight.FlightListServiceImpl;
import travelAgency.use_case.fake.FakeFlight;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static travelAgency.model.rate.currency.Currency.IRR;
import static travelAgency.model.rate.currency.Currency.USD;
import static travelAgency.use_case.fake.FakeFlight.flight;
import static travelAgency.use_case.fake.FakeFlightPlanBuilder.flightPlan;

public class SearchFlightEngineShould {

    private static final double ONE_DOLLAR_TO_RIAL = 42700D;
    private static final Double ONE_RIAL_TO_DOLLAR = 0.000024;

    private FlightListService app;
    private CurrencyConverter currencyConverter;

    @BeforeEach
    void setUp() {
        app = new FlightListServiceImpl(new FakeFlight());
        final ExchangeRateDAO exchangeRateDAO = mockExchangeRateDAO();
        currencyConverter = new CurrencyConverter(new ExchangeRates(exchangeRateDAO));
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
    void it_convert_an_amount_using_the_exchange_rate() {
        final List<Flight> flights = app.searchFlights(flightPlan().build());

        final Flight flight = flight("0321");

        Money expectedMoney = Money.of(4270000.00, IRR);

        assertAll(
                () -> assertThat(flights).contains(flight),
                () -> assertThat(currencyConverter.convert(flight.price(), IRR)).isEqualTo(expectedMoney)
        );
    }

    @Test
    void it_convert_an_amount_from_irr_to_usd_by_using_the_exchange_rate() {
        Money flightPrice = Money.of(870000, IRR);

        final double convertedMoney = currencyConverter.convert(flightPrice, USD).amount();

        assertThat(convertedMoney).isEqualTo(20.88);
    }

    @Test
    void return_the_base_amount_itself_when_base_and_target_currency_is_the_same() {
        final Money money = Money.of(45,USD);
        final Money convertedMoney = currencyConverter.convert(money, USD);
        assertThat(convertedMoney.amount()).isEqualTo(money.amount());
        assertThat(convertedMoney.currency()).isEqualTo(USD);
    }

    @Test
    void throw_IllegalArgumentException_when_converted_negative_amount() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> currencyConverter.convert(Money.of(-500, USD),IRR));
    }


    @NotNull
    private ExchangeRateDAO mockExchangeRateDAO() {
        final ExchangeRateDAO mock = mock(ExchangeRateDAO.class);
        when(mock.exchangeRateFor(USD,IRR)).thenReturn(ONE_DOLLAR_TO_RIAL);
        when(mock.exchangeRateFor(IRR,USD)).thenReturn(ONE_RIAL_TO_DOLLAR);
        return mock;
    }
}

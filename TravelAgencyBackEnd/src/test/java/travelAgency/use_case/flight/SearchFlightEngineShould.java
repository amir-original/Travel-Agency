package travelAgency.use_case.flight;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.dao.api.ExchangeRateDAO;
import travelAgency.domain.flight.currency.Currency;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.currency.Money;
import travelAgency.services.currency_exchange.currency_api.ExchangeRateConverter;
import travelAgency.services.currency_exchange.currency_api.ExchangeRateServiceImpl;
import travelAgency.services.flight.FlightListService;
import travelAgency.services.flight.FlightListServiceImpl;
import travelAgency.use_case.fake.FakeFlight;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static travelAgency.domain.flight.currency.Currency.IRR;
import static travelAgency.domain.flight.currency.Currency.USD;
import static travelAgency.use_case.fake.FakeFlight.flight;
import static travelAgency.use_case.fake.FakeFlightPlanBuilder.flightPlan;

public class SearchFlightEngineShould {

    private static final double ONE_DOLLAR_TO_RIAL = 42700D;
    private static final Double ONE_RIAL_TO_DOLLAR = 0.000024;

    private FlightListService app;
    private ExchangeRateConverter exchangeRateConverter;

    @BeforeEach
    void setUp() {
        app = new FlightListServiceImpl(new FakeFlight());
        final ExchangeRateDAO exchangeRateDAO = mockExchangeRateDAO();
        exchangeRateConverter = new ExchangeRateConverter(new ExchangeRateServiceImpl(exchangeRateDAO));
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

        Money expectedMoney = new Money(4270000.00, IRR);

        assertAll(
                () -> assertThat(flights).contains(flight),
                () -> assertThat(exchangeRateConverter.convert(flight.price().amount(), USD, IRR)).isEqualTo(expectedMoney)
        );
    }

    @Test
    void converted_price_from_rial_to_dollar() {
        Money flightPrice = new Money(870000, IRR);

        final double amount = flightPrice.amount();

        final double convertedMoney = exchangeRateConverter.convert(amount, IRR, USD).amount();

        assertThat(convertedMoney).isEqualTo(20.88);
    }

    @Test
    void return_the_amount_itself_when_base_and_currency_is_the_same() {
        final int amount = 45;
        final Money convertedMoney = exchangeRateConverter.convert(amount, USD, USD);
        assertThat(convertedMoney.amount()).isEqualTo(amount);
        assertThat(convertedMoney.currency()).isEqualTo(USD);
    }

    @Test
    void throw_IllegalArgumentException_when_converted_negative_amount() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> exchangeRateConverter.convert(-500, USD,IRR));
    }


    @NotNull
    private ExchangeRateDAO mockExchangeRateDAO() {
        final ExchangeRateDAO mock = mock(ExchangeRateDAO.class);
        when(mock.getExchangeRate(USD,IRR)).thenReturn(ONE_DOLLAR_TO_RIAL);
        when(mock.getExchangeRate(IRR,USD)).thenReturn(ONE_RIAL_TO_DOLLAR);
        return mock;
    }
}

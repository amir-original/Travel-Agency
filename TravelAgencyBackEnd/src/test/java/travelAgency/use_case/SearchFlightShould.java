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

    private static final double ONE_DOLLAR_TO_RIAL = 42700D;
    private static final Double ONE_RIAL_TO_DOLLAR = 0.000024;

    private FindFlightService app;
    private CurrencyConverter currencyConverter;
    private FlightMapper flightMapper;

    @BeforeEach
    void setUp() {
        app = new FindFlight(new FakeFlight());
        final ExchangeRateDAO exchangeRateDAO = mockExchangeRateDAO();
        currencyConverter = new CurrencyConverter(new FindExchangeRate(exchangeRateDAO));
        flightMapper = new FlightMapper();
    }

    @Test
    void find_flights_with_searched_flight_plan() {
        FlightPlan flightPlan = flightPlan().build();
        final List<FlightDto> flights = app.searchFlights(flightMapper.toView(flightPlan));
        assertAll(
                () -> assertThat(flights).isNotEmpty(),
                () -> assertThat(flights.size()).isEqualTo(5)
        );
    }

    @Test
    void return_empty_when_no_flights_match_the_searched_criteria() {
        FlightPlan flightPlan = flightPlan().withNotExistLocation().build();
        final List<FlightDto> flights = app.searchFlights(flightMapper.toView(flightPlan));
        assertAll(
                () -> assertThat(flights).isEmpty(),
                () -> assertThat(flights.size()).isEqualTo(0)
        );
    }

    @Test
    void not_find_any_flight_when_location_is_null() {
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

    @Test
    void it_convert_an_amount_using_the_exchange_rate() {
        FlightPlan flightPlan = flightPlan().build();

        final List<FlightDto> flights = app.searchFlights(flightMapper.toView(flightPlan));

        final Flight flight = flight("0321");

        Money expectedMoney = Money.of(4270000.00, IRR);

        assertAll(
                //() -> assertThat(flights).contains(flight.flightNumber()),
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
    void not_converted_money_when_amount_is_negative() {
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

package travelAgency.infrastructure.libraries.currency_conversion;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.infrastructure.libraries.currency_converter.CurrencyConverter;
import travelAgency.infrastructure.libraries.currency_converter.ExchangeRateDAO;
import travelAgency.infrastructure.libraries.currency_converter.FindExchangeRate;
import travelAgency.model.flight.Flight;
import travelAgency.model.flight.FlightPlan;
import travelAgency.model.rate.Money;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static travelAgency.model.rate.Currency.IRR;
import static travelAgency.model.rate.Currency.USD;
import static travelAgency.use_case.fake.FakeFlight.flight;
import static travelAgency.use_case.fake.FakeFlightPlanBuilder.flightPlan;

public class CurrencyConverterShould {

    private static final double ONE_DOLLAR_TO_RIAL = 42700D;
    private static final Double ONE_RIAL_TO_DOLLAR = 0.000024;

    private CurrencyConverter currencyConverter;

    @BeforeEach
    void setUp() {
        final ExchangeRateDAO exchangeRateDAO = mockExchangeRateDAO();
        currencyConverter = new CurrencyConverter(new FindExchangeRate(exchangeRateDAO));
    }

    @Test
    void it_convert_an_amount_using_the_exchange_rate() {
        FlightPlan flightPlan = flightPlan().build();


        final Flight flight = flight("0321");

        Money expectedMoney = Money.of(4270000.00, IRR);
        assertThat(currencyConverter.convert(flight.price(), IRR)).isEqualTo(expectedMoney);
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

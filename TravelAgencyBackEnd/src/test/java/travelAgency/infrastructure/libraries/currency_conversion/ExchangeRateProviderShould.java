package travelAgency.infrastructure.libraries.currency_conversion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.infrastructure.libraries.currency_converter.ExchangeRateDAO;
import travelAgency.exceptions.CouldNotFoundExchangeRate;
import travelAgency.infrastructure.libraries.currency_converter.ExchangeRateProvider;
import travelAgency.infrastructure.libraries.currency_converter.FindExchangeRate;
import travelAgency.use_case.fake.FakeExchangeRate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static travelAgency.model.rate.currency.Currency.*;

public class ExchangeRateProviderShould {


    private ExchangeRateProvider exchangeRateProvider;

    @BeforeEach
    void setUp() {
        ExchangeRateDAO exchangeRateDAO = new FakeExchangeRate();
        exchangeRateProvider = new FindExchangeRate(exchangeRateDAO);
    }

    @Test
    void not_return_anything_when_currency_does_not_exist() {
        assertThatExceptionOfType(CouldNotFoundExchangeRate.class)
                .isThrownBy(() -> exchangeRateProvider.getRateFor(EUR,USD));
    }

    @Test
    void it_retrieves_the_currency_exchange_rate_without_throw_any_exception() {
        assertThat(exchangeRateProvider.getRateFor(USD,IRR)).isEqualTo(42419.016);

        assertThat(exchangeRateProvider.getRateFor(IRR,USD)).isEqualTo(0.000024);
    }
}

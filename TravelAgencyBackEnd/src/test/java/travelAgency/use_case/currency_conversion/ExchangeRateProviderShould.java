package travelAgency.use_case.currency_conversion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.application.exchange_rates.ExchangeRateDAO;
import travelAgency.exceptions.CouldNotFoundExchangeRate;
import travelAgency.application.exchange_rates.ExchangeRateProvider;
import travelAgency.application.exchange_rates.ExchangeRates;
import travelAgency.use_case.fake.FakeExchangeRate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static travelAgency.model.rate.currency.Currency.*;

public class ExchangeRateProviderShould {


    private ExchangeRateProvider exchangeRateProvider;

    @BeforeEach
    void setUp() {
        ExchangeRateDAO exchangeRateDAO = new FakeExchangeRate();
        exchangeRateProvider = new ExchangeRates(exchangeRateDAO);
    }

    @Test
    void throw_CouldNotFoundExchangeRate_when_currency_is_not_exist() {
        assertThatExceptionOfType(CouldNotFoundExchangeRate.class)
                .isThrownBy(() -> exchangeRateProvider.getRateFor(EUR,USD));
    }

    @Test
    void it_retrieves_the_currency_exchange_rate() {
        assertThat(exchangeRateProvider.getRateFor(USD,IRR)).isEqualTo(42419.016);

        assertThat(exchangeRateProvider.getRateFor(IRR,USD)).isEqualTo(0.000024);
    }
}

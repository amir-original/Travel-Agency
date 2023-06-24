package travelAgency.use_case.currency_conversion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.dao.api.ExchangeRateDAO;
import travelAgency.dao.api.CouldNotFoundExchangeRate;
import travelAgency.services.currency_conversion.ExchangeRateProvider;
import travelAgency.services.currency_conversion.ExchangeRates;
import travelAgency.use_case.fake.FakeExchangeRate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static travelAgency.domain.rate.currency.Currency.*;

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
    void get_exchange_rate_from_base_currency_to_target_currency() {
        assertThat(exchangeRateProvider.getRateFor(USD,IRR)).isEqualTo(42419.016);

        assertThat(exchangeRateProvider.getRateFor(IRR,USD)).isEqualTo(0.000024);
    }
}

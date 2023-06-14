package travelAgency.use_case.currency_conversion;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.dao.api.ExchangeRateDAO;
import travelAgency.dao.api.ExchangeRateDAOImpl;
import travelAgency.dao.api.ExchangeRateNotFoundException;
import travelAgency.dao.api.WebServiceConnectionFailureException;
import travelAgency.services.currency_conversion.ExchangeRateService;
import travelAgency.services.currency_conversion.ExchangeRateProvider;
import travelAgency.use_case.fake.FakeExchangeRate;

import static org.assertj.core.api.Assertions.assertThat;
import static travelAgency.domain.rate.currency.Currency.*;

public class ExchangeRateServiceShould {


    private ExchangeRateService exchangeRateService;

    @BeforeEach
    void setUp() {
        ExchangeRateDAO exchangeRateDAO = new FakeExchangeRate();
        exchangeRateService = new ExchangeRateProvider(exchangeRateDAO);
    }

    @Test
    void throw_ExchangeRateNotFoundException_when_currency_is_not_exist() {
        Assertions.assertThatExceptionOfType(ExchangeRateNotFoundException.class)
                .isThrownBy(() -> exchangeRateService.getRateFor(EUR,USD));
    }

    @Test
    void get_exchange_rate_from_base_currency_to_target_currency() {
        assertThat(exchangeRateService.getRateFor(USD,IRR)).isEqualTo(42419.016);

        assertThat(exchangeRateService.getRateFor(IRR,USD)).isEqualTo(0.000024);
    }
}

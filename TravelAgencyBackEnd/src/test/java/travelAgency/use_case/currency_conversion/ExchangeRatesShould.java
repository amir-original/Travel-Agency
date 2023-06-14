package travelAgency.use_case.currency_conversion;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.dao.api.ExchangeRateDAOImpl;
import travelAgency.dao.api.WebServiceConnectionFailureException;
import travelAgency.services.currency_conversion.ExchangeRateService;
import travelAgency.services.currency_conversion.ExchangeRateProvider;

import static org.assertj.core.api.Assertions.assertThat;
import static travelAgency.domain.rate.currency.Currency.IRR;
import static travelAgency.domain.rate.currency.Currency.USD;

public class ExchangeRatesShould {


    private ExchangeRateService exchangeRateService;

    @BeforeEach
    void setUp() {
        ExchangeRateDAOImpl exchangeRateDAO = new ExchangeRateDAOImpl();
        exchangeRateService = new ExchangeRateProvider(exchangeRateDAO);
    }

    @Test
    void throw_WebServiceConnectionFailureException_when_can_not_connect_to_server() {
        Assertions.assertThatExceptionOfType(WebServiceConnectionFailureException.class)
                .isThrownBy(() -> exchangeRateService.getExchangeRate(IRR));
    }

    @Test
    void connect_to_webservice_and_get_currency_rate() {
        assertThat(exchangeRateService.getRateFor(USD,IRR)).isEqualTo(42419.016);

        assertThat(exchangeRateService.getRateFor(IRR,USD)).isEqualTo(0.000024);
    }
}

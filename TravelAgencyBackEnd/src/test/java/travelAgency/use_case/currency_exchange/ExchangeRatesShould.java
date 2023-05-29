package travelAgency.use_case.currency_exchange;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.dao.api.ExchangeRateDAOImpl;
import travelAgency.dao.api.WebServiceConnectionFailureException;
import travelAgency.domain.flight.currency.Currency;
import travelAgency.services.currency_exchange.currency_api.ExchangeRateConverter;
import travelAgency.services.currency_exchange.currency_api.ExchangeRateService;
import travelAgency.services.currency_exchange.currency_api.ExchangeRateServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static travelAgency.domain.flight.currency.Currency.IRR;
import static travelAgency.domain.flight.currency.Currency.USD;

public class ExchangeRatesShould {


    private ExchangeRateService exchangeRateService;

    @BeforeEach
    void setUp() {
        ExchangeRateDAOImpl exchangeRateDAO = new ExchangeRateDAOImpl();
        exchangeRateService = new ExchangeRateServiceImpl(exchangeRateDAO);
    }

    @Test
    void throw_WebServiceConnectionFailureException_when_can_not_connect_to_server() {
        Assertions.assertThatExceptionOfType(WebServiceConnectionFailureException.class)
                .isThrownBy(() -> exchangeRateService.getExchangeRate(IRR));
    }

    @Test
    void connect_to_webservice_and_get_currency_rate() {
        assertThat(exchangeRateService.getRate(USD,IRR)).isEqualTo(427000D);

        assertThat(exchangeRateService.getRate(IRR,USD)).isEqualTo(0.000024);
    }


}

package travelAgency.use_case.currency_conversion;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.dao.api.ExchangeRateDAO;
import travelAgency.dao.api.ExchangeRateDAOImpl;
import travelAgency.dao.api.CouldNotConnectToExchangeRateWebService;
import travelAgency.services.currency_conversion.ExchangeRateProvider;
import travelAgency.services.currency_conversion.ExchangeRateService;

import static org.assertj.core.api.Assertions.assertThat;
import static travelAgency.domain.rate.currency.Currency.*;

public class ExchangeRateServiceRealWorldShould {

    private ExchangeRateService exchangeRateService;

    @BeforeEach
    void setUp() {
        ExchangeRateDAO exchangeRateDAO = new ExchangeRateDAOImpl();
        exchangeRateService = new ExchangeRateProvider(exchangeRateDAO);
    }

    @Test
    void throw_WebServiceConnectionFailureException_can_not_connect_to_webservice() {
        Assertions.assertThatExceptionOfType(CouldNotConnectToExchangeRateWebService.class)
                .isThrownBy(() -> exchangeRateService.getRateFor(EUR,USD));
    }

    @Test
    void connect_to_webservice_and_get_currency_rate() {
        assertThat(exchangeRateService.getRateFor(USD,IRR)).isEqualTo(42419.016);

        assertThat(exchangeRateService.getRateFor(IRR,USD)).isEqualTo(0.000024);
    }
}

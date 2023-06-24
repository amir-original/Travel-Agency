package travelAgency.use_case.currency_conversion;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import travelAgency.controller.ServiceContainer;
import travelAgency.dao.api.CouldNotConnectToExchangeRateWebService;
import travelAgency.dao.api.ExchangeRateDAO;
import travelAgency.dao.api.ExchangeRateApi;
import travelAgency.services.currency_conversion.ExchangeRates;
import travelAgency.services.currency_conversion.ExchangeRateProvider;

import static org.assertj.core.api.Assertions.assertThat;
import static travelAgency.domain.rate.currency.Currency.*;

public class ExchangeRateProviderRealWorldShould {

    private ExchangeRateProvider exchangeRateProvider;

    @BeforeEach
    void setUp() {
        final ServiceContainer serviceContainer = new ServiceContainer();
        final ExchangeRateDAO exchangeRateDAO = serviceContainer.getExchangeRateDAO();
        exchangeRateProvider = new ExchangeRates(exchangeRateDAO);
    }

    @Test
    void throw_WebServiceConnectionFailureException_can_not_connect_to_webservice() {
        Assertions.assertThatExceptionOfType(CouldNotConnectToExchangeRateWebService.class)
                .isThrownBy(() -> exchangeRateProvider.getRateFor(EUR,USD));
    }

    @Test
    @Disabled
    void connect_to_webservice_and_get_currency_rate() {
        assertThat(exchangeRateProvider.getRateFor(USD,IRR)).isEqualTo(42419.016);

        assertThat(exchangeRateProvider.getRateFor(IRR,USD)).isEqualTo(0.000024);
    }
}

package travelAgency.use_case.currency_conversion;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import travelAgency.infrastructure.ServiceContainer;
import travelAgency.application.exchange_rates.ExchangeRateDAO;
import travelAgency.exceptions.CouldNotConnectToExchangeRateWebService;
import travelAgency.application.exchange_rates.ExchangeRateProvider;
import travelAgency.application.exchange_rates.ExchangeRates;

import static org.assertj.core.api.Assertions.assertThat;
import static travelAgency.model.rate.currency.Currency.*;

// actual test
public class ExchangeRateProviderRealWorldShould {

    private ExchangeRateProvider exchangeRateProvider;

    @BeforeEach
    void setUp() {
        final ServiceContainer serviceContainer = new ServiceContainer();
        final ExchangeRateDAO exchangeRateDAO = serviceContainer.getExchangeRateDAO();
        exchangeRateProvider = new ExchangeRates(exchangeRateDAO);
    }

    @Test
    @Disabled
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

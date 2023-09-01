package travelAgency.infrastructure.libraries.currency_conversion;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import travelAgency.infrastructure.ServiceContainer;
import travelAgency.infrastructure.libraries.currency_converter.ExchangeRateDAO;
import travelAgency.infrastructure.user_interface.web.api.CouldNotConnectToExchangeRateWebService;
import travelAgency.infrastructure.libraries.currency_converter.ExchangeRateProvider;
import travelAgency.infrastructure.libraries.currency_converter.FindExchangeRate;

import static org.assertj.core.api.Assertions.assertThat;
import static travelAgency.model.rate.Currency.*;

// actual test
public class ExchangeRateProviderRealWorldShould {

    private ExchangeRateProvider exchangeRateProvider;

    @BeforeEach
    void setUp() {
        final ServiceContainer serviceContainer = new ServiceContainer();
        final ExchangeRateDAO exchangeRateDAO = serviceContainer.getExchangeRateDAO();
        exchangeRateProvider = new FindExchangeRate(exchangeRateDAO);
    }

    @Test
    @Disabled
    void not_return_no_rate_when_can_not_connect_to_webservice() {
        Assertions.assertThatExceptionOfType(CouldNotConnectToExchangeRateWebService.class)
                .isThrownBy(() -> exchangeRateProvider.getRateFor(EUR,USD));
    }

    @Test
    @Disabled
    void return_exchange_rate_without_throw_any_exception() {
        assertThat(exchangeRateProvider.getRateFor(USD,IRR)).isEqualTo(42419.016);

        assertThat(exchangeRateProvider.getRateFor(IRR,USD)).isEqualTo(0.000024);
    }
}

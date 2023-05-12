package travelAgency.use_case.currency_exchange;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.dao.api.ExchangeRateDAOImpl;
import travelAgency.dao.api.WebServiceConnectionFailureException;
import travelAgency.services.currency_exchange.currency_api.USDToIRRConverter;
import travelAgency.services.currency_exchange.currency_api.IRRToUSDConverter;

import static org.assertj.core.api.Assertions.assertThat;

public class ExchangeRatesShould {

    private USDToIRRConverter usdToIrr;
    private IRRToUSDConverter irrToUsd;


    @BeforeEach
    void setUp() {
        ExchangeRateDAOImpl exchangeRateDAO = new ExchangeRateDAOImpl();
        usdToIrr = new USDToIRRConverter(exchangeRateDAO);
        irrToUsd = new IRRToUSDConverter(exchangeRateDAO);
    }

    @Test
    void throw_WebServiceConnectionFailureException_when_can_not_connect_to_server() {
        Assertions.assertThatExceptionOfType(WebServiceConnectionFailureException.class)
                .isThrownBy(() -> usdToIrr.diffAmount());
    }

    @Test
    void connect_to_webservice_and_get_currency_rate() {
        assertThat(usdToIrr.diffAmount()).isEqualTo(427000D);

        AssertionsForClassTypes.assertThat(irrToUsd.diffAmount()).isEqualTo(0.000024);
    }


}

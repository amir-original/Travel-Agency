package travelAgency.use_case.priceConverter;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.dao.api.ExchangeRateDAOImpl;
import travelAgency.services.price_converter.currencyApi.USDToIRRConverter;
import travelAgency.services.price_converter.currencyApi.IRRToUSDConverter;

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
    void connect_to_webservice_and_get_currency_rate() {
        assertThat(usdToIrr.diffAmount()).isEqualTo(427000D);

        AssertionsForClassTypes.assertThat(irrToUsd.diffAmount()).isEqualTo(0.000024);
    }


}

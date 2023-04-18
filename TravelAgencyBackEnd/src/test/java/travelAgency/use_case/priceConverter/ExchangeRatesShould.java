package travelAgency.use_case.priceConverter;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import travelAgency.services.priceConverter.currencyApi.USDToIRRConverter;
import travelAgency.services.priceConverter.currencyApi.IRRToUSDConverter;

import static org.assertj.core.api.Assertions.assertThat;

public class ExchangeRatesShould {

    @Test
    void connect_to_webservice_and_get_currency_rate() {
        final USDToIRRConverter dollarToRial = new USDToIRRConverter();

        assertThat(dollarToRial.diffAmount()).isEqualTo(427000D);


        final IRRToUSDConverter rialToDollar = new IRRToUSDConverter();

        AssertionsForClassTypes.assertThat(rialToDollar.diffAmount()).isEqualTo(0.000024);
    }
}

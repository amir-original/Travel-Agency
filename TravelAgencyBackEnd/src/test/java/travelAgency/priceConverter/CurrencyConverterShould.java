package travelAgency.priceConverter;

import org.junit.jupiter.api.Test;
import travelAgency.services.priceConverter.currencyApi.CurrencyConverterApiService;
import travelAgency.services.priceConverter.currencyApi.DollarToRialConverterApi;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyConverterShould {

    @Test
    void connect_to_webservice_and_get_currency_rate() {
        final DollarToRialConverterApi dollarToRial = new DollarToRialConverterApi();
        assertThat(dollarToRial.diffAmount()).isEqualTo(427000D);

    }
}

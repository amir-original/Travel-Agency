package travelAgency.use_case.priceConverter;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import travelAgency.services.priceConverter.currencyApi.DollarToRialConverterApi;
import travelAgency.services.priceConverter.currencyApi.RialToDollarConverterApi;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyConverterShould {

    @Test
    void connect_to_webservice_and_get_currency_rate() {
        final DollarToRialConverterApi dollarToRial = new DollarToRialConverterApi();

        assertThat(dollarToRial.diffAmount()).isEqualTo(427000D);


        final RialToDollarConverterApi rialToDollar = new RialToDollarConverterApi();

        AssertionsForClassTypes.assertThat(rialToDollar.diffAmount()).isEqualTo(0.000024);
    }
}

package travelAgency.priceConverter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.priceConverter.exception.AmountNotNegativeException;
import travelAgency.service.priceConverter.CurrencyConverterService;
import travelAgency.service.priceConverter.CurrencyConverterServiceImpl;
import travelAgency.service.priceConverter.currencyApi.DollarToRialConverterApi;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class DollarToRialConverterShould {

    private CurrencyConverterService dollarToRialConverter;

    @BeforeEach
    void setUp() {
        dollarToRialConverter = new CurrencyConverterServiceImpl(new DollarToRialConverterApi());
    }

    @Test
    void convert_dollar_to_rial() {
        final int oneDollar = 1;
        final double rial = dollarToRialConverter.convert(oneDollar);

        assertThat(rial).isEqualTo(427000);
    }

    @Test
    void throw_AmountMustBePositiveException_when_enter_negative_amount() {
        assertThatExceptionOfType(AmountNotNegativeException.class)
                .isThrownBy(()-> dollarToRialConverter.convert(-1));
    }
}

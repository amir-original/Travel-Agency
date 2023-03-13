package travelAgency.priceConverter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.services.priceConverter.exception.AmountNotNegativeException;
import travelAgency.services.priceConverter.CurrencyConverterService;
import travelAgency.services.priceConverter.CurrencyConverterServiceImpl;
import travelAgency.services.priceConverter.currencyApi.CurrencyConverterApiService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DollarToRialConverterShould {

    private static final double ONE_DOLLAR_TO_RIAL = 427000D;
    private CurrencyConverterService dollarToRialConverter;
    private CurrencyConverterApiService converterApiService;

    @BeforeEach
    void setUp() {
        converterApiService = mock(CurrencyConverterApiService.class);
        dollarToRialConverter = new CurrencyConverterServiceImpl(converterApiService);
    }

    @Test
    void convert_dollar_to_rial() {
        final int oneDollar = 1;

        when(converterApiService.diffAmount()).thenReturn(ONE_DOLLAR_TO_RIAL);

       assertThat(dollarToRialConverter.convert(oneDollar)).isEqualTo(427000);
    }

    @Test
    void throw_AmountMustBePositiveException_when_enter_negative_amount() {
        assertThatExceptionOfType(AmountNotNegativeException.class)
                .isThrownBy(()-> dollarToRialConverter.convert(-1));
    }

}

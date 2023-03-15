package travelAgency.priceConverter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.services.priceConverter.currencyApi.RialToDollarConverterApi;
import travelAgency.services.priceConverter.exception.AmountNotNegativeException;
import travelAgency.services.priceConverter.CurrencyConverterService;
import travelAgency.services.priceConverter.CurrencyConverterServiceImpl;
import travelAgency.services.priceConverter.currencyApi.CurrencyConverterApiService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RialToDollarConverterShould {

    private static final double ONE_RIAL_TO_DOLLAR = 0.000024;
    private CurrencyConverterService rialToDollarService;
    private CurrencyConverterApiService converterApiService;

    @BeforeEach
    void setUp() {
        converterApiService = mock(RialToDollarConverterApi.class);
        rialToDollarService = new CurrencyConverterServiceImpl(converterApiService);
    }

    @Test
    void convert_rial_to_dollar() {
        final int oneThousandRial = 1000000;

        when(converterApiService.diffAmount()).thenReturn(ONE_RIAL_TO_DOLLAR);

        assertThat(rialToDollarService.convert(oneThousandRial)).isEqualTo(24);
    }

    @Test
    void throw_AmountMustBePositiveException_when_enter_negative_amount() {
        assertThatExceptionOfType(AmountNotNegativeException.class)
                .isThrownBy(()->rialToDollarService.convert(-1));
    }


}

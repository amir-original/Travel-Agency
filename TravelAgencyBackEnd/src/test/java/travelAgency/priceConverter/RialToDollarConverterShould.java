package travelAgency.priceConverter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.priceConverter.exception.AmountNotNegativeException;
import travelAgency.service.priceConverter.CurrencyConverterService;
import travelAgency.service.priceConverter.CurrencyConverterServiceImpl;
import travelAgency.service.priceConverter.currencyApi.CurrencyConverterApiService;
import travelAgency.service.priceConverter.currencyApi.RialToDollarConverterApi;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class RialToDollarConverterShould {

    private CurrencyConverterService rialToDollarService;

    @BeforeEach
    void setUp() {
        rialToDollarService = new CurrencyConverterServiceImpl(new RialToDollarConverterApiDouble());
    }

    @Test
    void convert_rial_to_dollar() {
        final int oneThousandRial = 1000000;
        final double dollar = rialToDollarService.convert(oneThousandRial);

        assertThat(dollar).isEqualTo(24);
    }

    @Test
    void throw_AmountMustBePositiveException_when_enter_negative_amount() {
        assertThatExceptionOfType(AmountNotNegativeException.class)
                .isThrownBy(()->rialToDollarService.convert(-1));
    }


    private static class RialToDollarConverterApiDouble implements CurrencyConverterApiService{

        @Override
        public double diffAmount() {
            return 0.000024;
        }
    }
}

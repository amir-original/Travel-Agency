package travelAgency.services.priceConverter;

import travelAgency.services.priceConverter.currencyApi.CurrencyConverterApiService;

public class CurrencyConverterServiceImpl implements CurrencyConverterService {

    private final CurrencyConverterApiService currencyConverterService;

    public CurrencyConverterServiceImpl(CurrencyConverterApiService currencyConverterService) {
        this.currencyConverterService = currencyConverterService;
    }

    @Override
    public double convert(double amount) {
        check(amount);

        return amount * currencyConverterService.diffAmount();
    }
}

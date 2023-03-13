package travelAgency.services.priceConverter;

import travelAgency.services.priceConverter.currencyApi.CurrencyConverterApiService;

public class CurrencyConverterServiceImpl implements CurrencyConverterService {

    private final CurrencyConverterApiService currencyApi;

    public CurrencyConverterServiceImpl(CurrencyConverterApiService currencyApi) {
        this.currencyApi = currencyApi;
    }

    @Override
    public double convert(double amount) {
        check(amount);

        return amount * currencyApi.diffAmount();
    }
}

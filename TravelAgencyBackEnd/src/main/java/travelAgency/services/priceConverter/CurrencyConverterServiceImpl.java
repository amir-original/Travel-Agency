package travelAgency.services.priceConverter;

import travelAgency.services.priceConverter.currencyApi.ExchangeRateService;
import travelAgency.services.priceConverter.exception.AmountNotNegativeException;

public class CurrencyConverterServiceImpl implements CurrencyConverterService {

    private final ExchangeRateService rateService;

    public CurrencyConverterServiceImpl(ExchangeRateService rateService) {
        this.rateService = rateService;
    }

    @Override
    public double convert(double amount) {
        check(amount);

        return amount * rateService.diffAmount();
    }

    @Override
    public String convertAndFormat(double amount) {
        return formatPrice(convert(amount));
    }

    private String formatPrice(double price) {
        return String.format("%,.2f", price);
    }

    private void check(double amount) {
        if (isNegative(amount)) throw new AmountNotNegativeException();
    }

    private boolean isNegative(double amount) {
        return amount < 0;
    }
}

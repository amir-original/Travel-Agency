package travelAgency.services.price_converter;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.flight.Money;
import travelAgency.services.price_converter.currencyApi.ExchangeRateService;

public class CurrencyConverterServiceImpl implements CurrencyConverterService {

    private final ExchangeRateService rateService;

    public CurrencyConverterServiceImpl(ExchangeRateService rateService) {
        this.rateService = rateService;
    }

    @Override
    public Money convert(Money money) {
        money.check();

        return hasSameBaseCurrency(money) ? getConvertMoney(money) : money;
    }

    @NotNull
    private Money getConvertMoney(Money money) {
        return new Money(money.amount() * rateService.diffAmount(),rateService.targetCurrency());
    }

    private boolean hasSameBaseCurrency(Money money) {
        return money.currency().equals(rateService.baseCurrency());
    }
}

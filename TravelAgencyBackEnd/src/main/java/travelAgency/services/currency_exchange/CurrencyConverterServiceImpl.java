package travelAgency.services.currency_exchange;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.flight.currency.Currency;
import travelAgency.domain.flight.currency.Money;
import travelAgency.services.currency_exchange.currency_api.ExchangeRateService;

public class CurrencyConverterServiceImpl implements CurrencyConverterService {

    private final ExchangeRateService rateService;

    public CurrencyConverterServiceImpl(ExchangeRateService rateService) {
        this.rateService = rateService;
    }

    @Override
    public Money convert(Money money) {
        money.check();

        return hasSameBaseCurrency(money) ? convertToTargetCurrency(money) : money;
    }

    @NotNull
    private Money convertToTargetCurrency(Money money) {
        final double convertedAmount = money.amount() * rateService.diffAmount();
        final Currency targetCurrency = rateService.targetCurrency();

        return new Money(convertedAmount, targetCurrency);
    }

    private boolean hasSameBaseCurrency(Money money) {
        return money.currency().equals(rateService.baseCurrency());
    }
}

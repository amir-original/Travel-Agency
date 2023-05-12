package travelAgency.services.currency_exchange;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.flight.currency.Currency;
import travelAgency.domain.flight.currency.Money;
import travelAgency.services.currency_exchange.currency_api.ExchangeRateService;

public class CurrencyConverter {

    private final ExchangeRateService rateService;

    public CurrencyConverter(ExchangeRateService rateService) {
        this.rateService = rateService;
    }

    public Money convert(Money money) {
        money.check();

        return hasSameBaseCurrency(money) ? convertToTargetCurrency(money) : money;
    }

    @NotNull
    private Money convertToTargetCurrency(Money money) {
        final double convertedAmount = money.amount() * rateService.diffAmount();

        return new Money(convertedAmount, rateService.targetCurrency());
    }

    private boolean hasSameBaseCurrency(Money money) {
        return money.currency().equals(rateService.baseCurrency());
    }
}

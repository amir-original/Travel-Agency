package travelAgency.infrastructure.libraries.currency_converter;

import org.jetbrains.annotations.NotNull;
import travelAgency.model.rate.Currency;
import travelAgency.model.flight.Money;

public final class CurrencyConverter {

    private final ExchangeRateProvider exchangeRateProvider;

    public CurrencyConverter(ExchangeRateProvider exchangeRateProvider) {
        this.exchangeRateProvider = exchangeRateProvider;
    }

    public Money convert(Money money, Currency to) {
        return hasSameCurrency(money.currency(), to) ? money
                : convertPrice(money, to);
    }

    @NotNull
    private Money convertPrice(Money money, Currency to) {
        final double rate = exchangeRateProvider.getRateFor(money.currency(), to);

        return money.convert(to,rate);
    }

    private boolean hasSameCurrency(Currency from, Currency to) {
        return from == to;
    }

}

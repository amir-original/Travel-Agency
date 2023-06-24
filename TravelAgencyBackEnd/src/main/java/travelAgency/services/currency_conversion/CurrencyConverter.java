package travelAgency.services.currency_conversion;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.rate.currency.Currency;
import travelAgency.domain.rate.currency.Money;

public class CurrencyConverter {

    private final ExchangeRateProvider exchangeRateProvider;

    public CurrencyConverter(ExchangeRateProvider exchangeRateProvider) {
        this.exchangeRateProvider = exchangeRateProvider;
    }

    public Money convert(Money money, Currency to) {
        return hasSameCurrency(money.currency(), to) ? money : convertPrice(money, to);
    }

    @NotNull
    private Money convertPrice(Money money, Currency to) {
        final double rate = exchangeRateProvider.getRateFor(money.currency(), to);

        final double convertedAmount = money.amount() * rate;
        return createMoney(convertedAmount, to);
    }

    private boolean hasSameCurrency(Currency from, Currency to) {
        return from == to;
    }

    private Money createMoney(double amount, Currency currency) {
        return Money.of(amount, currency);
    }
}

package travelAgency.services.currency_conversion;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.rate.currency.Currency;
import travelAgency.domain.rate.currency.Money;

public class CurrencyConverter {

    private final ExchangeRateService exchangeRateProvider;

    public CurrencyConverter(ExchangeRateService exchangeRateProvider) {
        this.exchangeRateProvider = exchangeRateProvider;
    }

    public Money convert(Money money, Currency targetCurrency) {
        return hasSameCurrency(money.currency(), targetCurrency) ? money :
                convertPrice(money, targetCurrency);
    }

    @NotNull
    private Money convertPrice(Money money, Currency targetCurrency) {
        final double rate = exchangeRateProvider
                .getRateFor(money.currency(), targetCurrency);

        final double convertedAmount = money.amount() * rate;
        return createMoney(convertedAmount, targetCurrency);
    }

    private boolean hasSameCurrency(Currency from, Currency to) {
        return from == to;
    }

    private Money createMoney(double amount, Currency currency) {
        return Money.of(amount, currency);
    }
}

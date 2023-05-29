package travelAgency.services.currency_exchange.currency_api;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.flight.currency.Currency;
import travelAgency.domain.flight.currency.Money;

public class ExchangeRateConverter {
    private final ExchangeRateService exchangeRateService;

    public ExchangeRateConverter(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    public Money convert(double amount, Currency from, Currency to) {
        canConvert(amount);

        return hasSameCurrency(from, to) ? createMoney(amount, to) :
                convertPrice(amount, from, to);
    }

    @NotNull
    private Money convertPrice(double amount, Currency from, Currency to) {
        final double diffAmount = exchangeRateService.getRate(from, to);
        final double convertedAmount = amount * diffAmount;
        return createMoney(convertedAmount, to);
    }

    private boolean hasSameCurrency(Currency from, Currency to) {
        return from == to;
    }

    private void canConvert(double amount) {
        if (amount < 0)
            throw new IllegalArgumentException("amount must be greater than zero!");
    }

    private Money createMoney(double amount, Currency currency) {
        return new Money(amount, currency);
    }
}

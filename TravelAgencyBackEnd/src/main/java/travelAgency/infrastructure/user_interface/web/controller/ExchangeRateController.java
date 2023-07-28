package travelAgency.infrastructure.user_interface.web.controller;

import travelAgency.model.rate.Currency;
import travelAgency.model.rate.Money;
import travelAgency.infrastructure.libraries.currency_converter.CurrencyConverter;

public class ExchangeRateController implements ExchangeRateOperations {

    private final CurrencyConverter currencyConverter;
    private final String[] currencies;

    public ExchangeRateController(CurrencyConverter currencyConverter) {
        this.currencyConverter = currencyConverter;
        this.currencies = Currency.currencies();
    }

    @Override
    public Money convert(double amount, String baseCurrency, String targetCurrency) {

        Money money = Money.of(amount, Currency.valueOf(baseCurrency));
        return currencyConverter.convert(money,Currency.valueOf(targetCurrency));
    }

    public String[] getCurrencies(){
        return currencies;
    }
}

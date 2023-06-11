package travelAgency.controller;

import travelAgency.domain.flight.currency.Currency;
import travelAgency.domain.flight.currency.Money;
import travelAgency.services.currency_conversion.CurrencyConverter;

public class ExchangeRateController {

    private final CurrencyConverter currencyConverter;
    private final String[] currencies;

    public ExchangeRateController(CurrencyConverter currencyConverter) {
        this.currencyConverter = currencyConverter;
        this.currencies = Currency.currencies();
    }

    public Money convert(Money money, Currency targetCurrency){
        return currencyConverter.convert(money,targetCurrency);
    }

    public String[] currencies(){
        return currencies;
    }
}

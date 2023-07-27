package travelAgency.infrastructure.user_interface.web.controller;

import travelAgency.model.rate.currency.Currency;
import travelAgency.model.rate.currency.Money;
import travelAgency.infrastructure.libraries.currency_converter.CurrencyConverter;

public class ExchangeRateController implements ExchangeRateOperations {

    private final CurrencyConverter currencyConverter;
    private final String[] currencies;

    public ExchangeRateController(CurrencyConverter currencyConverter) {
        this.currencyConverter = currencyConverter;
        this.currencies = Currency.currencies();
    }

    public Money convert(Money money, Currency targetCurrency){
        return currencyConverter.convert(money,targetCurrency);
    }

    public String[] getCurrencies(){
        return currencies;
    }
}

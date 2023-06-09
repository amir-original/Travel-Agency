package travelAgency.controller;

import travelAgency.domain.flight.currency.Currency;
import travelAgency.domain.flight.currency.Money;
import travelAgency.services.currency_conversion.CurrencyConverter;

import java.util.Arrays;

public class ExchangeRateController {

    private final CurrencyConverter currencyConverter;
    private final Currency[] currencies;

    public ExchangeRateController(CurrencyConverter currencyConverter) {
        this.currencyConverter = currencyConverter;
        this.currencies = Currency.values();
    }

    public Money convert(double amount, Currency from, Currency to){
        return currencyConverter.convert(amount,from,to);
    }

    public String[] currencies(){
        return Arrays.stream(currencies).map(Enum::name).filter(s -> !s.equals("CHF")).toArray(String[]::new);
    }
}

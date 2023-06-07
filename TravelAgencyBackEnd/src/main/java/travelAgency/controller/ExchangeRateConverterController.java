package travelAgency.controller;

import travelAgency.domain.flight.currency.Currency;
import travelAgency.domain.flight.currency.Money;
import travelAgency.services.currency_exchange.currency_api.ExchangeRateConverter;

import java.util.Arrays;
import java.util.List;

public class ExchangeRateConverterController {

    private final ExchangeRateConverter exchangeRateConverter;
    private final Currency[] currencies;

    public ExchangeRateConverterController(ExchangeRateConverter exchangeRateConverter) {
        this.exchangeRateConverter = exchangeRateConverter;
        this.currencies = Currency.values();
    }

    public Money convert(double amount, Currency from, Currency to){
        return exchangeRateConverter.convert(amount,from,to);
    }

    public String[] currencies(){
        return Arrays.stream(currencies).map(Enum::name).filter(s -> !s.equals("CHF")).toArray(String[]::new);
    }
}

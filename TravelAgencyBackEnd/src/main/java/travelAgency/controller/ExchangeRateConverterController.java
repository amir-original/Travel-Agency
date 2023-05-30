package travelAgency.controller;

import travelAgency.domain.flight.currency.Currency;
import travelAgency.domain.flight.currency.Money;
import travelAgency.services.currency_exchange.currency_api.ExchangeRateConverter;

public class ExchangeRateConverterController {

    private final ExchangeRateConverter exchangeRateConverter;

    public ExchangeRateConverterController(ExchangeRateConverter exchangeRateConverter) {
        this.exchangeRateConverter = exchangeRateConverter;
    }

    public Money convert(double amount, Currency from, Currency to){
        return exchangeRateConverter.convert(amount,from,to);
    }
}

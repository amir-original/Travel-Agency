package travelAgency.controller;

import travelAgency.domain.rate.currency.Currency;
import travelAgency.domain.rate.currency.Money;

public interface ExchangeRateOperations {

    Money convert(Money money, Currency targetCurrency);

    String[] getCurrencies();
}

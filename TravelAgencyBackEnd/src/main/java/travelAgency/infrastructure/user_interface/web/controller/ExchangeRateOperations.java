package travelAgency.infrastructure.user_interface.web.controller;

import travelAgency.model.rate.currency.Currency;
import travelAgency.model.rate.currency.Money;

public interface ExchangeRateOperations {

    Money convert(Money money, Currency targetCurrency);

    String[] getCurrencies();
}

package travelAgency.infrastructure.user_interface.web.controller;

import travelAgency.model.rate.Currency;
import travelAgency.model.rate.Money;

public interface ExchangeRateOperations {

    Money convert(double amount,String baseCurrency, String targetCurrency);

    String[] getCurrencies();
}

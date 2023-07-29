package travelAgency.infrastructure.user_interface.web.controller;

import travelAgency.model.flight.Money;

import java.util.List;

public interface ExchangeRateOperations {

    Money convert(double amount,String baseCurrency, String targetCurrency);

    List<String> getCurrencies();
}

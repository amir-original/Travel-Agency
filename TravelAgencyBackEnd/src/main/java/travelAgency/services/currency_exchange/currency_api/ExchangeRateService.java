package travelAgency.services.currency_exchange.currency_api;

import travelAgency.domain.flight.currency.Currency;

public interface ExchangeRateService {
    double diffAmount();
    Currency baseCurrency();
    Currency targetCurrency();
}

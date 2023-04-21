package travelAgency.services.price_converter.currencyApi;

import travelAgency.domain.flight.Currency;

public interface ExchangeRateService {
    double diffAmount();
    Currency baseCurrency();
    Currency targetCurrency();
}

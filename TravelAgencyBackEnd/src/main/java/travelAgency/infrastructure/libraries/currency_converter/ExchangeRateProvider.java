package travelAgency.infrastructure.libraries.currency_converter;

import travelAgency.model.rate.Currency;

public interface ExchangeRateProvider {
    double getRateFor(Currency from, Currency to);
}

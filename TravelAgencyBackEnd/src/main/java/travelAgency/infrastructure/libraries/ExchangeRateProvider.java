package travelAgency.infrastructure.libraries;

import travelAgency.model.rate.currency.Currency;

public interface ExchangeRateProvider {
    double getRateFor(Currency from, Currency to);
}

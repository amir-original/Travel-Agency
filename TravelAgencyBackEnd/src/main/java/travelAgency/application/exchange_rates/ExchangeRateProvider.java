package travelAgency.application.exchange_rates;

import travelAgency.model.rate.currency.Currency;

public interface ExchangeRateProvider {
    double getRateFor(Currency from, Currency to);
}

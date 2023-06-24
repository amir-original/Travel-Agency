package travelAgency.services.currency_conversion;

import travelAgency.domain.rate.currency.Currency;

public interface ExchangeRateProvider {
    double getRateFor(Currency from, Currency to);
}

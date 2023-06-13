package travelAgency.services.currency_conversion;

import travelAgency.domain.rate.ExchangeRate;
import travelAgency.domain.rate.currency.Currency;

public interface ExchangeRateService {
    ExchangeRate getExchangeRate(Currency currency);
    double getRateFor(Currency from, Currency to);
}

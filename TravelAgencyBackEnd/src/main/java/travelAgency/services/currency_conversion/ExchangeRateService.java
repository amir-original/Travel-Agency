package travelAgency.services.currency_conversion;

import travelAgency.dao.api.ExchangeRate;
import travelAgency.domain.flight.currency.Currency;

public interface ExchangeRateService {
    ExchangeRate getExchangeRate(Currency currency);
    double getRateFor(Currency from, Currency to);
}

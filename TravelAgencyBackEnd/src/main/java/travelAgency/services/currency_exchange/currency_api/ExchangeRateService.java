package travelAgency.services.currency_exchange.currency_api;

import travelAgency.dao.api.ExchangeRate;
import travelAgency.domain.flight.currency.Currency;

public interface ExchangeRateService {
    ExchangeRate getExchangeRate(Currency currency);
    double getRate(Currency from, Currency to);
}

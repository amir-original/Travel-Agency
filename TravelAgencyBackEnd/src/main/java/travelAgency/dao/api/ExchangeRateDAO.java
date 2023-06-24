package travelAgency.dao.api;

import travelAgency.domain.rate.ExchangeRate;
import travelAgency.domain.rate.currency.Currency;

import java.util.Optional;

public interface ExchangeRateDAO {
    Optional<ExchangeRate> retrieveExchangeRate(Currency by);
    double exchangeRateFor(Currency from, Currency to);
}

package travelAgency.dao.api;

import travelAgency.domain.rate.currency.Currency;
import travelAgency.domain.rate.ExchangeRate;

import java.util.Optional;

public interface ExchangeRateDAO {
    Optional<ExchangeRate> retrieveExchangeRate(Currency by);
    double getExchangeRate(Currency from,Currency to);
}

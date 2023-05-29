package travelAgency.dao.api;

import travelAgency.domain.flight.currency.Currency;

import java.util.Optional;

public interface ExchangeRateDAO {
    Optional<ExchangeRate> retrieveExchangeRate(Currency by);
    double getExchangeRate(Currency from,Currency to);
}

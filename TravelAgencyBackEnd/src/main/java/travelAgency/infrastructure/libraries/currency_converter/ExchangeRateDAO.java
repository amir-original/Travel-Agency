package travelAgency.infrastructure.libraries.currency_converter;

import travelAgency.model.rate.ExchangeRate;
import travelAgency.model.rate.Currency;

import java.util.Optional;

public interface ExchangeRateDAO {
    Optional<ExchangeRate> retrieveExchangeRate(Currency by);
    double exchangeRateFor(Currency from, Currency to);
}

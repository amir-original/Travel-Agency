package travelAgency.application.exchange_rates;

import travelAgency.model.rate.ExchangeRate;
import travelAgency.model.rate.currency.Currency;

import java.util.Optional;

public interface ExchangeRateDAO {
    Optional<ExchangeRate> retrieveExchangeRate(Currency by);
    double exchangeRateFor(Currency from, Currency to);
}

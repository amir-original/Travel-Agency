package com.dev.exchange_rate.repository;

import com.dev.exchange_rate.domain.Currency;
import com.dev.exchange_rate.domain.ExchangeRate;

import java.util.List;
import java.util.Optional;

public interface ExchangeRateRepository {
    List<ExchangeRate> retrieveExchangeRates();
    Optional<ExchangeRate> retrieveExchangeRate(Currency baseCurrency);

    void addExchangeRate(ExchangeRate exchangeRate);

    void remove(ExchangeRate exchangeRate);
}

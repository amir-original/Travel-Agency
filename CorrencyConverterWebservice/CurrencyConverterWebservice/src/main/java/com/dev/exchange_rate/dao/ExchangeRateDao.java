package com.dev.exchange_rate.dao;

import com.dev.exchange_rate.domain.Currency;
import com.dev.exchange_rate.domain.ExchangeRate;

import java.util.List;
import java.util.Optional;

public interface ExchangeRateDao {
    List<ExchangeRate> retrieveExchangeRates();
    Optional<ExchangeRate> retrieveExchangeRate(Currency baseCurrency);

}

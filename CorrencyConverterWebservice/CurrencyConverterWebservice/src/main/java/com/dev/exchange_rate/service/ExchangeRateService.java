package com.dev.exchange_rate.service;

import com.dev.exchange_rate.domain.Currency;
import com.dev.exchange_rate.domain.ExchangeRate;
import com.dev.exchange_rate.exceptions.ExchangeRateNotFoundException;

public interface ExchangeRateService {
    ExchangeRate retrieveExchangeRate(Currency baseCurrency) throws ExchangeRateNotFoundException;

    void addExchangeRate(ExchangeRate exchangeRate) ;
}

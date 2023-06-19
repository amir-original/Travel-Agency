package com.dev.exchange_rate.service;

import com.dev.exchange_rate.domain.Currency;
import com.dev.exchange_rate.dto.ExchangeRateDto;
import com.dev.exchange_rate.exceptions.ExchangeRateNotFoundException;

public interface ExchangeRateService {
    ExchangeRateDto retrieveExchangeRate(Currency baseCurrency) throws ExchangeRateNotFoundException;

    void addExchangeRate(ExchangeRateDto exchangeRateDto) ;
}

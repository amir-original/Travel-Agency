package com.dev.exchange_rate.service;

import com.dev.exchange_rate.domain.Currency;
import com.dev.exchange_rate.domain.ExchangeRate;
import com.dev.exchange_rate.repository.ExchangeRateRepository;
import com.dev.exchange_rate.exceptions.ExchangeRateNotFoundException;

public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateServiceImpl(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public ExchangeRate retrieveExchangeRate(Currency baseCurrency) throws ExchangeRateNotFoundException {
        return exchangeRateRepository.retrieveExchangeRate(baseCurrency)
                .orElseThrow(ExchangeRateNotFoundException::new);
    }



    @Override
    public void addExchangeRate(ExchangeRate exchangeRate) {
        exchangeRateRepository.addExchangeRate(exchangeRate);
    }
}

package com.dev.exchange_rate.service;

import com.dev.exchange_rate.domain.Currency;
import com.dev.exchange_rate.domain.ExchangeRate;
import com.dev.exchange_rate.dao.ExchangeRateDao;
import com.dev.exchange_rate.exceptions.ExchangeRateNotFoundException;

public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateDao exchangeRateDao;

    public ExchangeRateServiceImpl(ExchangeRateDao exchangeRateDao) {
        this.exchangeRateDao = exchangeRateDao;
    }

    @Override
    public ExchangeRate retrieveExchangeRate(Currency baseCurrency) throws ExchangeRateNotFoundException {
        return exchangeRateDao.retrieveExchangeRate(baseCurrency)
                .orElseThrow(ExchangeRateNotFoundException::new);
    }
}

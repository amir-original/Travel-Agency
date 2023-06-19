package com.dev.exchange_rate.controller;

import com.dev.exchange_rate.dto.ExchangeRateDto;
import com.dev.exchange_rate.dto.ExchangeRateMapper;
import com.dev.exchange_rate.exceptions.CurrencyNotFoundException;
import com.dev.exchange_rate.repository.ExchangeRateRepository;
import com.dev.exchange_rate.domain.Currency;
import com.dev.exchange_rate.domain.ExchangeRate;
import com.dev.exchange_rate.exceptions.ExchangeRateNotFoundException;
import com.dev.exchange_rate.service.ExchangeRateService;
import com.dev.exchange_rate.service.ExchangeRateServiceImpl;

public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    public ExchangeRateController(ExchangeRateRepository exchangeRateRepository) {
        exchangeRateService = new ExchangeRateServiceImpl(exchangeRateRepository);
    }

    public ExchangeRateDto retrieveExchangeRate(Currency currency)
            throws ExchangeRateNotFoundException,CurrencyNotFoundException {

        return exchangeRateService.retrieveExchangeRate(currency);
    }

    public void addExchangeRate(ExchangeRateDto exchangeRateDto) {
        exchangeRateService.addExchangeRate(exchangeRateDto);
    }
}

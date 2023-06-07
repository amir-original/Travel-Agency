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
    private final ExchangeRateMapper exchangeRateMapper;

    public ExchangeRateController(ExchangeRateRepository exchangeRateRepository) {
        exchangeRateService = new ExchangeRateServiceImpl(exchangeRateRepository);
        exchangeRateMapper = new ExchangeRateMapper();
    }

    public ExchangeRateDto retrieveExchangeRate(String currency)
            throws ExchangeRateNotFoundException,CurrencyNotFoundException {
        Currency baseCurrency = getCurrencyIfValid(currency);

        ExchangeRate exchangeRate = exchangeRateService.retrieveExchangeRate(baseCurrency);
        return exchangeRateMapper.toViewDto(exchangeRate);
    }

    private Currency getCurrencyIfValid(String currency) {
        if (currency.isBlank()) throw new IllegalArgumentException("Currency must not be null!");

        return getCurrencyIfExist(currency);
    }

    private Currency getCurrencyIfExist(String currency) {
        Currency baseCurrency;
        try {
            baseCurrency = Currency.valueOf(currency.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CurrencyNotFoundException("sorry! Currency not found! enter a valid currency");
        }
        return baseCurrency;
    }

    public void addExchangeRate(ExchangeRateDto exchangeRateDto) {
        exchangeRateService.addExchangeRate(exchangeRateMapper.toEntity(exchangeRateDto));
    }
}

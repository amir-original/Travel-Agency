package com.dev.exchange_rate.service;

import com.dev.exchange_rate.domain.Currency;
import com.dev.exchange_rate.domain.ExchangeRate;
import com.dev.exchange_rate.dto.ExchangeRateDto;
import com.dev.exchange_rate.dto.ExchangeRateMapper;
import com.dev.exchange_rate.repository.ExchangeRateRepository;
import com.dev.exchange_rate.exceptions.ExchangeRateNotFoundException;

public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final ExchangeRateMapper exchangeRateMapper;

    public ExchangeRateServiceImpl(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
        exchangeRateMapper = new ExchangeRateMapper();

    }

    @Override
    public ExchangeRateDto retrieveExchangeRate(Currency baseCurrency) throws ExchangeRateNotFoundException {
        ExchangeRate exchangeRate = exchangeRateRepository.retrieveExchangeRate(baseCurrency)
                .orElseThrow(ExchangeRateNotFoundException::new);
        return exchangeRateMapper.toViewDto(exchangeRate);
    }



    @Override
    public void addExchangeRate(ExchangeRateDto exchangeRateDto) {
        ExchangeRate exchangeRate = exchangeRateMapper.toEntity(exchangeRateDto);
        exchangeRateRepository.addExchangeRate(exchangeRate);
    }
}

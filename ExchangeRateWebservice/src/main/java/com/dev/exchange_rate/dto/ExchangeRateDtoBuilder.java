package com.dev.exchange_rate.dto;

import com.dev.exchange_rate.domain.Currency;

import java.time.LocalDate;
import java.util.Map;

public class ExchangeRateDtoBuilder {
    private Currency baseCurrency;
    private LocalDate date;
    private Map<Currency, Double> rates;

    public ExchangeRateDtoBuilder setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
        return this;
    }

    public ExchangeRateDtoBuilder setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public ExchangeRateDtoBuilder setRates(Map<Currency, Double> rates) {
        this.rates = rates;
        return this;
    }

    public ExchangeRateDto create() {
        return new ExchangeRateDto(baseCurrency, date, rates);
    }
}
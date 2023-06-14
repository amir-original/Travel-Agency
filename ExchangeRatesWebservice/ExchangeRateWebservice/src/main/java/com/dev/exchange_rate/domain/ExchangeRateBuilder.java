package com.dev.exchange_rate.domain;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class ExchangeRateBuilder {
    private Currency baseCurrency;
    private LocalDate date;
    private int id;
    private Map<Currency, Double> rates;

    public ExchangeRateBuilder() {
        this.rates = new LinkedHashMap<>();
    }

    public ExchangeRateBuilder setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
        return this;
    }

    public ExchangeRateBuilder setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public ExchangeRateBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public ExchangeRateBuilder setRates(Map<Currency, Double> rates) {
        this.rates = rates;
        return this;
    }

    public ExchangeRate createExchangeRate() {
        return ExchangeRate.create(id,baseCurrency, date,rates);
    }
}
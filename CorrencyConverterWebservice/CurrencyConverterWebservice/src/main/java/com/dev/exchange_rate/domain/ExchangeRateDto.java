package com.dev.exchange_rate.domain;

import com.dev.exchange_rate.helper.LocalDateDeserializer;
import com.dev.exchange_rate.helper.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;
import java.util.Map;

public class ExchangeRateDto {
    private Currency baseCurrency;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;
    private Map<Currency, Double> rates;

    public ExchangeRateDto() {
    }

    public ExchangeRateDto(Currency baseCurrency, LocalDate date, Map<Currency, Double> rates) {
        this.baseCurrency = baseCurrency;
        this.date = date;
        this.rates = rates;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public LocalDate getDate() {
        return date;
    }

    public Map<Currency, Double> getRates() {
        return rates;
    }
}

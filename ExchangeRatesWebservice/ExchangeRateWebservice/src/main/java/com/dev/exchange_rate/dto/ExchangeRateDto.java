package com.dev.exchange_rate.dto;

import com.dev.exchange_rate.domain.Currency;
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

    public ExchangeRateDto(Currency baseCurrency, LocalDate date, Map<Currency, Double> rates) {
        this.baseCurrency = baseCurrency;
        this.date = date;
        this.rates = rates;
    }

    public ExchangeRateDto(Currency baseCurrency, LocalDate date) {
        this.baseCurrency = baseCurrency;
        this.date = date;
    }

    public ExchangeRateDto() {
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public LocalDate getDate() {
        return date;
    }

    public Map<Currency, Double> getRates() {
        return rates;
    }
}

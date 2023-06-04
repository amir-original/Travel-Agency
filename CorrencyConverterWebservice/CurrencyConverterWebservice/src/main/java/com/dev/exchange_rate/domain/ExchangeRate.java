package com.dev.exchange_rate.domain;

import com.dev.exchange_rate.helper.LocalDateTimeDeserializer;
import com.dev.exchange_rate.helper.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;


public class ExchangeRate {

    private int id;
    private Currency baseCurrency;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDate date;

    private Map<Currency, Double> rates;


    public ExchangeRate(Currency baseCurrency, LocalDate date) {
        this.baseCurrency = baseCurrency;
        this.date = date;
        this.rates = new LinkedHashMap<>();
    }

    public ExchangeRate(int id, Currency baseCurrency, LocalDate date, Map<Currency, Double> rates) {
        this.id = id;
        this.baseCurrency = baseCurrency;
        this.date = date;
        this.rates = rates;
    }

    public int getId() {
        return id;
    }

    public ExchangeRate() {
    }

    public void addRate(Currency currency, double diffAmount) {
        rates.put(currency, diffAmount);
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public Map<Currency, Double> getRates() {
        return rates;
    }

    public double getRate(Currency currency) {
        return rates.get(currency);
    }

    public LocalDate getDate() {
        return date;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRate that = (ExchangeRate) o;
        return baseCurrency == that.baseCurrency && Objects.equals(date, that.date) && Objects.equals(rates, that.rates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseCurrency, date, rates);
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "baseCurrency=" + baseCurrency +
                ", date=" + date +
                ", rates=" + rates +
                '}';
    }

    public boolean hasSameBaseCurrency(Currency baseCurrency) {
        return this.baseCurrency.equals(baseCurrency);
    }
}

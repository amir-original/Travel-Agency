package com.dev.exchange_rate.domain;

import com.dev.exchange_rate.exceptions.NullBaseCurrencyException;
import com.dev.exchange_rate.exceptions.NullExchangeRateDateException;
import com.dev.exchange_rate.helper.LocalDateDeserializer;
import com.dev.exchange_rate.helper.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;


public class ExchangeRate {

    private int id;
    private Currency baseCurrency;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;
    private Map<Currency, Double> rates;


    public ExchangeRate(Currency baseCurrency, LocalDate date, Map<Currency, Double> rates) {
        if (baseCurrency == null) throw new NullBaseCurrencyException();
        this.baseCurrency = baseCurrency;

        if (date == null) throw new NullExchangeRateDateException();
        this.date = date;

        this.rates = rates;
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

}

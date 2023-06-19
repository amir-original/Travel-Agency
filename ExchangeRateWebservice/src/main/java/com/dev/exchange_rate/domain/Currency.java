package com.dev.exchange_rate.domain;

import com.dev.exchange_rate.exceptions.CurrencyNotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Currency {
    IRR("irr"),
    USD("usd"),
    EUR("eur"),
    CNY("cny");

    private final String key;

    Currency(String key) {
        this.key = key;
    }

    @JsonCreator
    public static Currency fromString(String value){
        for (Currency currency : Currency.values()) {
            if (currency.key.equalsIgnoreCase(value)){
                return currency;
            }
        }
        throw new CurrencyNotFoundException("Invalid Currency");
    }

    @JsonValue
    public String getValue(){
        return key.toUpperCase();
    }

    public String getKey() {
        return key;
    }
    @Override
    public String toString() {
        return super.toString();
    }
}

package com.dev.exchange_rate.domain;

import com.dev.exchange_rate.exceptions.CouldNotFoundCurrency;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
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
        throw CouldNotFoundCurrency.becauseItIsInvalid();
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

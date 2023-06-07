package com.dev.exchange_rate.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Currency {
    IRR,
    USD,
    EUR, CNY;

    @Override
    @JsonValue
    public String toString() {
        return super.toString();
    }
}

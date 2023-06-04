package com.dev.exchange_rate.exceptions;

public class CurrencyNotFoundException extends RuntimeException {
    public CurrencyNotFoundException(String s) {
        super(s);
    }
}

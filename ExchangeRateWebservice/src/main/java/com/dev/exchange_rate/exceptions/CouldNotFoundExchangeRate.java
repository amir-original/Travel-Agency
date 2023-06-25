package com.dev.exchange_rate.exceptions;

import com.dev.exchange_rate.domain.Currency;

public class CouldNotFoundExchangeRate extends RuntimeException {
    private CouldNotFoundExchangeRate(String message) {
        super(message);
    }

    public static CouldNotFoundExchangeRate withCurrency(Currency currency)
            throws CouldNotFoundCurrency
    {
        return new CouldNotFoundExchangeRate(getMessage(currency));
    }

    private static String getMessage(Currency value) {
        return String.format("Could not found exchange rate with [%s]", value);
    }
}

package com.dev.exchange_rate.exceptions;

public class CouldNotFoundCurrency extends RuntimeException {
    public CouldNotFoundCurrency(String s) {
        super(s);
    }

    public static CouldNotFoundCurrency becauseItIsInvalid()
            throws CouldNotFoundCurrency {
        return new CouldNotFoundCurrency("Could not found currency because it is invalid!");
    }
}

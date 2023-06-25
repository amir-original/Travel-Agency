package com.dev.exchange_rate.repository;

public class CouldNotStoreExchangeRate extends RuntimeException {
    private CouldNotStoreExchangeRate() {
    }

    private CouldNotStoreExchangeRate(String message) {
        super(message);
    }

    public static CouldNotStoreExchangeRate becauseItIsDuplicate()
    throws CouldNotStoreExchangeRate
    {
        return new CouldNotStoreExchangeRate("Could not store exchange rate because it is duplicate!");
    }
}

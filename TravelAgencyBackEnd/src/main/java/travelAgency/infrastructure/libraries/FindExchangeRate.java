package travelAgency.infrastructure.libraries;

import travelAgency.model.rate.currency.Currency;

public final class FindExchangeRate implements ExchangeRateProvider {

    private final ExchangeRateDAO exchangeRateDAO;

    public FindExchangeRate(ExchangeRateDAO exchangeRateDAO) {
        this.exchangeRateDAO = exchangeRateDAO;
    }

    @Override
    public double getRateFor(Currency from, Currency to) {
        return exchangeRateDAO.exchangeRateFor(from,to);
    }
}

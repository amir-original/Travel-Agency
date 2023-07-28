package travelAgency.infrastructure.libraries.currency_converter;

import travelAgency.model.rate.Currency;

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

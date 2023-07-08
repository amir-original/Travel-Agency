package travelAgency.application.exchange_rates;

import travelAgency.model.rate.currency.Currency;

public final class ExchangeRates implements ExchangeRateProvider {

    private final ExchangeRateDAO exchangeRateDAO;

    public ExchangeRates(ExchangeRateDAO exchangeRateDAO) {
        this.exchangeRateDAO = exchangeRateDAO;
    }

    @Override
    public double getRateFor(Currency from, Currency to) {
        return exchangeRateDAO.exchangeRateFor(from,to);
    }
}

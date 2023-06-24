package travelAgency.services.currency_conversion;

import travelAgency.dao.api.ExchangeRateDAO;
import travelAgency.domain.rate.currency.Currency;

public class ExchangeRates implements ExchangeRateProvider {

    private final ExchangeRateDAO exchangeRateDAO;

    public ExchangeRates(ExchangeRateDAO exchangeRateDAO) {
        this.exchangeRateDAO = exchangeRateDAO;
    }

    @Override
    public double getRateFor(Currency from, Currency to) {
        return exchangeRateDAO.exchangeRateFor(from,to);
    }
}

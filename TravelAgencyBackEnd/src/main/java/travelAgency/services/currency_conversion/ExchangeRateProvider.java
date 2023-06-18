package travelAgency.services.currency_conversion;

import travelAgency.domain.rate.ExchangeRate;
import travelAgency.dao.api.ExchangeRateDAO;
import travelAgency.dao.api.CouldNotFoundExchangeRate;
import travelAgency.domain.rate.currency.Currency;

public class ExchangeRateProvider implements ExchangeRateService {

    private final ExchangeRateDAO exchangeRateDAO;

    public ExchangeRateProvider(ExchangeRateDAO exchangeRateDAO) {
        this.exchangeRateDAO = exchangeRateDAO;
    }

    @Override
    public ExchangeRate getExchangeRate(Currency currency) {
        return exchangeRateDAO.retrieveExchangeRate(currency)
                .orElseThrow(CouldNotFoundExchangeRate::new);
    }

    @Override
    public double getRateFor(Currency from, Currency to) {
        return exchangeRateDAO.getExchangeRate(from,to);
    }
}

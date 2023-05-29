package travelAgency.services.currency_exchange.currency_api;

import travelAgency.dao.api.ExchangeRate;
import travelAgency.dao.api.ExchangeRateDAO;
import travelAgency.dao.api.ExchangeRateNotFoundException;
import travelAgency.domain.flight.currency.Currency;

public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateDAO exchangeRateDAO;

    public ExchangeRateServiceImpl(ExchangeRateDAO exchangeRateDAO) {
        this.exchangeRateDAO = exchangeRateDAO;
    }

    @Override
    public ExchangeRate getExchangeRate(Currency currency) {
        return exchangeRateDAO.retrieveExchangeRate(currency)
                .orElseThrow(ExchangeRateNotFoundException::new);
    }

    @Override
    public double getRate(Currency from, Currency to) {
        return exchangeRateDAO.getExchangeRate(from,to);
    }
}

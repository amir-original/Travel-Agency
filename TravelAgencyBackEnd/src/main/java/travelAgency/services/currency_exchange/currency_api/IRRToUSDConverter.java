package travelAgency.services.currency_exchange.currency_api;

import travelAgency.dao.api.ExchangeRateDAO;
import travelAgency.domain.flight.currency.Currency;

import static travelAgency.domain.flight.currency.Currency.IRR;
import static travelAgency.domain.flight.currency.Currency.USD;

public class IRRToUSDConverter implements ExchangeRateService {

    private final ExchangeRateDAO exchangeRateDAO;

    public IRRToUSDConverter(ExchangeRateDAO exchangeRateDAO) {
        this.exchangeRateDAO = exchangeRateDAO;
    }

    @Override
    public double diffAmount() {
        return exchangeRateDAO.irrToUsd();
    }

    @Override
    public Currency baseCurrency() {
        return IRR;
    }

    @Override
    public Currency targetCurrency() {
        return USD;
    }
}

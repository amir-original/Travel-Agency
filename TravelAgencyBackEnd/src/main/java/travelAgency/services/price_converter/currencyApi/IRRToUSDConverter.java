package travelAgency.services.price_converter.currencyApi;

import travelAgency.dao.api.ExchangeRateDAO;
import travelAgency.domain.flight.Currency;

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
        return Currency.IRR;
    }

    @Override
    public Currency targetCurrency() {
        return Currency.USD;
    }
}

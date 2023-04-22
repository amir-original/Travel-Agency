package travelAgency.services.currency_exchange.currency_api;

import travelAgency.dao.api.ExchangeRateDAO;
import travelAgency.domain.flight.currency.Currency;

import static travelAgency.domain.flight.currency.Currency.IRR;
import static travelAgency.domain.flight.currency.Currency.USD;

public class USDToIRRConverter implements ExchangeRateService {

    private final ExchangeRateDAO exchangeRateDAO;

    public USDToIRRConverter(ExchangeRateDAO exchangeRateDAO) {
        this.exchangeRateDAO = exchangeRateDAO;
    }

    @Override
    public double diffAmount() {
        return exchangeRateDAO.usdToIrr();
    }

    @Override
    public Currency baseCurrency() {
        return USD;
    }

    @Override
    public Currency targetCurrency() {
        return IRR;
    }
}

package travelAgency.services.price_converter.currencyApi;

import travelAgency.dao.api.ExchangeRateDAO;
import travelAgency.domain.flight.Currency;

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
        return Currency.USD;
    }

    @Override
    public Currency targetCurrency() {
        return Currency.IRR;
    }
}

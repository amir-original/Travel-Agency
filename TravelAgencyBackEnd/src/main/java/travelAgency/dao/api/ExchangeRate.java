package travelAgency.dao.api;

import travelAgency.domain.flight.currency.Currency;

import java.time.LocalDate;
import java.util.Map;

public class ExchangeRate {

    private final Currency baseCurrency;
    private final LocalDate date;
    private final Map<Currency,Double> rates;

    public ExchangeRate(Currency baseCurrency, LocalDate date, Map<Currency, Double> rates) {
        this.baseCurrency = baseCurrency;
        this.date = date;
        this.rates = rates;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public LocalDate getDate() {
        return date;
    }

    public Map<Currency, Double> getRates() {
        return rates;
    }

    public double getRate(Currency to) {
        return rates.get(to);
    }
}

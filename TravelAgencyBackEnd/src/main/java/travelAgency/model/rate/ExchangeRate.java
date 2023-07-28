package travelAgency.model.rate;


import java.time.LocalDate;
import java.util.Map;

public final class ExchangeRate {

    private final Currency baseCurrency;
    private final LocalDate date;
    private final Map<Currency,Double> rates;

    public ExchangeRate(Currency baseCurrency, LocalDate date, Map<Currency, Double> rates) {
        this.baseCurrency = baseCurrency;
        this.date = date;
        this.rates = rates;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getRate(Currency to) {
        return rates.get(to);
    }
}
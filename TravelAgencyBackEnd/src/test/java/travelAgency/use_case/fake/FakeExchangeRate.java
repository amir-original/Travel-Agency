package travelAgency.use_case.fake;

import travelAgency.application.exchange_rates.ExchangeRateDAO;
import travelAgency.exceptions.CouldNotFoundExchangeRate;
import travelAgency.model.rate.ExchangeRate;
import travelAgency.model.rate.currency.Currency;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static travelAgency.model.rate.currency.Currency.IRR;
import static travelAgency.model.rate.currency.Currency.USD;

public class FakeExchangeRate implements ExchangeRateDAO {

    private final Map<Currency,ExchangeRate> rates;

    public FakeExchangeRate() {
        this.rates = new LinkedHashMap<>();
        final Map<Currency, Double> usd = Map.of(USD, 0.000024);
        final LocalDate date = LocalDate.of(2023, 2, 16);
        rates.put(IRR, new ExchangeRate(IRR, date,usd));
        final Map<Currency, Double> irr = Map.of(IRR, 42419.016);
        rates.put(USD, new ExchangeRate(USD,date,irr));
    }


    public Optional<ExchangeRate> retrieveExchangeRate(Currency by) {
        return Optional.ofNullable(rates.get(by));
    }

    @Override
    public double exchangeRateFor(Currency from, Currency to) {
        return retrieveExchangeRate(from)
                .orElseThrow(CouldNotFoundExchangeRate::new)
                .getRate(to);
    }
}

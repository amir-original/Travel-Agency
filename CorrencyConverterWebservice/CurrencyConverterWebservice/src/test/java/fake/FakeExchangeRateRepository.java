package fake;

import com.dev.exchange_rate.domain.ExchangeRateBuilder;
import com.dev.exchange_rate.repository.ExchangeRateRepository;
import com.dev.exchange_rate.domain.Currency;
import com.dev.exchange_rate.domain.ExchangeRate;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FakeExchangeRateRepository implements ExchangeRateRepository {

    private Map<Currency,ExchangeRate> rateMap;

    public FakeExchangeRateRepository() {
        createFakeExchangeRate();
    }

    @Override
    public List<ExchangeRate> retrieveExchangeRates() {
        return rateMap.values().stream().toList();
    }

    @Override
    public Optional<ExchangeRate> retrieveExchangeRate(Currency baseCurrency) {
        return Optional.ofNullable(rateMap.get(baseCurrency));
    }

    @Override
    public void addExchangeRate(ExchangeRate exchangeRate) {
        rateMap.put(exchangeRate.getBaseCurrency(),exchangeRate);
    }

    @Override
    public void remove(ExchangeRate exchangeRate) {
        rateMap.remove(exchangeRate.getBaseCurrency());
    }

    private void createFakeExchangeRate() {
        rateMap = new LinkedHashMap<>();
        LocalDate of = LocalDate.of(2023, 5, 14);
        ExchangeRate exchangeRate = new ExchangeRateBuilder().setBaseCurrency(Currency.USD).setDate(of).createExchangeRate();
        exchangeRate.addRate(Currency.IRR,42419.016);
        exchangeRate.addRate(Currency.EUR,0.92009013);
        rateMap.put(exchangeRate.getBaseCurrency(),exchangeRate);

        ExchangeRate irrRate = new ExchangeRateBuilder().setBaseCurrency(Currency.IRR).setDate(of).createExchangeRate();
        irrRate.addRate(Currency.USD,0.000024);
        irrRate.addRate(Currency.EUR,0.000022);
        rateMap.put(irrRate.getBaseCurrency(),irrRate);

        ExchangeRate eurRate = new ExchangeRateBuilder().setBaseCurrency(Currency.EUR).setDate(of).createExchangeRate();
        eurRate.addRate(Currency.IRR,45364.64);
        eurRate.addRate(Currency.USD,1.07);

        rateMap.put(eurRate.getBaseCurrency(),eurRate);
    }
}

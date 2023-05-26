package fake;

import com.dev.exchange_rate.dao.ExchangeRateDao;
import com.dev.exchange_rate.domain.Currency;
import com.dev.exchange_rate.domain.ExchangeRate;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FakeJsonExchangeRateDao implements ExchangeRateDao {

    private Map<Currency,ExchangeRate> rateMap;

    public FakeJsonExchangeRateDao() {
        rateMap = new LinkedHashMap<>();
        LocalDate of = LocalDate.of(2023, 5, 14);
        ExchangeRate exchangeRate = new ExchangeRate(Currency.USD, of);
        exchangeRate.addRate(Currency.IRR,42419.016);
        exchangeRate.addRate(Currency.EUR,0.92009013);
        rateMap.put(exchangeRate.getBaseCurrency(),exchangeRate);
    }

    @Override
    public List<ExchangeRate> retrieveExchangeRates() {
        return rateMap.values().stream().toList();
    }

    @Override
    public Optional<ExchangeRate> retrieveExchangeRate(Currency baseCurrency) {
        return Optional.ofNullable(rateMap.get(baseCurrency));
    }
}

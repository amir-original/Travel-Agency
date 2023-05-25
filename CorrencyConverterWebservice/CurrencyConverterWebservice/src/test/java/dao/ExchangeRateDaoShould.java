package dao;

import com.dev.exchange_rate.dao.JsonExchangeRateDao;
import com.dev.exchange_rate.domain.Currency;
import com.dev.exchange_rate.domain.ExchangeRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ExchangeRateDaoShould {

    private JsonExchangeRateDao exchangeRateDao;

    @BeforeEach
    void setUp() {
        exchangeRateDao = new JsonExchangeRateDao();
    }

    @Test
    void find_currency_by_base_name() {

        Optional<ExchangeRate> exchangeRate = exchangeRateDao.retrieveExchangeRate(Currency.USD);

        assertThat(exchangeRate).isNotEmpty();
        assertThat(exchangeRate.get().getBaseCurrency()).isEqualTo(Currency.USD);
    }

    @Test
    void get_irr_currency_rates() {
        Optional<ExchangeRate> exchangeRate = exchangeRateDao.retrieveExchangeRate(Currency.IRR);

        assertThat(exchangeRate).isNotEmpty();
        assertThat(exchangeRate.get().getBaseCurrency()).isEqualTo(Currency.IRR);
    }
}

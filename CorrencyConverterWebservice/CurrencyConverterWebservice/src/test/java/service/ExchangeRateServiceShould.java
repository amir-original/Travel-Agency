package service;

import com.dev.exchange_rate.domain.Currency;
import com.dev.exchange_rate.domain.ExchangeRate;
import com.dev.exchange_rate.exceptions.ExchangeRateNotFoundException;
import com.dev.exchange_rate.service.ExchangeRateService;
import com.dev.exchange_rate.service.ExchangeRateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import fake.FakeJsonExchangeRateDao;

import static org.assertj.core.api.Assertions.*;

public class ExchangeRateServiceShould {

    private ExchangeRateService exchangeRateService;

    @BeforeEach
    void setUp() {
        exchangeRateService = new ExchangeRateServiceImpl(new FakeJsonExchangeRateDao());
    }

    @Test
    void retrieve_base_currency_without_throw_any_exception() {
        assertThatNoException().isThrownBy(()-> exchangeRateService.retrieveExchangeRate(Currency.USD));
    }

    @Test
    void throw_ExchangeRateNotFoundException_when_exchange_rate_not_found() {
        assertThatExceptionOfType(ExchangeRateNotFoundException.class)
                .isThrownBy(()->exchangeRateService.retrieveExchangeRate(Currency.EUR));
    }

    @Test
    void retrieve_exchange_rate_by_base_currency() {
        assertThatNoException().isThrownBy(()-> {
            ExchangeRate exchangeRate = exchangeRateService.retrieveExchangeRate(Currency.USD);
            assertThat(exchangeRate.getBaseCurrency()).isEqualTo(Currency.USD);
        });
    }
}

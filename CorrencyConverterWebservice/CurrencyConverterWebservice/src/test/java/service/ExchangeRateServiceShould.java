package service;

import com.dev.exchange_rate.domain.Currency;
import com.dev.exchange_rate.domain.ExchangeRate;
import com.dev.exchange_rate.exceptions.ExchangeRateNotFoundException;
import com.dev.exchange_rate.service.ExchangeRateService;
import com.dev.exchange_rate.service.ExchangeRateServiceImpl;
import fake.FakeExchangeRateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class ExchangeRateServiceShould {

    private ExchangeRateService exchangeRateService;

    @BeforeEach
    void setUp() {
        exchangeRateService = new ExchangeRateServiceImpl(new FakeExchangeRateRepository());
    }

    @Test
    void retrieve_base_currency_without_throw_any_exception() {
        assertThatNoException().isThrownBy(() -> exchangeRateService.retrieveExchangeRate(Currency.USD));
    }

    @Test
    void throw_ExchangeRateNotFoundException_when_exchange_rate_not_found() {
        assertThatExceptionOfType(ExchangeRateNotFoundException.class)
                .isThrownBy(() -> exchangeRateService.retrieveExchangeRate(Currency.CNY));
    }

    @Test
    void retrieve_exchange_rate_by_base_currency() {
        assertThatNoException().isThrownBy(() -> {
            ExchangeRate exchangeRate = exchangeRateService.retrieveExchangeRate(Currency.USD);
            assertThat(exchangeRate.getBaseCurrency()).isEqualTo(Currency.USD);
        });
    }

    @Test
    void add_new_exchange_rate() {
        ExchangeRate exchangeRate = getExchangeRate();

        assertThatNoException().isThrownBy(() -> exchangeRateService.addExchangeRate(exchangeRate));
        assertThatNoException().isThrownBy(() -> {
            ExchangeRate fetchedExchangeRate = exchangeRateService.retrieveExchangeRate(Currency.CNY);
            assertThat(fetchedExchangeRate.getBaseCurrency()).isEqualTo(Currency.CNY);
        });
    }

    private static ExchangeRate getExchangeRate() {
        LocalDate date = LocalDate.of(2023, 4, 5);
        Map<Currency, Double> rates = Map.of(
                Currency.USD, 0.14,
                Currency.EUR, 0.13,
                Currency.IRR, 5970.44);
        return new ExchangeRate(Currency.CNY, date, rates);
    }
}

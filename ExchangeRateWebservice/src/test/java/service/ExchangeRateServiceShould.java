package service;

import com.dev.exchange_rate.domain.Currency;
import com.dev.exchange_rate.dto.ExchangeRateDto;
import com.dev.exchange_rate.dto.ExchangeRateDtoBuilder;
import com.dev.exchange_rate.exceptions.ExchangeRateNotFoundException;
import com.dev.exchange_rate.exceptions.NullBaseCurrencyException;
import com.dev.exchange_rate.exceptions.NullExchangeRateDateException;
import com.dev.exchange_rate.service.ExchangeRateService;
import com.dev.exchange_rate.service.ExchangeRateServiceImpl;
import fake.FakeExchangeRateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.*;

public class ExchangeRateServiceShould {

    private ExchangeRateService exchangeRateService;
    private ExchangeRateDtoBuilder rateDtoBuilder;

    @BeforeEach
    void setUp() {
        exchangeRateService = new ExchangeRateServiceImpl(new FakeExchangeRateRepository());
        rateDtoBuilder = new ExchangeRateDtoBuilder();
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
            ExchangeRateDto exchangeRate = exchangeRateService.retrieveExchangeRate(Currency.USD);
            assertThat(exchangeRate.getBaseCurrency()).isEqualTo(Currency.USD);
        });
    }

    @Test
    void add_new_exchange_rate_without_throw_any_exception() {
        ExchangeRateDto exchangeRate = getExchangeRate();

        assertThatNoException().isThrownBy(() -> exchangeRateService.addExchangeRate(exchangeRate));
        assertThatNoException().isThrownBy(() -> {
            ExchangeRateDto fetchedExchangeRate = exchangeRateService.retrieveExchangeRate(Currency.CNY);
            assertThat(fetchedExchangeRate.getBaseCurrency()).isEqualTo(Currency.CNY);
        });
    }

    @Test
    void throw_NullBaseCurrencyException_when_base_currency_is_null() {
        ExchangeRateDto exchangeRateDto = rateDtoBuilder
                .setBaseCurrency(null)
                .setDate(of(2012, 1, 5)).create();

        assertThatExceptionOfType(NullBaseCurrencyException.class)
                .isThrownBy(()-> exchangeRateService.addExchangeRate(exchangeRateDto));
    }

    @Test
    void throw_NullExchangeRateException_when_exchange_rate_date_is_null() {
        ExchangeRateDto exchangeRateDto = rateDtoBuilder
                .setBaseCurrency(Currency.IRR)
                .setDate(null).create();

        assertThatExceptionOfType(NullExchangeRateDateException.class)
                .isThrownBy(()-> exchangeRateService.addExchangeRate(exchangeRateDto));
    }

    private static ExchangeRateDto getExchangeRate() {
        LocalDate date = of(2023, 4, 5);
        Map<Currency, Double> rates = Map.of(
                Currency.USD, 0.14,
                Currency.EUR, 0.13,
                Currency.IRR, 5970.44);
        return new ExchangeRateDto(Currency.CNY, date, rates);
    }
}

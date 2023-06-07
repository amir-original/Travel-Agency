package dao;

import com.dev.exchange_rate.domain.ExchangeRateBuilder;
import com.dev.exchange_rate.repository.*;
import com.dev.exchange_rate.domain.Currency;
import com.dev.exchange_rate.domain.ExchangeRate;
import com.dev.exchange_rate.helper.file_reader.PropertiesReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class MySQLExchangeRateRepositoryShould {
    private ExchangeRateRepository exchangeRateRepository;

    @BeforeEach
    void setUp() {
        PropertiesReader propertiesReader = new PropertiesReader("fake_db_config.properties");
        ConnectionConfigurationImpl configuration = new ConnectionConfigurationImpl(propertiesReader);
        MySQLConnectionGateway connection = new MySQLConnectionGateway(configuration);
        exchangeRateRepository = new MySQLExchangeRateRepository(connection);
    }


    @Test
    void add_new_exchange_rate_in_db() {
        ExchangeRate exchangeRate = addNewExchangeRateInDb();

        List<ExchangeRate> exchangeRates = exchangeRateRepository.retrieveExchangeRates();

        assertThat(exchangeRates.size()).isEqualTo(1);
        exchangeRateRepository.remove(exchangeRate);
    }

    @Test
    void find_currency_by_base_name() {
        ExchangeRate rate = addNewExchangeRateInDb();

        Optional<ExchangeRate> exchangeRate = exchangeRateRepository.retrieveExchangeRate(Currency.EUR);

        assertThat(exchangeRate).isNotEmpty();
        assertThat(exchangeRate.get().getBaseCurrency()).isEqualTo(Currency.EUR);
        exchangeRateRepository.remove(rate);
    }

    private ExchangeRate addNewExchangeRateInDb() {
        ExchangeRate exchangeRate = new ExchangeRateBuilder().setBaseCurrency(Currency.EUR).setDate(LocalDate.now()).createExchangeRate();
        exchangeRate.addRate(Currency.IRR,45382.27);
        exchangeRate.addRate(Currency.USD,1.07);
        exchangeRateRepository.addExchangeRate(exchangeRate);
        return exchangeRate;
    }
}

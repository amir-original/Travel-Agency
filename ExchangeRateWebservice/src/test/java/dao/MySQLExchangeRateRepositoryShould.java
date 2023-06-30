package dao;

import com.dev.exchange_rate.domain.ExchangeRateBuilder;
import com.dev.exchange_rate.repository.*;
import com.dev.exchange_rate.domain.Currency;
import com.dev.exchange_rate.domain.ExchangeRate;
import com.dev.exchange_rate.helper.file_reader.PropertiesReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;

@Disabled
// TODO this test doesn't work with h2
public class MySQLExchangeRateRepositoryShould {
    private ExchangeRateRepository exchangeRateRepository;

    @BeforeEach
    void setUp() {
        PropertiesReader propertiesReader = new PropertiesReader("h2_db_config.properties");
        ConnectionConfigurationImpl configuration = new ConnectionConfigurationImpl(propertiesReader);
        MySQLConnectionGateway connection = new MySQLConnectionGateway(configuration);
        exchangeRateRepository = new MySQLExchangeRateRepository(connection);
    }


    @Test
    void add_new_exchange_rate_in_db() {
        addNewExchangeRateInDb();

        Optional<ExchangeRate> fetchedExchangeRate = exchangeRateRepository.retrieveExchangeRate(Currency.EUR);

        assertThat(fetchedExchangeRate.isPresent()).isTrue();
        exchangeRateRepository.remove(fetchedExchangeRate.get());
    }

    @Test
    void find_currency_by_base_name() {
       addNewExchangeRateInDb();

        Optional<ExchangeRate> exchangeRate = exchangeRateRepository.retrieveExchangeRate(Currency.EUR);

        assertThat(exchangeRate.isPresent()).isTrue();
        assertThat(exchangeRate.get().getBaseCurrency()).isEqualTo(Currency.EUR);
        assertThat(exchangeRate.get().getDate()).isEqualTo(of(2023, 4, 6));
        exchangeRateRepository.remove(exchangeRate.get());
    }

    private ExchangeRate addNewExchangeRateInDb() {
        LocalDate date = of(2023, 4, 6);
        ExchangeRate exchangeRate = new ExchangeRateBuilder()
                .setBaseCurrency(Currency.EUR).setDate(date)
                .createExchangeRate();

        exchangeRate.addRate(Currency.IRR,45382.27);
        exchangeRate.addRate(Currency.USD,1.07);
        exchangeRateRepository.addExchangeRate(exchangeRate);
        return exchangeRate;
    }


}

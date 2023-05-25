package rest;

import com.dev.exchange_rate.domain.Currency;
import com.dev.exchange_rate.domain.ExchangeRate;
import com.dev.exchange_rate.helper.HttpApiClient;
import com.dev.exchange_rate.helper.HttpHandlerApiClient;
import com.dev.exchange_rate.helper.file_reader.LocalDateTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyConverterApiTest {

    private static final String BASE_URL = "http://localhost:16162/CurrencyConverterWebservice-1.0/api/base_currency/";
    private HttpApiClient httpApiClient;

    @BeforeEach
    void setUp() {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter()).create();
        httpApiClient = new HttpHandlerApiClient(gson);
    }

    @Test
    void send_get_request_to_fetch_exchange_rates() {
        var exchangeRate = httpApiClient
                .target(BASE_URL + "IRR")
                .GET(ExchangeRate.class);

        assertThat(exchangeRate.getBaseCurrency()).isEqualTo(Currency.IRR);
    }
}

package rest;

import com.dev.exchange_rate.domain.*;
import com.dev.exchange_rate.dto.ExchangeRateDto;
import com.dev.exchange_rate.dto.ExchangeRateDtoBuilder;
import com.dev.exchange_rate.helper.HttpApiClient;
import com.dev.exchange_rate.helper.HttpHandlerApiClient;
import com.dev.exchange_rate.helper.file_reader.LocalDateTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ExchangeRateApiTest {

    private static final String BASE_URL
            = "http://localhost:8080/ExchangeRatesWebservice-1.0/api/base_currency/";
    private HttpApiClient client;
    private Gson gson;

    @BeforeEach
    void setUp() {
        this.gson = createGson();
        client = new HttpHandlerApiClient(gson);
    }

    private Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .setPrettyPrinting().create();
    }

    @Test
    @Disabled
    void get_status_code_200_when_retrieve_exchange_rate_information_by_get_request() {
        var response = client.target(BASE_URL + "IRR").GET();
        ExchangeRateDto exchangeRate = client.target(BASE_URL + "IRR").GET(ExchangeRateDto.class);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(exchangeRate.getBaseCurrency()).isEqualTo(Currency.IRR);
    }

    @Test
    @Disabled
    void get_status_code_201_after_created_new_data_successfully_by_post_request() {
        ExchangeRateDto exchangeRate = getExchangeRateDto();

        String jsonRate = gson.toJson(exchangeRate);
        HttpResponse<String> httpResponse = client.target(BASE_URL).POST(jsonRate);

        assertThat(httpResponse.statusCode()).isEqualTo(201);
    }

    @Test
    @Disabled
    void get_status_code_404_when_currency_is_not_found() {
        String notFoundCurrency = "notFound";
        HttpResponse<String> httpResponse = client.target(BASE_URL + notFoundCurrency).GET();

        assertThat(httpResponse.statusCode()).isEqualTo(404);
    }

    private static ExchangeRateDto getExchangeRateDto() {
        Map<Currency,Double> rates = new LinkedHashMap<>();
        rates.put(Currency.IRR, 45382.27);
        rates.put(Currency.USD, 1.07);

        return new ExchangeRateDtoBuilder()
                .setBaseCurrency(Currency.EUR)
                .setDate(LocalDate.now())
                .setRates(rates)
                .create();
    }
}

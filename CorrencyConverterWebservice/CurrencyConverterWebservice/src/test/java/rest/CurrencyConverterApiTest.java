package rest;

import com.dev.exchange_rate.domain.Currency;
import com.dev.exchange_rate.domain.ExchangeRate;
import com.dev.exchange_rate.domain.ExchangeRateBuilder;
import com.dev.exchange_rate.helper.HttpApiClient;
import com.dev.exchange_rate.helper.HttpHandlerApiClient;
import com.dev.exchange_rate.helper.file_reader.LocalDateTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyConverterApiTest {

    private static final String BASE_URL = "http://localhost:16162/CurrencyConverterWebservice-1.0/api/base_currency/";
    private HttpApiClient httpApiClient;
    private Gson gson;

    @BeforeEach
    void setUp() {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter()).create();
        httpApiClient = new HttpHandlerApiClient(gson);
        this.gson = createGson();
    }

    private  Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .setPrettyPrinting().create();
    }

    @Test
    void send_get_request_to_fetch_exchange_rates() {
        var exchangeRate = httpApiClient
                .target(BASE_URL + "IRR")
                .GET(ExchangeRate.class);

        assertThat(exchangeRate.getBaseCurrency()).isEqualTo(Currency.IRR);
    }

    @Test
    void send_post_request_to_add_new_exchange_rate() {
        ExchangeRate exchangeRate = new ExchangeRateBuilder().setBaseCurrency(Currency.EUR).setDate(LocalDate.now()).createExchangeRate();
        exchangeRate.addRate(Currency.IRR,45382.27);
        exchangeRate.addRate(Currency.USD,1.07);

        String jsonRate = gson.toJson(exchangeRate);
        System.out.println(jsonRate);
        HttpResponse<String> httpResponse = httpApiClient.target(BASE_URL).POST(jsonRate);

        System.out.println(httpResponse.body());
        System.out.println(httpResponse);
        assertThat(httpResponse.statusCode()).isEqualTo(201);
    }

    @Test
    void name() {
        String filePath = "data/fake_rate.json";
        Path resourceDirectory = Paths.get("src", "main", "resources");
        Path absolutePath = resourceDirectory.resolve(filePath);
        try {
            Files.createDirectories(absolutePath.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(BufferedWriter fileWriter = Files.newBufferedWriter(absolutePath, StandardOpenOption.APPEND)) {
            // Get the absolute path to the file
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter()).setPrettyPrinting().create();
            ExchangeRate exchangeRate = new ExchangeRateBuilder().setBaseCurrency(Currency.EUR).setDate(LocalDate.now()).createExchangeRate();
            exchangeRate.addRate(Currency.IRR,45382.27);
            exchangeRate.addRate(Currency.USD,1.07);;
            String s = gson.toJson(exchangeRate);
            fileWriter.write(s + System.lineSeparator());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

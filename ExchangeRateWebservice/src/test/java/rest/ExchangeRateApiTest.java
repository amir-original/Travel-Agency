package rest;

import com.dev.exchange_rate.domain.Currency;
import com.dev.exchange_rate.dto.ExchangeRateDto;
import com.dev.exchange_rate.dto.ExchangeRateDtoBuilder;
import com.dev.exchange_rate.helper.file_reader.LocalDateTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ExchangeRateApiTest {


    private Gson gson;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:16162/ExchangeRatesWebservice-1.0/api/";
        gson = createGson();
    }

    @Test
    @Disabled
    void it_get_status_code_ok_when_retrieve_exchange_rate_information_by_get_request() {
        RestAssured.defaultParser = Parser.JSON;

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .pathParams("rate","IRR")
                .when()
                .get("/latest/{rate}")
                .then()
                .statusCode(equalTo(200))
                .body("baseCurrency",is("IRR"))
                .body("rates",hasKey("USD"))
                .body("rates",hasKey("EUR"));
    }

    @Test
    @Disabled
    void it_get_status_code_201_after_created_new_data_successfully_by_post_request() {
        ExchangeRateDto exchangeRate = getExchangeRateDto();
        String data = gson.toJson(exchangeRate);
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(data)
                .when()
                .post("latest")
                .then()
                .statusCode(is(201));
    }

    @Test
    @Disabled
    void it_get_status_code_404_when_currency_is_not_found() {
        String notFoundCurrency = "notFound";
       given()
               .contentType(MediaType.APPLICATION_JSON)
               .pathParam("rate",notFoundCurrency)
               .when()
               .get("latest/{rate}")
               .then()
               .statusCode(is(404));
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

    private Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .setPrettyPrinting().create();
    }

}

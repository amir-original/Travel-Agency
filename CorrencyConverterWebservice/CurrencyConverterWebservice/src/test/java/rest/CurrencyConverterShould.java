package rest;

import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;

public class ExchangeRateServiceShould {

    @Test
    void send_get_request_to_get_exchange_rates() throws URISyntaxException {
        HttpRequest.Builder get = HttpRequest.newBuilder(new URI("http://localhost:16162/CurrencyConverterWebservice-1.0/"))
                .header("content-type","application/json")
                .GET();

    }
}

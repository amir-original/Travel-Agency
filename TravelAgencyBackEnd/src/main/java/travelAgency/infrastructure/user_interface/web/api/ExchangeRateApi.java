package travelAgency.infrastructure.user_interface.web.api;

import org.jetbrains.annotations.NotNull;
import travelAgency.infrastructure.libraries.currency_converter.ExchangeRateDAO;
import travelAgency.infrastructure.io.PropertiesReader;
import travelAgency.infrastructure.network.HttpClient;
import travelAgency.infrastructure.network.Response;
import travelAgency.model.rate.ExchangeRate;
import travelAgency.model.rate.Currency;

import java.net.URI;
import java.util.Optional;

public class ExchangeRateApi implements ExchangeRateDAO {

    public static final String API = "app.api.pro.rest_exchange_rate.url";
    private final HttpClient httpClient;
    private final String baseUrl;


    public ExchangeRateApi(HttpClient httpClient) {
        PropertiesReader propertiesReader = new PropertiesReader("app-config");
        this.baseUrl = propertiesReader.getProperty(API);
        this.httpClient = httpClient;
    }

    @Override
    public Optional<ExchangeRate> retrieveExchangeRate(Currency by) {
        final ExchangeRate exchangeRate = fetchExchangeRate(by);
        return Optional.ofNullable(exchangeRate);
    }

    @Override
    public double exchangeRateFor(Currency from, Currency to) {
        return retrieveExchangeRate(from)
                .orElseThrow(CouldNotFoundExchangeRate::new)
                .getRate(to);
    }

    private ExchangeRate fetchExchangeRate(Currency currency) {
        Response result;
        final String fullUrl = getFullUrl(currency);
        try {
            result = httpClient.get(URI.create(fullUrl));
        } catch (Exception e) {
            throw CouldNotConnectToExchangeRateWebService.withUrl(fullUrl);
        }
        return result.readEntity(ExchangeRate.class);
    }

    @NotNull
    private String getFullUrl(Currency currency) {
        return this.baseUrl + currency.value();
    }

}

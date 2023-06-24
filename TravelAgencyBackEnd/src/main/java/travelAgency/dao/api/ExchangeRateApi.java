package travelAgency.dao.api;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.rate.ExchangeRate;
import travelAgency.domain.rate.currency.Currency;
import travelAgency.helper.*;

import java.util.Optional;

public class ExchangeRateApi implements ExchangeRateDAO {

    public static final String API = "app.api.pro.rest_exchange_rate.url";
    private final HttpClient httpClient;
    private String baseUri;


    public ExchangeRateApi(HttpClient httpClient) {
        this.httpClient = httpClient;
        readConfig();
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
        ExchangeRate result;
        final String fullUrl = getFullUrl(currency);
        try {
            result = httpClient.target(fullUrl).GET(ExchangeRate.class);
        } catch (Exception e) {
            throw CouldNotConnectToExchangeRateWebService.withUrl(fullUrl);
        }
        return result;
    }

    @NotNull
    private String getFullUrl(Currency currency) {
        return this.baseUri + currency.value();
    }

    private void readConfig() {
        baseUri = new PropertiesReader("app-config").getProperty(API);
    }
}

package travelAgency.dao.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;
import travelAgency.domain.flight.currency.Currency;
import travelAgency.helper.HttpHandlerApiClient;
import travelAgency.helper.LocalDateAdapter;
import travelAgency.helper.PropertiesReader;

import java.time.LocalDate;
import java.util.Optional;

public class ExchangeRateDAOImpl implements ExchangeRateDAO {

    public static final String API = "app.api.pro.rest_exchange_rate.url";
    private final HttpHandlerApiClient request;
    private String baseUri;

    public ExchangeRateDAOImpl() {
        readConfig();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        request = new HttpHandlerApiClient(gson);
    }

    @Override
    public Optional<ExchangeRate> retrieveExchangeRate(Currency by) {
        final ExchangeRate exchangeRate = fetchExchangeRate(by);
        return Optional.ofNullable(exchangeRate);
    }

    @Override
    public double getExchangeRate(Currency from, Currency to) {
        return retrieveExchangeRate(from)
                .orElseThrow(ExchangeRateNotFoundException::new)
                .getRate(to);
    }

    private ExchangeRate fetchExchangeRate(Currency currency) {
        ExchangeRate result;
        try {
            final String fullUrl = getFullUrl(currency);
            result = request.target(fullUrl).GET(ExchangeRate.class);
        } catch (Exception e) {
            throw new WebServiceConnectionFailureException("Failed to connect to the exchange rate web service.");
        }
        return result;
    }

    @NotNull
    private String getFullUrl(Currency currency) {
        return this.baseUri + currency.name();
    }

    private void readConfig() {
        baseUri = new PropertiesReader("app-config").getProperty(API);
    }
}

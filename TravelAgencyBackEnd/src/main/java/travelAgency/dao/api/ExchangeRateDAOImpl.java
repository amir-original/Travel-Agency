package travelAgency.dao.api;

import travelAgency.helper.HttpHandlerApiClient;
import travelAgency.helper.PropertiesReader;

public class ExchangeRateDAOImpl implements ExchangeRateDAO {

    public static final String API = "app.api.exchangeRate.url";
    private final HttpHandlerApiClient request;
    private String baseUri;

    public ExchangeRateDAOImpl() {
        readConfig();
        request = new HttpHandlerApiClient();
    }

    @Override
    public double usdToIrr() {
        return service().usdToIrr();
    }

    @Override
    public double irrToUsd() {
        return service().irrToUsd();
    }

    private ExchangeRates service() {
        ExchangeRates result;
        try {
            result = request.target(baseUri).GET(ExchangeRates.class);
        } catch (Exception e) {
            throw new WebServiceConnectionFailureException("Failed to connect to the web service.");
        }
        return result;
    }

    private void readConfig() {
        baseUri = new PropertiesReader("app-config").getProperty(API);
    }

}

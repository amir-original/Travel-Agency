package travelAgency.dao.api;

import travelAgency.helper.HttpRequestHandler;
import travelAgency.helper.PropertiesReader;

public class ExchangeRateDAOImpl implements ExchangeRateDAO {

    public static final String API = "app.api.exchangeRate.url";
    private final HttpRequestHandler request;
    private String baseUri;

    public ExchangeRateDAOImpl() {
        readConfig();
        request = new HttpRequestHandler();
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
        return request.target(baseUri).GET(ExchangeRates.class);
    }

    private void readConfig() {
        baseUri = new PropertiesReader("app-config").getProperty(API);
    }

}

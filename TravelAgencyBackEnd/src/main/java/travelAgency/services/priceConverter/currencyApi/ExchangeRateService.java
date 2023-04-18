package travelAgency.services.priceConverter.currencyApi;

import travelAgency.helper.HttpRequestHandler;
import travelAgency.helper.PropertiesReader;
import travelAgency.services.priceConverter.ExchangeRates;

import java.net.http.HttpResponse;

public interface ExchangeRateService {

    double diffAmount();

    default ExchangeRates service(){
        final HttpRequestHandler request = new HttpRequestHandler();

        final HttpResponse<String> response = request.target(getBaseUri()).GET().build();


        return request.getResponse(response, ExchangeRates.class);
    }

    private String getBaseUri() {
        return readConfig().getProperty("app.api.currencyConverter.url");
    }

    private PropertiesReader readConfig() {
        return new PropertiesReader("app-config");
    }

}

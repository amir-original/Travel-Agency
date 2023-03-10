package travelAgency.service.priceConverter.currencyApi;

import travelAgency.helper.HttpRequestHandler;
import travelAgency.helper.PropertiesReader;
import travelAgency.service.priceConverter.CurrencyConverter;

import java.net.http.HttpResponse;

public interface CurrencyConverterApiService {

    double diffAmount();

    default CurrencyConverter converter(){
        final HttpRequestHandler request = new HttpRequestHandler();

        final HttpResponse<String> response = request.target(getBaseUri()).GET().build();
        return request.getResponse(response, CurrencyConverter.class);
    }

    private String getBaseUri() {
        return readConfig().getProperty("app.api.currencyConverter.url");
    }

    private PropertiesReader readConfig() {
        return new PropertiesReader("app-config");
    }

}

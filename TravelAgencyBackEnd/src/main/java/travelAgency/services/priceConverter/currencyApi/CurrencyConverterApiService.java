package travelAgency.services.priceConverter.currencyApi;

import com.google.gson.reflect.TypeToken;
import travelAgency.helper.HttpRequestHandler;
import travelAgency.helper.PropertiesReader;
import travelAgency.services.priceConverter.CurrencyConverter;

import java.lang.reflect.GenericArrayType;
import java.net.http.HttpResponse;

public interface CurrencyConverterApiService {

    double diffAmount();

    default CurrencyConverter service(){
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

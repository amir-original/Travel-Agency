package travelAgency.service.priceConverter.currencyApi;

import travelAgency.helper.HttpRequestHandler;
import travelAgency.helper.PropertiesReader;
import travelAgency.service.priceConverter.CurrencyConverter;

import java.net.http.HttpResponse;

public class DollarToRialConverterApi implements CurrencyConverterApiService {

    @Override
    public double diffAmount() {
        return converter().oneDollarToRial();
    }
}

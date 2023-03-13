package travelAgency.services.priceConverter.currencyApi;

public class DollarToRialConverterApi implements CurrencyConverterApiService {

    @Override
    public double diffAmount() {
        return converter().oneDollarToRial();
    }
}

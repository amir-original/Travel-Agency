package travelAgency.services.priceConverter.currencyApi;

public class RialToDollarConverterApi implements CurrencyConverterApiService {

    @Override
    public double diffAmount() {
        return service().oneRialToDollar();
    }
}

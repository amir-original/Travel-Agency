package travelAgency.service.priceConverter.currencyApi;

public class RialToDollarConverterApi implements CurrencyConverterApiService {

    @Override
    public double diffAmount() {
        return converter().oneRialToDollar();
    }
}

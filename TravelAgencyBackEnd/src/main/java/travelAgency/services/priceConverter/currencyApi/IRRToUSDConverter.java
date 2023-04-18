package travelAgency.services.priceConverter.currencyApi;

public class IRRToUSDConverter implements ExchangeRateService {

    @Override
    public double diffAmount() {
        return service().irrToUsd();
    }
}
